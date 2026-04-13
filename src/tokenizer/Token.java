package tokenizer;

/**
 * Token represents a small unit of code after tokenization.
 * 
 * It stores:
 * - type  → what kind of token it is
 * - value → actual text from the source
 * - line  → line number (for error messages)
 * 
 * This is a record, so it is immutable by default.
 */
public record Token(TokenType type, String value, int line) {

    // Helps in debugging by printing token in readable form
    @Override
    public String toString() {
        return "Token[LINE:" + line + "  TYPE:" + type + "  VALUE:\"" + value + "\"]";
    }
}