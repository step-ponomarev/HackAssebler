package edu.assembler;

import java.io.BufferedReader;
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
        for (Path next : Files.newDirectoryStream(Resources.RESOURCES_DIR)) {
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

        //reference binary code
        final Path reference = Resources.RESOURCES_DIR.resolve(name + ".hack");
        Assertions.assertEquals(Files.size(reference), Files.size(testHackFile));

        int currLine = 1;
        try (BufferedReader referenceFileReader = Files.newBufferedReader(reference);
             BufferedReader testFileReader = Files.newBufferedReader(testHackFile)
        ) {
            String referenceLine;
            String testLine;
            while (((referenceLine = referenceFileReader.readLine()) != null)
                    && ((testLine = testFileReader.readLine()) != null)) {
                Assertions.assertEquals(referenceLine, testLine);
                currLine++;
            }
        } catch (Error e) {
            throw new RuntimeException("Failed compilation of " + sourceFile.getFileName().toString() + " lines are not equals, line: " + currLine, e);
        } finally {
            Files.deleteIfExists(testHackFile);
        }
    }
}
