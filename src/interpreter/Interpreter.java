package interpreter;
import evaluator.Environment;
import evaluator.Instruction;
import parser.Parser;
import tokenizer.Token;
import tokenizer.Tokenizer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
public class Interpreter {
    public void run(String sourceCode) {
        //Tokenization
        Tokenizer tokenizer = new Tokenizer(sourceCode);
        List<Token> tokens = tokenizer.tokenize();
        //Parsing
        Parser parser = new Parser(tokens);
        List<Instruction> program = parser.parse();
        //Execution
        Environment env = new Environment();

        for (Instruction instruction : program) {
            instruction.execute(env);
        }
    }
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java interpreter.Interpreter <path_to_file.speek>");
            System.exit(1);
        }
        try {
            String sourceCode = Files.readString(Paths.get(args[0]));
            Interpreter engine = new Interpreter();
            engine.run(sourceCode);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Runtime Error: " + e.getMessage());
        }
    }
}