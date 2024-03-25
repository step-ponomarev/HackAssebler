package edu.assembler.prarser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

public final class Parser implements Closeable {
    private final BufferedReader reader;
    private boolean eof;
    private String symbol;

    private String dest;

    private String comp;

    private String jump;
    private InstructionType instructionType;

    public Parser(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("Reader cannot be null!");
        }

        this.reader = reader;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public boolean hasMoreLines() {
        return !eof;
    }

    public void advance() {
        try {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                final InstructionType type = InstructionType.parse(currentLine.trim());
                if (type == null) {
                    continue;
                }

                instructionType = type;
                break;
            }

            if (currentLine == null) {
                eof = true;
                return;
            }
            
            switch (instructionType) {
                case A_INSTRUCTION:
                    handleSymbol(currentLine);
                    break;
                case C_INSTRUCTION:
                    handleCInstruction(currentLine);
                default:
                    throw new UnsupportedOperationException("Unsupported instruction " + instructionType);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCInstruction(String currentLine) {
        if (TokenPatterns.JMP_INSTRUCTION.matcher(currentLine).matches()) {
            return;
        }
        
        symbol = currentLine.substring(1);
        dest = null;
        comp = null;
        jump = null;
    }
    
    private void handleJump(String currentLine) {
        
    }

    private void handleSymbol(String currentLine) {
        symbol = currentLine.substring(1);
        dest = null;
        comp = null;
        jump = null;
    }

    public InstructionType instructionType() {
        return instructionType;
    }

    public String symbol() {
        return symbol;
    }

    public String dest() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String comp() {
        throw new UnsupportedOperationException("Not implemented");
    }

    public String jump() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
