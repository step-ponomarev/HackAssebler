package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HackAssemblerTest {
    @Test
    public void testAddCompilation() throws IOException {
        final Path addAsmFile = Resources.RESOURCES_DIR.resolve("Add.asm");

        final Path testHackFile = Resources.RESOURCES_DIR.resolve("Add_test.hack");
        HackAssembler.main(new String[]{addAsmFile.toString(), testHackFile.toString()});
        Assertions.assertTrue(Files.exists(testHackFile));

        try {
            final Path compiledHackFile = Resources.RESOURCES_DIR.resolve("Add.hack");
            Assertions.assertEquals(Files.size(compiledHackFile), Files.size(testHackFile));
            Assertions.assertEquals(Files.readString(compiledHackFile), Files.readString(testHackFile));
        } finally {
            Files.deleteIfExists(testHackFile);
        }
    }
}
