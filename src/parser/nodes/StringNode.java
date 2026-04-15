package parser.nodes;

import parser.Expression;
import evaluator.Environment;

/**
 * StringNode represents a string literal in the AST (Abstract Syntax Tree).
 * 
 * Examples:
 * - "hello"
 * - "world"
 * 
 * This node stores a string value and returns it during evaluation.
 */
public class StringNode implements Expression {

    // Stores the string value
    private final String value;

    // Constructor to initialize string
    public StringNode(String value) {
        this.value = value;
    }

    /**
     * Evaluates the string node.
     * 
     * Since this is a constant value, it simply returns the string.
     * 
     * @param env Environment (not used here, but required by interface)
     * @return string value
     */
    public Object evaluate(Environment env) {
        return value;
    }
}
