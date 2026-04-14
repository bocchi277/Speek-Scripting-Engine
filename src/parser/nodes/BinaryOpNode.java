package parser.nodes;

import parser.Expression;
import evaluator.Environment;

/**
 * BinaryOpNode represents a binary operation between two expressions.
 * 
 * Examples:
 * - Arithmetic → x + y, a * b
 * - Comparison → x > y, a < b
 * - Equality → x == y
 * - Logical → true and false
 * - String concatenation → "Hello " + name
 */
public class BinaryOpNode implements Expression {

    // Left and right operands
    private final Expression left;
    private final Expression right;

    // Operator (+, -, *, /, >, <, ==, and, or)
    private final String op;

    public BinaryOpNode(Expression left, String op, Expression right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public Object evaluate(Environment env) {

        // Evaluate left and right expressions first
        Object leftVal = left.evaluate(env);
        Object rightVal = right.evaluate(env);

        //  ADDITION 
        if (op.equals("+")) {

            // String concatenation if either operand is string
            if (leftVal instanceof String || rightVal instanceof String) {
                return String.valueOf(leftVal) + String.valueOf(rightVal);
            }

            // Numeric addition
            if (leftVal instanceof Number && rightVal instanceof Number) {
                double a = ((Number) leftVal).doubleValue();
                double b = ((Number) rightVal).doubleValue();
                return a + b;
            }

            // Invalid types
            throw new RuntimeException("Invalid operands for +: " + leftVal + ", " + rightVal);
        }

        //  ARITHMETIC 
        if (op.equals("-") || op.equals("*") || op.equals("/")) {

            // Only numbers allowed
            if (!(leftVal instanceof Number) || !(rightVal instanceof Number)) {
                throw new RuntimeException("Arithmetic requires numbers. Got: " + leftVal + ", " + rightVal);
            }

            double a = ((Number) leftVal).doubleValue();
            double b = ((Number) rightVal).doubleValue();

            switch (op) {
                case "-": return a - b;   // subtraction
                case "*": return a * b;   // multiplication

                case "/":
                    // Prevent division by zero
                    if (b == 0) throw new RuntimeException("Division by zero");
                    return a / b;
            }
        }

        //  COMPARISON 
        if (op.equals(">") || op.equals("<")) {

            // Only numbers allowed
            if (!(leftVal instanceof Number) || !(rightVal instanceof Number)) {
                throw new RuntimeException("Comparison requires numbers. Got: " + leftVal + ", " + rightVal);
            }

            double a = ((Number) leftVal).doubleValue();
            double b = ((Number) rightVal).doubleValue();

            // Return boolean result
            if (op.equals(">")) return a > b;
            if (op.equals("<")) return a < b;
        }

        //  EQUALITY 
        if (op.equals("==")) {
            // Works for all types (number, string, boolean)
            return leftVal.equals(rightVal);
        }

        //  LOGICAL 
        if (op.equals("and") || op.equals("or")) {

            // Only boolean values allowed
            if (!(leftVal instanceof Boolean) || !(rightVal instanceof Boolean)) {
                throw new RuntimeException("Logical operations require booleans. Got: " + leftVal + ", " + rightVal);
            }

            boolean a = (Boolean) leftVal;
            boolean b = (Boolean) rightVal;

            // Logical AND / OR
            if (op.equals("and")) return a && b;
            if (op.equals("or")) return a || b;
        }

        // Unknown operator
        throw new RuntimeException("Unknown operator: " + op);
    }
}
