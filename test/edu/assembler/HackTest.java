package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HackTest {
    @Test
    public void testAddCompilation() throws IOException {
        final Path addAsmFile = Resources.RESOURCES_DIR.resolve("Add.asm");

        final Path testHackFile = Resources.RESOURCES_DIR.resolve("Add_test.hack");
        Hack.main(new String[]{addAsmFile.toString(), testHackFile.toString()});
        Assertions.assertTrue(Files.exists(testHackFile));

        try {
            final Path compiledHackFile = Resources.RESOURCES_DIR.resolve("Add.hack");
            Assertions.assertEquals(Files.readAllBytes(compiledHackFile), Files.readAllBytes(testHackFile));
        } finally {
//            Files.deleteIfExists(testHackFile);
        }

    }
}
