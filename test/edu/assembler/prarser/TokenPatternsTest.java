package edu.assembler.prarser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.assembler.Constants;

public final class TokenPatternsTest {
    @Test
    public void matchCommentLine() {
        Assertions.assertTrue(TokenPatterns.COMMENT.matcher("// some comment").matches());
    }

    @Test
    public void matchEmptyLine() {
        Assertions.assertTrue(TokenPatterns.EMPTY.matcher("   \s \t").matches());
    }

    @Test
    public void matchAInstruction() {
        Assertions.assertTrue(TokenPatterns.A_INSTRUCTION.matcher("@128").matches());

        final boolean matches = Constants.DEFAULT_LABEL_TO_ADDRESS.keySet().stream().allMatch(s -> TokenPatterns.A_INSTRUCTION.matcher("@" + s).matches());
        Assertions.assertTrue(matches);
    }

    @Test
    public void matchTooShortAInstruction() {
        Assertions.assertFalse(TokenPatterns.A_INSTRUCTION.matcher("@").matches());
    }

    @Test
    public void matchSymbol() {
        final String symbol = "testSymbol";
        Assertions.assertTrue(TokenPatterns.SYMBOL.matcher(symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_", "."};
        for (String start : correctStringFirstSymbols) {
            Assertions.assertTrue(TokenPatterns.SYMBOL.matcher(start + symbol).matches());
        }

        for (int i = 0; i <= 9; i++) {
            Assertions.assertFalse(TokenPatterns.SYMBOL.matcher(i + symbol).matches());
        }

        final boolean matches = Constants.DEFAULT_LABEL_TO_ADDRESS.keySet().stream().allMatch(s -> TokenPatterns.SYMBOL.matcher(s).matches());
        Assertions.assertTrue(matches);
    }

    @Test
    public void matchLabel() {
        final String symbol = "testSymbol)";
        Assertions.assertTrue(TokenPatterns.L_INSTRUCTION.matcher("(" + symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_", "."};
        for (String start : correctStringFirstSymbols) {
            final String curSymbol = "(" + start + symbol;
            Assertions.assertTrue(TokenPatterns.L_INSTRUCTION.matcher(curSymbol).matches());
        }

        for (int i = 0; i <= 9; i++) {
            final String curSymbol = "(" + i + symbol;
            Assertions.assertFalse(TokenPatterns.L_INSTRUCTION.matcher(curSymbol).matches());
        }
    }

    @Test
    public void matchLabelAssignInstruction() {
        final String[] registers = {"A", "D", "M"};
        for (String register1 : registers) {
            for (String register2 : registers) {
                final String assign = register1 + "=" + register2;
                Assertions.assertTrue(TokenPatterns.ASSIGN_INSTRUCTION.matcher(assign).matches());
                Assertions.assertTrue(TokenPatterns.C_INSTRUCTION.matcher(assign).matches());
            }
        }

        final String[] operations = {"+", "-"};
        for (String register1 : registers) {
            for (String register2 : registers) {
                for (String register3 : registers) {
                    for (String operation : operations) {
                        final String assign = register1 + "=" + register2 + operation + register3;
                        Assertions.assertTrue(TokenPatterns.ASSIGN_INSTRUCTION.matcher(assign).matches());
                        Assertions.assertTrue(TokenPatterns.C_INSTRUCTION.matcher(assign).matches());
                    }
                }
            }
        }

        for (String register1 : registers) {
            for (String register2 : registers) {
                for (String operation : operations) {
                    for (int i = 0; i <= 1; i++) {
                        final String assign = register1 + "=" + register2 + operation + i;
                        Assertions.assertTrue(TokenPatterns.ASSIGN_INSTRUCTION.matcher(assign).matches());
                        Assertions.assertTrue(TokenPatterns.C_INSTRUCTION.matcher(assign).matches());
                    }
                }
            }
        }
    }

    @Test
    public void matchNotAssignInstruction() {
        Assertions.assertTrue(TokenPatterns.ASSIGN_INSTRUCTION.matcher("D=-D").matches());
    }

    @Test
    public void matchLabelJmpInstruction() {
        final String[] jmpCommands = {"JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP"};

        final String[] registers = {"A", "D", "M"};
        for (String register : registers) {
            for (String jmp : jmpCommands) {
                final String command = register + ";" + jmp;
                Assertions.assertTrue(TokenPatterns.JMP_INSTRUCTION.matcher(command).matches());
                Assertions.assertTrue(TokenPatterns.C_INSTRUCTION.matcher(command).matches());
            }
        }

        for (int i = 0; i < 100; i++) {
            for (String jmp : jmpCommands) {
                final String command = i + ";" + jmp;
                Assertions.assertTrue(TokenPatterns.JMP_INSTRUCTION.matcher(command).matches());
                Assertions.assertTrue(TokenPatterns.C_INSTRUCTION.matcher(command).matches());
            }
        }
    }
}
