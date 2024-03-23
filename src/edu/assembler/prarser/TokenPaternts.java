package edu.assembler.prarser;

import java.util.regex.Pattern;

public final class TokenPaternts {
    static Pattern SYMBOL = Pattern.compile("^[a-zA-Z_.$:][a-zA-Z_.$:0-9]*$");
    static Pattern COMMENT = Pattern.compile("^\\/{2}.*$");
    static Pattern EMPTY = Pattern.compile("^\\s*$");
    
    static Pattern LABEL_INSTRUCTION = Pattern.compile("^(" + SYMBOL.pattern() + ")$");

    static Pattern A_INSTRUCTION = Pattern.compile("^@[0-9]+$");
    static Pattern C_INSTRUCTION = Pattern.compile(SYMBOL.pattern());

    private TokenPaternts() {}
}
