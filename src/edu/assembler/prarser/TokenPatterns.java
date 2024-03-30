package edu.assembler.prarser;

import java.util.regex.Pattern;

public final class TokenPatterns {
    static Pattern SYMBOL = Pattern.compile("^[a-zA-Z_.$:][a-zA-Z_.$:0-9]*$");
    static Pattern DECIMAL = Pattern.compile("^[0-9]+$");
    
    static Pattern COMMENT = Pattern.compile("^\\/{2}.*$");
    
    static Pattern EMPTY = Pattern.compile("^\\s*$");

    static Pattern L_INSTRUCTION = Pattern.compile("^\\(" + SYMBOL.pattern().substring(1, SYMBOL.pattern().length() - 1) + "\\)$");

    static Pattern ASSIGN_INSTRUCTION = Pattern.compile("^[ADM]+[\s]*=[\s]*([!]*[ADM]{1}|([01]{1}|-1))([\s]*[-+&|]{1}[\s]*([ADM]{1}|[01]{1}))?$");

    static Pattern JMP_INSTRUCTION = Pattern.compile("^([ADM]{1}|[0-9]+);(JGT|JEQ|JGE|JLT|JNE|JLE|JMP)$");

    static Pattern A_INSTRUCTION = Pattern.compile(String.format("@(%s|%s)", SYMBOL.pattern().substring(1, SYMBOL.pattern().length() - 1), "[0-9]+"));
    
    static Pattern C_INSTRUCTION = Pattern.compile(String.format("(%s|%s)", ASSIGN_INSTRUCTION, JMP_INSTRUCTION));

    private TokenPatterns() {}
}
