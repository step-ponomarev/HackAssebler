package edu.assembler;

public final class Code {
    public String dest(String command) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String comp(String command) {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String jump(String jumpInstruction) {
        if (jumpInstruction == null) {
            return "000";
        }

        return switch (jumpInstruction) {
            case "JGT" -> "001";
            case "JEQ" -> "010";
            case "JGE" -> "011";
            case "JLT" -> "100";
            case "JNE" -> "101";
            case "JLE" -> "110";
            case "JMP" -> "111";
            default -> throw new IllegalStateException("Unsupported jump instruction: " + jumpInstruction);
        };
    }
}
