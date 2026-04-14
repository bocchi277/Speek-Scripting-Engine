package evaluator.instructions;
import evaluator.Environment;
import evaluator.Instruction;
import parser.Expression;
public class AssignInstruction implements Instruction {
    private final String variableName;
    private final Expression expression;

    public AssignInstruction(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public void execute(Environment env){
        Object value = this.expression.evaluate(env);
        //For safety
        if (value == null) {
            throw new RuntimeException("Cannot assign null to " + variableName);
        }
        env.set(this.variableName, value);
    }

}