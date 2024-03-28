package edu.assembler.prarser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.assembler.Constants;
import edu.assembler.Resources;
import edu.assembler.exceptions.NumberOutOfRangeException;

public final class ParserTest {
    @Test
    public void testEmptyFile() throws IOException {
        final Path file = Resources.RESOURCES_DIR.resolve("empty.asm");
        Files.createFile(file);
        try (Parser parser = new Parser((file))) {
            parser.advance();

            Assertions.assertFalse(parser.hasMoreLines());
            Assertions.assertNull(parser.instructionType());
            Assertions.assertNull(parser.symbol());
            Assertions.assertNull(parser.comp());
            Assertions.assertNull(parser.dest());
            Assertions.assertNull(parser.jump());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testComments() throws IOException {
        final String comments = """
                // Мой комментарий, он будет пропущен
                                
                                
                // Еще один комментарий
                                
                                
                                
                // и еще один
                """;

        final Path file = Resources.RESOURCES_DIR.resolve("comments.asm");
        Files.createFile(file);
        Files.writeString(file, comments);
        try (Parser parser = new Parser((file))) {
            parser.advance();

            Assertions.assertNull(parser.instructionType());
            Assertions.assertNull(parser.symbol());
            Assertions.assertNull(parser.comp());
            Assertions.assertNull(parser.dest());
            Assertions.assertNull(parser.jump());
            Assertions.assertFalse(parser.hasMoreLines());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testAInstruction() throws IOException {
        final String digit = "12345";

        final Path file = Resources.RESOURCES_DIR.resolve("a_instruction.asm");
        Files.createFile(file);
        Files.writeString(file, "@" + digit);

        try (Parser parser = new Parser((file))) {
            parser.advance();

            Assertions.assertEquals(digit, parser.symbol());
            Assertions.assertEquals(InstructionType.A_INSTRUCTION, parser.instructionType());
            Assertions.assertNull(parser.comp());
            Assertions.assertNull(parser.dest());
            Assertions.assertNull(parser.jump());
            Assertions.assertFalse(parser.hasMoreLines());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testNumberOutOfRange() throws IOException {
        final String codeTemplate = """
                            @%d
                            @%d
                            """;

        final Path file = Resources.RESOURCES_DIR.resolve("a_instruction.asm");
        Files.createFile(file);
        Files.writeString(file, String.format(codeTemplate, Constants.MAX_DECIMAL_VALUE, Constants.MAX_DECIMAL_VALUE + 1));

        try (Parser parser = new Parser((file))) {
            parser.advance();

            Assertions.assertEquals(String.valueOf(Constants.MAX_DECIMAL_VALUE), parser.symbol());
            Assertions.assertEquals(InstructionType.A_INSTRUCTION, parser.instructionType());
            Assertions.assertNull(parser.comp());
            Assertions.assertNull(parser.dest());
            Assertions.assertNull(parser.jump());

            Assertions.assertThrows(NumberOutOfRangeException.class, parser::advance);
            Assertions.assertFalse(parser.hasMoreLines());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testJumpInstruction() throws IOException {
        final String[] registers = {"D", "M", "A"};
        final String[] jumpInstructions = {"JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"};

        StringBuilder asmInstruction = new StringBuilder();
        for (String register : registers) {
            for (String jump : jumpInstructions) {
                asmInstruction.append(register).append(";").append(jump).append("\n");
            }
        }

        final Path file = Resources.RESOURCES_DIR.resolve("jump_instruction.asm");
        Files.createFile(file);
        Files.writeString(file, asmInstruction.toString());

        try (Parser parser = new Parser((file))) {
            for (String register : registers) {
                for (String jump : jumpInstructions) {
                    parser.advance();

                    Assertions.assertEquals(InstructionType.C_INSTRUCTION, parser.instructionType());
                    Assertions.assertEquals(register, parser.dest());
                    Assertions.assertEquals(jump, parser.jump());
                    Assertions.assertNull(parser.symbol());
                    Assertions.assertNull(parser.comp());
                }
            }

            Assertions.assertFalse(parser.hasMoreLines());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testAssignInstruction() throws IOException {
        final String[] registers = {"D", "M", "A"};
        final String[] operations = {"+", "-"};

        StringBuilder asmInstruction = new StringBuilder();
        for (String destRegister : registers) {
            for (String compRegister1 : registers) {
                for (String compRegister2 : registers) {
                    for (String operation : operations) {
                        asmInstruction.append(destRegister).append("=").append(compRegister1).append(operation).append(compRegister2).append("\n");
                    }
                }
            }
        }

        final Path file = Resources.RESOURCES_DIR.resolve("assign_instruction.asm");
        Files.createFile(file);
        Files.writeString(file, asmInstruction.toString());

        try (Parser parser = new Parser((file))) {
            for (String destRegister : registers) {
                for (String compRegister1 : registers) {
                    for (String compRegister2 : registers) {
                        for (String operation : operations) {
                            parser.advance();

                            Assertions.assertEquals(InstructionType.C_INSTRUCTION, parser.instructionType());
                            Assertions.assertEquals(destRegister, parser.dest());
                            Assertions.assertEquals(compRegister1 + operation + compRegister2, parser.comp());

                            Assertions.assertNull(parser.symbol());
                            Assertions.assertNull(parser.jump());
                        }
                    }
                }
            }
            Assertions.assertFalse(parser.hasMoreLines());
        } finally {
            Files.deleteIfExists(file);
        }
    }

    @Test
    public void testSpaceRemoving() throws IOException {
        final String assignText = "A     = M +      D";

        final Path file = Resources.RESOURCES_DIR.resolve("assign_instruction.asm");
        Files.createFile(file);
        Files.writeString(file, assignText);

        try (Parser parser = new Parser((file))) {
            Assertions.assertEquals(assignText.replaceAll("\s", ""), parser.readNextInstruction());
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
