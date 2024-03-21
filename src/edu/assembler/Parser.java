package src.edu.assembler;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public final class Parser implements Closeable {
    private final Reader reader;

    public enum Instruction {
        A_INSTRUCTION,
        C_INSTRUCTION,
        L_INSTRUCTION
    }

    //TODO: Мб ридер прост?
    public Parser(Reader reader) {
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
