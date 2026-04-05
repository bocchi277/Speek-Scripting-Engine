package parser.nodes;

import parser.Expression;
import evaluator.Environment;

public class NumberNode implements Expression {
    private final double value;
    public NumberNode(double value) { this.value = value; }

    public Object evaluate(Environment env) {
        return value;
    }
}

