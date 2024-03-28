package edu.assembler.prarser;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import edu.assembler.Constants;
import edu.assembler.exceptions.NumberOutOfRangeException;

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
            String instruction = readNextInstruction();

            final boolean firstTime = currInstruction == null;
            if (firstTime) {
                currInstruction = readNextInstruction();
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

            symbol = null;
            dest = null;
            comp = null;
            jump = null;
            instructionType = type;

            switch (type) {
                case A_INSTRUCTION -> handleAInstruction(instruction);
                case C_INSTRUCTION -> handleCInstruction(instruction);
                default -> throw new UnsupportedOperationException("Unsupported instruction " + type);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String readNextInstruction() throws IOException {
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            currentLine = currentLine.replaceAll("\s", "");
            if (currentLine.isEmpty() || TokenPatterns.COMMENT.matcher(currentLine).matches()) {
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
    }

    private void handleJump(String currentLine) {
        final String[] split = currentLine.split(";");
        dest = split[0];
        jump = split[1];
    }

    private void handleAssign(String currentLine) {
        final String[] split = currentLine.split("=");
        dest = split[0];
        comp = split[1];
    }

    private void handleAInstruction(String currentLine) {
        final String number = currentLine.substring(1);

        if (Integer.parseInt(number) > Constants.MAX_DECIMAL_VALUE) {
            throw new NumberOutOfRangeException("Number " + number + " is out of range [0, " + Constants.MAX_DECIMAL_VALUE + "]");
        }

        symbol = number;
    }

    public InstructionType instructionType() {
        return instructionType;
    }

    public String symbol() {
        return symbol;
    }

    public String dest() {
        return dest;
    }

    public String comp() {
        return comp;
    }

    public String jump() {
        return jump;
    }
}
