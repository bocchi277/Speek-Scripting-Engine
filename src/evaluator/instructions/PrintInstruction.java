package evaluator.instructions;
import evaluator.Environment;
import evaluator.Instruction;
import parser.Expression;

public class PrintInstruction implements Instruction {
    private final Expression expression;
    public PrintInstruction(Expression expression) {
        this.expression = expression;
    }
    @Override
    public void execute(Environment env) {
        Object result = this.expression.evaluate(env);

        // Handle null safely
        if (result == null) {
            throw new RuntimeException("Cannot print null value");
        }

        //Format output (avoid printing 10.0 instead of 10)
        if (result instanceof Double) {
            double value = (Double) result;
            if (value == (int) value) {
                System.out.println((int) value); // print as integer
            } else {
                System.out.println(value); // print decimal
            }
        } else {
            // Step 4: Print strings or other types directly
            System.out.println(result);
        }
    }
}