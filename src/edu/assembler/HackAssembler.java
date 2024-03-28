package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        try (final Parser parser = new Parser(asmFile)) {
            final StringBuilder code = new StringBuilder();
            while (parser.hasMoreLines()) {
                parser.advance();

                final InstructionType instructionType = parser.instructionType();
                code.append(instructionType == InstructionType.C_INSTRUCTION ? createCInstruction(parser) : createAInstruction(parser));
                code.append("\n");
            }

            final Path outFile = Paths.get(args[1]);
            Files.deleteIfExists(outFile);
            Files.createFile(outFile);
            Files.writeString(outFile, code.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createAInstruction(Parser parser) {
        final String binaryValue = Integer.toBinaryString(
                Integer.parseInt(parser.symbol())
        );

        return String.format("%0" + (Constants.INSTRUCTION_LENGTH - binaryValue.length()) + "d%s", 0, binaryValue);
    }

    private static String createCInstruction(Parser parser) {
        final StringBuilder instruction = new StringBuilder("111");
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
