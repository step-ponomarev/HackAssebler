package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import edu.assembler.prarser.InstructionType;
import edu.assembler.prarser.Parser;

public final class HackAssembler {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected 2 arg, but got: " + args.length);
        }

        final Path asmFile = Paths.get(args[0]);
        if (Files.notExists(asmFile)) {
            throw new IllegalStateException("File does not exist: " + asmFile);
        }

        final Map<String, Integer> labels = collectLabels(asmFile);
        final String code = generateCode(asmFile, labels);

        final Path outFile = Paths.get(args[1]);
        try {
            Files.deleteIfExists(outFile);
            Files.createFile(outFile);
            Files.writeString(outFile, code);
        } catch (IOException e) {
            throw new RuntimeException("Compilation is failed ", e);
        }
    }

    static Map<String, Integer> collectLabels(Path asmFile) {
        try (final Parser parser = new Parser(asmFile)) {
            final Map<String, Integer> labels = new HashMap<>(Constants.DEFAULT_LABEL_TO_ADDRESS);

            for (int ip = 0; parser.hasMoreLines(); ip++) {
                parser.advance();
                if (parser.instructionType() != InstructionType.L_INSTRUCTION) {
                    continue;
                }

                labels.put(parser.symbol(), ip--);
            }

            return labels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String generateCode(Path asmFile, Map<String, Integer> labels) {
        try (final Parser parser = new Parser(asmFile)) {
            final int[] currVarAddress = {Constants.VARIABLE_START_ADDRESS};
            final StringBuilder code = new StringBuilder();
            while (parser.hasMoreLines()) {
                parser.advance();

                final InstructionType instructionType = parser.instructionType();
                if (instructionType == InstructionType.L_INSTRUCTION) {
                    continue;
                }

                code.append(instructionType == InstructionType.C_INSTRUCTION ? createCInstruction(parser) : createAInstruction(parser.symbol(), labels, currVarAddress));
                code.append("\n");

            }

            return code.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static String createAInstruction(String symbol, Map<String, Integer> labelToAddress, int[] currVarAddress) {
        if (currVarAddress.length != 1) {
            throw new IllegalStateException("Curr address invalid format");
        }

        String binaryValue;
        try {
            binaryValue = Integer.toBinaryString(Integer.parseInt(symbol));
        } catch (NumberFormatException e) {
            Integer address = labelToAddress.get(symbol);
            if (address == null) {
                final int varAddress = currVarAddress[0]++;

                address = varAddress;
                labelToAddress.put(symbol, address);
            }

            binaryValue = Integer.toBinaryString(address);
        }

        return String.format("%0" + (Constants.INSTRUCTION_LENGTH - binaryValue.length()) + "d%s", 0, binaryValue);
    }

    static String createCInstruction(Parser parser) {
        final StringBuilder instruction = new StringBuilder(Constants.C_INSTRUCTION_START_CODE);
        instruction.append(
                Code.comp(
                        parser.comp()
                )
        );

        instruction.append(
                Code.dest(
                        parser.dest()
                )
        );

        instruction.append(
                Code.jump(
                        parser.jump()
                )
        );

        return instruction.toString();
    }
}
