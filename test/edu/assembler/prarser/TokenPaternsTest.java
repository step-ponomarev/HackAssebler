package edu.assembler.prarser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TokenPaternsTest {
    @Test
    public void matchCommentLine() {
        Assertions.assertTrue(TokenPaterns.COMMENT.matcher("// some comment").matches());
    }

    @Test
    public void matchEmptyLine() {
        Assertions.assertTrue(TokenPaterns.EMPTY.matcher("   \s \t").matches());
    }

    @Test
    public void matchAInstruction() {
        Assertions.assertTrue(TokenPaterns.A_INSTRUCTION.matcher("@128").matches());
    }

    @Test
    public void matchTooShortAInstruction() {
        Assertions.assertFalse(TokenPaterns.A_INSTRUCTION.matcher("@").matches());
    }

    @Test
    public void matchSymbol() {
        final String symbol = "testSymbol";
        Assertions.assertTrue(TokenPaterns.SYMBOL.matcher(symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_", "."};
        for (String start : correctStringFirstSymbols) {
            Assertions.assertTrue(TokenPaterns.SYMBOL.matcher(start + symbol).matches());
        }

        for (int i = 0; i <= 9; i++) {
            Assertions.assertFalse(TokenPaterns.SYMBOL.matcher(i + symbol).matches());
        }
    }

    @Test
    public void matchLabel() {
        final String symbol = "testSymbol)";
        Assertions.assertTrue(TokenPaterns.LABEL_INSTRUCTION.matcher("(" + symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_", "."};
        for (String start : correctStringFirstSymbols) {
            final String curSymbol = "(" + start + symbol;
            Assertions.assertTrue(TokenPaterns.LABEL_INSTRUCTION.matcher(curSymbol).matches());
            Assertions.assertTrue(TokenPaterns.C_INSTRUCTION.matcher(curSymbol).matches());
        }

        for (int i = 0; i <= 9; i++) {
            final String curSymbol = "(" + i + symbol;
            Assertions.assertFalse(TokenPaterns.LABEL_INSTRUCTION.matcher(curSymbol).matches());
            Assertions.assertFalse(TokenPaterns.C_INSTRUCTION.matcher(curSymbol).matches());
        }
    }

    @Test
    public void matchLabelAssignInstruction() {
        final String[] registers = {"A", "D", "M"};
        for (String register1 : registers) {
            for (String register2 : registers) {
                final String assign = register1 + "=" + register2;
                Assertions.assertTrue(TokenPaterns.ASSIGN_INSTRUCTION.matcher(assign).matches());
                Assertions.assertTrue(TokenPaterns.C_INSTRUCTION.matcher(assign).matches());
            }
        }

        final String[] operations = {"+", "-"};
        for (String register1 : registers) {
            for (String register2 : registers) {
                for (String register3 : registers) {
                    for (String operation : operations) {
                        final String assign = register1 + "=" + register2 + operation + register3;
                        Assertions.assertTrue(TokenPaterns.ASSIGN_INSTRUCTION.matcher(assign).matches());
                        Assertions.assertTrue(TokenPaterns.C_INSTRUCTION.matcher(assign).matches());
                    }
                }
            }
        }
    }
}
