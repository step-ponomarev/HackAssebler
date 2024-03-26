package edu.assembler.prarser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Parser implements Closeable {
    private final BufferedReader reader;

    private boolean eof;
    private String currInstruction;
    private String symbol;

    private String dest;

    private String comp;

    private String jump;
    private InstructionType instructionType;

    public Parser(Path file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Reader cannot be null!");
        }

        if (Files.notExists(file)) {
            throw new IllegalStateException("File does not exist: " + file);
        }

        this.reader = Files.newBufferedReader(file);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    public boolean hasMoreLines() {
        return !eof;
    }

    public void advance() {
        if (eof) {
            throw new IllegalStateException("End of file");
        }

        try {
            String instruction = readInstruction();

            final boolean firstTime = currInstruction == null;
            if (firstTime) {
                currInstruction = readInstruction();
            } else {
                final String nextInstruction = instruction;
                instruction = currInstruction;
                currInstruction = nextInstruction;
            }

            // if empty file or full comment file
            if (instruction == null && eof) {
                return;
            }

            final InstructionType type = InstructionType.parse(instruction);
            if (type == null) {
                throw new IllegalStateException("Unsupported instruction " + instruction);
            }

            switch (type) {
                case A_INSTRUCTION:
                    handleSymbol(instruction);
                    break;
                case C_INSTRUCTION:
                    handleCInstruction(instruction);
                default:
                    throw new UnsupportedOperationException("Unsupported instruction " + instructionType);
            }

            instructionType = type;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readInstruction() throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            currentLine = currentLine.trim();
            if (currentLine.isEmpty()) {
                continue;
            }

            if (TokenPatterns.COMMENT.matcher(currentLine).matches()) {
                continue;
            }

            return currentLine;
        }
        
        eof = true;

        return null;
    }

    private void handleCInstruction(String currentLine) {
        if (TokenPatterns.JMP_INSTRUCTION.matcher(currentLine).matches()) {
            handleJump(currentLine);
        } else if (TokenPatterns.ASSIGN_INSTRUCTION.matcher(currentLine).matches()) {
            handleAssign(currentLine);
        } else if (TokenPatterns.LABEL_INSTRUCTION.matcher(currentLine).matches()) {
            throw new UnsupportedOperationException("Unsupported instruction");
        }

        symbol = currentLine.substring(1);
        dest = null;
        comp = null;
        jump = null;
    }

    private void handleJump(String currentLine) {

    }

    private void handleAssign(String currentLine) {

    }

    private void handleLabel(String currentLine) {

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
