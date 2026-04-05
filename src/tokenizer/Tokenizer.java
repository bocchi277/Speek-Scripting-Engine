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
}