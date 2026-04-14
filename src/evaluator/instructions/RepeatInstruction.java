package evaluator.instructions;
import evaluator.Environment;
import evaluator.Instruction;
import parser.Expression;
import java.util.List;

public class RepeatInstruction implements Instruction {
    private final Expression countExpression;
    private final List<Instruction> body;
    public RepeatInstruction(Expression countExpression, List<Instruction> body) {
        this.countExpression = countExpression;
        this.body = body;
    }
    @Override
    public void execute(Environment env) {
        //Evaluate loop count
        Object result = countExpression.evaluate(env);
        //Validate type
        if (!(result instanceof Double)) {
            throw new RuntimeException(
                    "Repeat count must be a number, got: " + result
            );
        }
        double value = (Double) result;
        //Validate non-negative
        if (value < 0) {
            throw new RuntimeException("Repeat count cannot be negative.");
        }
        int times = (int) value;
        for (int i = 0; i < times; i++) {
            for (Instruction instruction : body) {
                instruction.execute(env);
            }
        }
    }
}