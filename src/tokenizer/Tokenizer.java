package tokenizer;

import java.util.*;

/**
 * Tokenizer — reads raw source code character by character
 * and breaks it into a list of tokens (words, numbers, symbols, etc.)
 * that the parser can understand.
 */
public class Tokenizer {

    private final String source;  // the full source code string
    private int pos;              // current character position
    private int line;             // current line number (for error reporting)
    private Deque<Integer> indentStack;

    public Tokenizer(String source) {
        this.source = source;
        this.pos = 0;
        this.line = 1;
        this.indentStack = new ArrayDeque<>();//// Using Deque instead of Stack because it is a modern and faster alternative.
        indentStack.push(0);
    }

    // Reserved words the language understands (e.g. "if", "let", "repeat")
    private static final Map<String, TokenType> keywords = new HashMap<>();

    // Multi-word expressions treated as a single token (e.g. "is greater than")
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

    /**
     * Main method — scans the entire source and returns all tokens in order.
     * Each character is checked and routed to the right reader method.
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (pos < source.length()) {

            skipWhitespace();

            if (pos >= source.length()) break;

            char c = source.charAt(pos);

            // Newline marks the end of a statement — add token once, then move to next line
        if (c == '\n') {
                // Step 1: Add NEWLINE (same as before)
                if (!tokens.isEmpty() &&
                    tokens.get(tokens.size() - 1).type() != TokenType.NEWLINE) {
                tokens.add(createToken(TokenType.NEWLINE, "\\n", line));
                }

            line++;
            pos++;

            // Step 2: Count spaces at start of next line
            int currentIndent = 0;
            while (pos < source.length() && (source.charAt(pos) == ' '||source.charAt(pos)=='\t')) {
                currentIndent++;
                pos++;
            }

            int previousIndent = indentStack.peek();

            // Step 3: Compare indentation
            if (currentIndent > previousIndent) {
                indentStack.push(currentIndent);
                tokens.add(createToken(TokenType.INDENT, "", line));
                
            } else {
            while (currentIndent < previousIndent) {
                indentStack.pop();
                previousIndent = indentStack.peek();
                tokens.add(createToken(TokenType.DEDENT, "", line));
                }
            }

            continue;
        }

            // Windows line ending — skip \r, the \n after it will be handled above
            if (c == '\r') {
                pos++;
                continue;
            }

            // Each character type is handled by a dedicated reader
            if (c == '"') {
                tokens.add(readString());
                continue;
            }

            if (Character.isDigit(c)) {
                tokens.add(readNumber());
                continue;
            }

            if (Character.isLetter(c) || c == '_') {
                tokens.addAll(readWordOrPhrase());
                continue;
            }

            handleOperator(tokens, c);
        }
        while (indentStack.size() > 1) {
                indentStack.pop();
                tokens.add(createToken(TokenType.DEDENT, "", line));
        }

        // EOF signals the parser that there's nothing left to read
        tokens.add(createToken(TokenType.EOF, "", line));
        return List.copyOf(tokens);
    }

    // Skips spaces and tabs between tokens — they don't affect meaning
    private void skipWhitespace() {
        while (pos < source.length()) {
            char c = source.charAt(pos);
            if (c == ' ' || c == '\t') pos++;
            else break;
        }
    }

    private Token createToken(TokenType type, String value, int line) {
        return new Token(type, value, line);
    }

    /**
     * Reads a quoted string like "hello world".
     * Handles escape sequences: \n (newline), \t (tab), \" (quote), \\ (backslash).
     * Throws an error if the string is never closed.
     */
    private Token readString() {
        int startLine = line;
        pos++; // move past the opening quote

        StringBuilder sb = new StringBuilder();

        while (pos < source.length() && source.charAt(pos) != '"') {
            char c = source.charAt(pos);

            if (c == '\\' && pos + 1 < source.length()) {
                pos++;
                char next = source.charAt(pos);

                switch (next) {
                    case 'n': sb.append('\n'); break;
                    case 't': sb.append('\t'); break;
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    default: sb.append(next);
                }
            } else {
                sb.append(c);
            }
            pos++;
        }

        if (pos >= source.length()) {
            throw new RuntimeException("Unterminated string at line " + startLine);
        }

        pos++; // move past the closing quote
        return createToken(TokenType.STRING, sb.toString(), startLine);
    }

    /**
     * Reads an integer or decimal number (e.g. 42 or 3.14).
     * Only one decimal point is allowed — the second dot stops reading.
     */
    private Token readNumber() {
        int start = pos;
        int startLine = line;
        boolean dot = false;

        while (pos < source.length()) {
            char c = source.charAt(pos);

            if (Character.isDigit(c)) pos++;
            else if (c == '.' && !dot) {
                dot = true;
                pos++;
            } else break;
        }

        return createToken(TokenType.NUMBER, source.substring(start, pos), startLine);
    }

    /**
     * Reads a word and decides what it is:
     * 1. First checks if it starts a known phrase (e.g. "is greater than")
     * 2. Then checks if it's a keyword (e.g. "if", "let")
     * 3. Otherwise treats it as a user-defined identifier (variable/function name)
     */
    private List<Token> readWordOrPhrase() {
        List<Token> result = new ArrayList<>();

        int start = pos;
        int startLine = line;

        while (pos < source.length() &&
                (Character.isLetterOrDigit(source.charAt(pos)) || source.charAt(pos) == '_')) {
            pos++;
        }

        String word = source.substring(start, pos);

        for (String phrase : phrases.keySet()) {
            if (source.startsWith(phrase, start)) {
                pos = start + phrase.length();
                result.add(createToken(phrases.get(phrase), phrase, startLine));
                return result;
            }
        }

        result.add(createToken(classify(word), word, startLine));
        return result;
    }

    // Looks up the word in keywords map — returns IDENTIFIER if not found
    private TokenType classify(String word) {
        return keywords.getOrDefault(word.toLowerCase(), TokenType.IDENTIFIER);
    }

    /**
     * Handles single-character operators (+, -, *, /, <, >)
     * and the two-character equality check (==).
     * A lone "=" is skipped — it has no meaning in this language yet.
     * Unknown characters print a warning but don't crash the tokenizer.
     */
    private void handleOperator(List<Token> tokens, char c) {
        switch (c) {
            case '+':
                tokens.add(createToken(TokenType.PLUS, "+", line));
                pos++;
                break;

            case '-':
                tokens.add(createToken(TokenType.MINUS, "-", line));
                pos++;
                break;

            case '*':
                tokens.add(createToken(TokenType.MUL, "*", line));
                pos++;
                break;

            case '/':
                tokens.add(createToken(TokenType.DIV, "/", line));
                pos++;
                break;

            case '>':
                tokens.add(createToken(TokenType.GREATER_THAN, ">", line));
                pos++;
                break;

            case '<':
                tokens.add(createToken(TokenType.LESS_THAN, "<", line));
                pos++;
                break;

            case '=':
                if (pos + 1 < source.length() && source.charAt(pos + 1) == '=') {
                    tokens.add(createToken(TokenType.EQUALS, "==", line));
                    pos += 2;
                } else {
                    pos++;
                }
                break;

            default:
                System.err.println("Warning: unexpected char '" + c + "' at line " + line);
                pos++;
        }
    }
}