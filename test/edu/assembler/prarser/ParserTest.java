package edu.assembler.prarser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParserTest {
    final static Path RESOURCES_DIR = Paths.get("test", "resources");

    @Test
    public void testEmptyFile() throws IOException {
        final Path emptyFile = RESOURCES_DIR.resolve("empty.asm");
        try (Parser parser = new Parser((emptyFile))) {
            parser.advance();

            Assertions.assertFalse(parser.hasMoreLines());
        }
    }

    @Test
    public void testACommand() throws IOException {
        final Path emptyFile = RESOURCES_DIR.resolve("a_instruction.asm");
        try (Parser parser = new Parser((emptyFile))) {
            parser.advance();

            Assertions.assertEquals("12345", parser.symbol());
            Assertions.assertFalse(parser.hasMoreLines());
        }
    }
}
