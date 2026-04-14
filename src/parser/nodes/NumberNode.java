package parser.nodes;

import parser.Expression;
import evaluator.Environment;

/**
 * NumberNode represents a numeric value in the AST (Abstract Syntax Tree).
 * 
 * Examples:
 * - 5
 * - 10.5
 * - -3  (negative numbers are handled via parser as 0 - 3)
 * 
 * This node simply stores a number and returns it during evaluation.
 */
public class NumberNode implements Expression {

    // Stores the numeric value (double supports integers + decimals)
    private final double value;

    // Constructor to initialize number
    public NumberNode(double value) {
        this.value = value;
    }

    /**
     * Evaluates the number node.
     * 
     * Since this is a constant value, it simply returns itself.
     * 
     * @param env Environment (not used here, but required by interface)
     * @return numeric value
     */
    public Object evaluate(Environment env) {
        return value;
    }
}
