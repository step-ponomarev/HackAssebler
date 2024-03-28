package edu.assembler.prarser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.assembler.Code;

public final class CodeTest {
    @Test
    public void testDestination() {
        Assertions.assertEquals("000", Code.dest(null));
        Assertions.assertEquals("001", Code.dest("M"));
        Assertions.assertEquals("010", Code.dest("D"));
        Assertions.assertEquals("011", Code.dest("DM"));
        Assertions.assertEquals("100", Code.dest("A"));
        Assertions.assertEquals("101", Code.dest("AM"));
        Assertions.assertEquals("110", Code.dest("AD"));
        Assertions.assertEquals("111", Code.dest("ADM"));
    }

    @Test
    public void testJump() {
        Assertions.assertEquals("000", Code.jump(null));
        Assertions.assertEquals("001", Code.jump("JGT"));
        Assertions.assertEquals("010", Code.jump("JEQ"));
        Assertions.assertEquals("011", Code.jump("JGE"));
        Assertions.assertEquals("100", Code.jump("JLT"));
        Assertions.assertEquals("101", Code.jump("JNE"));
        Assertions.assertEquals("110", Code.jump("JLE"));
        Assertions.assertEquals("111", Code.jump("JMP"));
    }

    @Test
    public void testComp() {
        Assertions.assertEquals("0101010", Code.comp("0"));
        Assertions.assertEquals("0111111", Code.comp("1"));
        Assertions.assertEquals("0111010", Code.comp("-1"));
        Assertions.assertEquals("0001100", Code.comp("D"));
        Assertions.assertEquals("0110000", Code.comp("A"));
        Assertions.assertEquals("0001101", Code.comp("!D"));
        Assertions.assertEquals("0110001", Code.comp("!A"));
        Assertions.assertEquals("0001111", Code.comp("-D"));
        Assertions.assertEquals("0110011", Code.comp("-A"));
        Assertions.assertEquals("0011111", Code.comp("D+1"));
        Assertions.assertEquals("0001110", Code.comp("D-1"));
        Assertions.assertEquals("0110010", Code.comp("A-1"));
        Assertions.assertEquals("0000010", Code.comp("D+A"));
        Assertions.assertEquals("0010011", Code.comp("D-A"));
        Assertions.assertEquals("0000111", Code.comp("A-D"));
        Assertions.assertEquals("0000000", Code.comp("D&A"));
        Assertions.assertEquals("0010101", Code.comp("D|A"));
        Assertions.assertEquals("1110000", Code.comp("M"));
        Assertions.assertEquals("1110001", Code.comp("!M"));
        Assertions.assertEquals("1110011", Code.comp("-M"));
        Assertions.assertEquals("1110111", Code.comp("M+1"));
        Assertions.assertEquals("1110010", Code.comp("M-1"));
        Assertions.assertEquals("1000010", Code.comp("D+M"));
        Assertions.assertEquals("1010011", Code.comp("D-M"));
        Assertions.assertEquals("1000000", Code.comp("D&M"));
        Assertions.assertEquals("1010101", Code.comp("D|M"));
    }
}
