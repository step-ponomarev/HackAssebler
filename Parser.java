public final class Parser {
    public enum Instruction {
        A_INSTRUCTION,
        C_INSTRUCTION,
        L_INSTRUCTION
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
