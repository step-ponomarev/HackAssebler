package edu.assembler.prarser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TokenPaterntsTest {
    @Test
    public void matchCommentLine() {
        Assertions.assertTrue(TokenPaternts.COMMENT.matcher("// some comment").matches());
    }

    @Test
    public void matchEmptyLine() {
        Assertions.assertTrue(TokenPaternts.EMPTY.matcher("   \s \t").matches());
    }

    @Test
    public void matchAInstruction() {
        Assertions.assertTrue(TokenPaternts.A_INSTRUCTION.matcher("@128").matches());
    }

    @Test
    public void matchTooShortAInstruction() {
        Assertions.assertFalse(TokenPaternts.A_INSTRUCTION.matcher("@").matches());
    }

    @Test
    public void matchSymbol() {
        final String symbol = "testSymbol";
        Assertions.assertTrue(TokenPaternts.SYMBOL.matcher(symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_", "."};
        for (String start : correctStringFirstSymbols) {
            Assertions.assertTrue(TokenPaternts.SYMBOL.matcher(start + symbol).matches());
        }

        for (int i = 0; i <= 9; i++) {
            Assertions.assertFalse(TokenPaternts.SYMBOL.matcher(i + symbol).matches());
        }
    }
}
