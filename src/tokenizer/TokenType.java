package tokenizer;

import java.util.HashMap;
import java.util.Map;

/**
 * This Enum defines every type of "word" or "symbol" our language understands.
 * To follow SOLID, we've moved the keyword lookup logic here so the Tokenizer
 * doesn't have to manage a giant list of strings.
 */
public enum TokenType {
    // Basic building blocks
    NUMBER, STRING, IDENTIFIER,

    // Math operations
    PLUS, MINUS, MUL, DIV,

    // Comparisons
    GREATER_THAN, LESS_THAN, EQUALS,

    // SPEEK language keywords [cite: 35, 38]
    LET, BE, SAY, IF, THEN, REPEAT, TIMES, IS,

    // Control tokens
    NEWLINE, EOF;

    // A simple map to quickly find if a word is a keyword or just a variable name
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("let", LET);
        KEYWORDS.put("be", BE);
        KEYWORDS.put("say", SAY);
        KEYWORDS.put("if", IF);
        KEYWORDS.put("then", THEN);
        KEYWORDS.put("repeat", REPEAT);
        KEYWORDS.put("times", TIMES);
        KEYWORDS.put("is", IS);
    }

    /**
     * SRP: This method handles the responsibility of "classifying" a word.
     * If it's in our map, it's a keyword; otherwise, it's an IDENTIFIER[cite: 64].
     */
    public static TokenType lookup(String word) {
        return KEYWORDS.getOrDefault(word.toLowerCase(), IDENTIFIER);
    }
}