package evaluator.instructions;
import evaluator.Environment;
import evaluator.Instruction;
import parser.Expression;
import java.util.List;

public class IfInstruction implements Instruction {
    private final Expression condition;
    private final List<Instruction> body;

    public IfInstruction(Expression condition, List<Instruction> body) {
        this.condition = condition;
        this.body = body;
    }
    @Override
    public void execute(Environment env) {
        // Evaluate condition
        Object result = condition.evaluate(env);

        // Validate that result is boolean
        if (!(result instanceof Boolean)) {
            throw new RuntimeException(
                    "Condition must evaluate to a boolean, got: " + result
            );
        }

        boolean isTrue = (Boolean) result;
        // Execute body if condition is true
        if (isTrue) {
            for (Instruction instruction : body) {
                instruction.execute(env);
            }
        }
    }
}