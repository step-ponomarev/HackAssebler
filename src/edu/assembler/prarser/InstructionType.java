package edu.assembler.prarser;

import java.util.regex.Pattern;

public enum InstructionType {
    C_INSTRUCTION(TokenPatterns.C_INSTRUCTION),
    A_INSTRUCTION(TokenPatterns.A_INSTRUCTION);
    private final Pattern pattern;

    InstructionType(Pattern pattern) {
        this.pattern = pattern;
    }

    public static InstructionType parse(String line) {
        for (InstructionType type : InstructionType.values()) {
            if (type.pattern.matcher(line).matches()) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unexpected line: " + line);
    }
}
