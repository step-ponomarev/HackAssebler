package edu.assembler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Constants {
    public static final int INSTRUCTION_LENGTH = 16;
    public static final int MAX_DECIMAL_VALUE = 0b111111111111111;
    public static final Map<String, Integer> DEFAULT_LABEL_TO_ADDRESS;
    public static final int VARIABLE_START_ADDRESS = 16;
    
    public static final String C_INSTRUCTION_START_CODE = "111";

    static {
        final Map<String, Integer> labels = new HashMap<>();
        for (int i = 0; i <= 15; i++) {
            labels.put("R" + i, i);
        }

        labels.put("SP", 0);
        labels.put("LCL", 1);
        labels.put("ARG", 2);
        labels.put("THIS", 3);
        labels.put("THAT", 4);
        labels.put("SCREEN", 16384);
        labels.put("KBD", 24576);

        DEFAULT_LABEL_TO_ADDRESS = Collections.unmodifiableMap(labels);
    }
    private Constants() {}
}
