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

    @Override
    public Object evaluate(Environment env) {

        Object leftVal = left.evaluate(env);
        Object rightVal = right.evaluate(env);

        if (op.equals("+")) {

            // String concatenation
            if (leftVal instanceof String || rightVal instanceof String) {
                return String.valueOf(leftVal) + String.valueOf(rightVal);
            }

            // Numeric addition
            if (leftVal instanceof Number && rightVal instanceof Number) {
                double a = ((Number) leftVal).doubleValue();
                double b = ((Number) rightVal).doubleValue();
                return a + b;
            }

            throw new RuntimeException("Invalid operands for +: " + leftVal + ", " + rightVal);
        }

        if (op.equals("-") || op.equals("*") || op.equals("/")) {

            if (!(leftVal instanceof Number) || !(rightVal instanceof Number)) {
                throw new RuntimeException("Arithmetic requires numbers. Got: " + leftVal + ", " + rightVal);
            }

            double a = ((Number) leftVal).doubleValue();
            double b = ((Number) rightVal).doubleValue();

            switch (op) {
                case "-": return a - b;
                case "*": return a * b;
                case "/":
                    if (b == 0) throw new RuntimeException("Division by zero");
                    return a / b;
            }
        }

        if (op.equals(">") || op.equals("<")) {

            if (!(leftVal instanceof Number) || !(rightVal instanceof Number)) {
                throw new RuntimeException("Comparison requires numbers. Got: " + leftVal + ", " + rightVal);
            }

            double a = ((Number) leftVal).doubleValue();
            double b = ((Number) rightVal).doubleValue();

            if (op.equals(">")) return a > b;
            if (op.equals("<")) return a < b;
        }

        if (op.equals("==")) {
            return leftVal.equals(rightVal);
        }

        if (op.equals("and") || op.equals("or")) {

            if (!(leftVal instanceof Boolean) || !(rightVal instanceof Boolean)) {
                throw new RuntimeException("Logical operations require booleans. Got: " + leftVal + ", " + rightVal);
            }

            boolean a = (Boolean) leftVal;
            boolean b = (Boolean) rightVal;

            if (op.equals("and")) return a && b;
            if (op.equals("or")) return a || b;
        }

        throw new RuntimeException("Unknown operator: " + op);
    }
}
