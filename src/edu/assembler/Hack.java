package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.assembler.prarser.InstructionType;
import edu.assembler.prarser.Parser;

public final class Hack {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Expected 1 arg, but got: " + args.length);
        }

        final Path asmFile = Paths.get(args[0]);
        if (Files.notExists(asmFile)) {
            throw new IllegalStateException("File is not exists");
        }

        try (final Parser parser = new Parser(asmFile)) {
            final StringBuilder code = new StringBuilder();
            while (parser.hasMoreLines()) {
                parser.advance();

                final InstructionType instructionType = parser.instructionType();
                if (instructionType == InstructionType.A_INSTRUCTION) {
                    final String binaryValue = Integer.toBinaryString(Integer.parseInt(parser.symbol()));
                    code.append(
                            String.format("%0" + (16 - binaryValue.length()) + "d%s", 0, binaryValue)
                    );
                } else {
                    code.append(111);

                    code.append(
                            Code.comp(
                                    parser.comp()
                            )
                    );

                    code.append(
                            Code.dest(
                                    parser.dest()
                            )
                    );

                    code.append(
                            Code.jump(
                                    parser.jump()
                            )
                    );
                }

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
}
