package edu.assembler.prarser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TokenPaterntsTest {
    private static final String COMMENT_LINE = "// some comment";

    private static final String EMPTY_LINE = "   \s \t";

    private static final String A_COMMAND_LINE = "@128";

    private static final String INVALID_A_COMMAND = "@";

    private static final String INVALID_SYMBOL = "1:testSymbol";

    @Test
    public void parseCommentLine() {
        Assertions.assertTrue(TokenPaternts.COMMENT.matcher(COMMENT_LINE).matches());
    }

    @Test
    public void parseEmptyLine() {
        Assertions.assertTrue(TokenPaternts.EMPTY.matcher(EMPTY_LINE).matches());
    }

    @Test
    public void parseAInstruction() {
        Assertions.assertTrue(TokenPaternts.A_INSTRUCTION.matcher(A_COMMAND_LINE).matches());
    }

    @Test
    public void parseTooShortAInstruction() {
        Assertions.assertFalse(TokenPaternts.A_INSTRUCTION.matcher(INVALID_A_COMMAND).matches());
    }

    @Test
    public void parseValidSymbol() {
        final String symbol = "symbol";
        Assertions.assertTrue(TokenPaternts.SYMBOL.matcher(symbol).matches());

        final String[] correctStringFirstSymbols = {":", "$", "_"};
        for (String start : correctStringFirstSymbols) {
            Assertions.assertTrue(TokenPaternts.SYMBOL.matcher(start + symbol).matches());
        }

        for (int i = 0; i < 10; i++) {
            Assertions.assertFalse(TokenPaternts.SYMBOL.matcher(i + symbol).matches());
        }
    }

    @Test
    public void parseInvalidSymbol() {
        Assertions.assertFalse(TokenPaternts.SYMBOL.matcher(INVALID_SYMBOL).matches());
    }
}
