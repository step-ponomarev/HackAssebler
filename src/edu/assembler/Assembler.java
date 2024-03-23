package edu.assembler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import edu.assembler.prarser.Parser;

public final class Assembler {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected 1 arg, but got: " + args.length);
        }

        final Path path = Paths.get(args[0]);
        if (Files.notExists(path)) {
            throw new IllegalStateException("File is not exists");
        }

        try (final Parser parser = new Parser(Files.newBufferedReader(path))) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
