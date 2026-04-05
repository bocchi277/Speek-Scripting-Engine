
package parser.nodes;

import parser.Expression;
import evaluator.Environment;

public class BinaryOpNode implements Expression {

    private final Expression left;
    private final Expression right;
    private final String op;

    public BinaryOpNode(Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public Object evaluate(Environment env) {
        double a = ((Number) left.evaluate(env)).doubleValue();
        double b = ((Number) right.evaluate(env)).doubleValue();

        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            case ">": return a > b;
            case "<": return a < b;
            case "==": return a == b;
        }

        throw new RuntimeException("Unknown operator: " + op);
    }
}
