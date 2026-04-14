package parser.nodes;

import parser.Expression;
import evaluator.Environment;

/**
 * Represents a variable reference.
 * 
 * Example:
 * - x
 * 
 * Fetches value from environment at runtime.
 */
public class VariableNode implements Expression {

    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    /**
     * Gets variable value from environment.
     */
    public Object evaluate(Environment env) {

        Object value = env.get(name);

        // Optional safety check
        if (value == null) {
            throw new RuntimeException("Undefined variable: " + name);
        }

        return value;
    }
}
