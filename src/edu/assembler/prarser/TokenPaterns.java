package edu.assembler.prarser;

import java.util.regex.Pattern;

public final class TokenPaterns {
    static Pattern SYMBOL = Pattern.compile("^[a-zA-Z_.$:][a-zA-Z_.$:0-9]*$");
    static Pattern COMMENT = Pattern.compile("^\\/{2}.*$");
    static Pattern EMPTY = Pattern.compile("^\\s*$");

    static Pattern LABEL_INSTRUCTION = Pattern.compile("^\\(" + SYMBOL.pattern().substring(1, SYMBOL.pattern().length() - 1) + "\\)$");

    static Pattern ASSIGN_INSTRUCTION = Pattern.compile("^[ADM]{1}[\s]*=[\s]*[ADM]{1}([\s]*[-+]{1}[\s]*[ADM])?$");

    static Pattern A_INSTRUCTION = Pattern.compile("^@[0-9]+$");
    static Pattern C_INSTRUCTION = Pattern.compile(String.format("(%s|%s)", LABEL_INSTRUCTION, ASSIGN_INSTRUCTION));

    private TokenPaterns() {}
}
