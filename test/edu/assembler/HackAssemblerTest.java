package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HackAssemblerTest {
    private static final Pattern SOURSE_FILE_PATTERN = Pattern.compile("^[A-Za-z0-9]+.asm$");

    @Test
    public void testCompilation() throws IOException {
        final Iterator<Path> iterator = Files.newDirectoryStream(Resources.RESOURCES_DIR).iterator();
        while (iterator.hasNext()) {
            final Path next = iterator.next();
            if (!SOURSE_FILE_PATTERN.matcher(next.getFileName().toString()).matches()) {
                continue;
            }

            testCompilation(next);
        }
    }

    private static void testCompilation(Path sourceFile) throws IOException {
        final String name = sourceFile.getFileName().toString().replace(".asm", "");

        //test binary code
        final Path testHackFile = Resources.RESOURCES_DIR.resolve(name + "_test.asm");
        HackAssembler.main(new String[]{sourceFile.toString(), testHackFile.toString()});
        Assertions.assertTrue(Files.exists(testHackFile));

        try {
            //reference binary code
            final Path compiledHackFile = Resources.RESOURCES_DIR.resolve(name + ".hack");
            Assertions.assertEquals(Files.size(compiledHackFile), Files.size(testHackFile));
            Assertions.assertEquals(Files.readString(compiledHackFile), Files.readString(testHackFile));
        } finally {
            Files.deleteIfExists(testHackFile);
        }
    }
}
