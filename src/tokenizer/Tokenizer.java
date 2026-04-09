package tokenizer;

import java.util.*;

/**
 * Tokenizer
 *
 * Converts raw source code into tokens.
 */
public class Tokenizer {

    private final String source;
    private int pos;
    private int line;

    public Tokenizer(String source) {
        this.source = source;
        this.pos = 0;
        this.line = 1;
    }

        // Keywords (Open/Closed Principle)
    private static final Map<String, TokenType> keywords = new HashMap<>();

    // Phrases (like "is greater than")
    private static final Map<String, TokenType> phrases = new HashMap<>();

    static {
        keywords.put("let", TokenType.LET);
        keywords.put("be", TokenType.BE);
        keywords.put("say", TokenType.SAY);
        keywords.put("if", TokenType.IF);
        keywords.put("then", TokenType.THEN);
        keywords.put("repeat", TokenType.REPEAT);
        keywords.put("times", TokenType.TIMES);

        phrases.put("is greater than", TokenType.GREATER_THAN);
    }
}