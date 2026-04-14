package parser;

import evaluator.Environment;

/**
 * Expression interface represents any calculatable value in the language.
 * 
 * Examples of expressions:
 * - Numbers → 5, -10, 3.14
 * - Strings → "hello"
 * - Variables → x, y
 * - Operations → x + 5, a > b
 * 
 * Every expression must implement this interface and provide
 * logic to evaluate itself using the current environment.
 */
public interface Expression {

    /**
     * Evaluates the expression and returns the result.
     * 
     * @param env The current environment storing variable values
     * @return The result of evaluation (can be Number, String, Boolean, etc.)
     */
    Object evaluate(Environment env);
}
