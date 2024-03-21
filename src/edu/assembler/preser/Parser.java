package edu.assembler.preser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

public final class Parser implements Closeable {
    private final BufferedReader reader;
    private boolean eof;
    private String currentLine;

    public enum Instruction {
        A_INSTRUCTION,
        C_INSTRUCTION,
        L_INSTRUCTION
    }

    public Parser(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null!");
        }

        this.reader = reader;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public boolean hasMoreLines() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void advance() {
        try {
            final String line = reader.readLine();
            if (line == null) {
                currentLine = null;
                eof = true;
                return;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        throw new UnsupportedOperationException("Not implemented");
    }

    public Instruction instructionType() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String symbol() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String dest() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String comp() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String jump() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
