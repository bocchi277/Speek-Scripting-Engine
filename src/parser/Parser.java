package parser;

import evaluator.*;
import evaluator.instructions.*;
import java.util.*;
import parser.nodes.*;
import tokenizer.*;

public class Parser {

    // List of tokens from tokenizer
    private final List<Token> tokens;

    // Current position in token list
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Entry point: parse full program
    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        while (!check(TokenType.EOF)) {

            // Skip empty lines
            if (match(TokenType.NEWLINE)) continue;

            instructions.add(parseStatement());
        }

        return instructions;
    }

    // Decide which statement to parse
    private Instruction parseStatement() {

        if (match(TokenType.LET)) return parseAssign();
        if (match(TokenType.SAY)) return parsePrint();
        if (match(TokenType.IF)) return parseIf();
        if (match(TokenType.REPEAT)) return parseRepeat();

        throw error("Invalid statement");
    }

    // let x be expression
    private Instruction parseAssign() {
        String name = consume(TokenType.IDENTIFIER).value();
        consume(TokenType.BE);

        Expression expr = parseExpression();

        return new AssignInstruction(name, expr);
    }

    // say expression
    private Instruction parsePrint() {
        Expression expr = parseExpression();
        return new PrintInstruction(expr);
    }

    // if condition then (block)
    private Instruction parseIf() {

        Expression condition = parseExpression();

        consume(TokenType.THEN);
        consume(TokenType.NEWLINE);
        consume(TokenType.INDENT);

        List<Instruction> body = new ArrayList<>();

        // Read block until DEDENT
        while (!check(TokenType.DEDENT) && !check(TokenType.EOF)) {

            if (match(TokenType.NEWLINE)) continue;

            body.add(parseStatement());
        }

        consume(TokenType.DEDENT);

        return new IfInstruction(condition, body);
    }

    // repeat expression times (block)
    private Instruction parseRepeat() {

        Expression countExpr = parseExpression();

        consume(TokenType.TIMES);
        consume(TokenType.NEWLINE);
        consume(TokenType.INDENT);

        List<Instruction> body = new ArrayList<>();

        while (!check(TokenType.DEDENT) && !check(TokenType.EOF)) {

            if (match(TokenType.NEWLINE)) continue;

            body.add(parseStatement());
        }

        consume(TokenType.DEDENT);

        return new RepeatInstruction(countExpr, body);
    }

    // Handle + - > < ==
    private Expression parseExpression() {

        Expression expr = parseTerm();

        while (match(TokenType.PLUS) ||
               match(TokenType.MINUS) ||
               match(TokenType.GREATER_THAN) ||
               match(TokenType.LESS_THAN) ||
               match(TokenType.EQUALS)) {

            String op = previous().value();

            Expression right = parseTerm();

            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    // Handle * /
    private Expression parseTerm() {

        Expression expr = parsePrimary();

        while (match(TokenType.MUL) || match(TokenType.DIV)) {

            String op = previous().value();

            Expression right = parsePrimary();

            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    // Handle literals and unary minus
    private Expression parsePrimary() {

        // Handle Unary minus (negative numbers)
        if (match(TokenType.MINUS)) {
            Expression right = parsePrimary();
            return new BinaryOpNode(new NumberNode(0), "-", right);
        }

        if (match(TokenType.NUMBER))
            return new NumberNode(Double.parseDouble(previous().value()));

        if (match(TokenType.STRING))
            return new StringNode(previous().value());

        if (match(TokenType.IDENTIFIER))
            return new VariableNode(previous().value());

        throw error("Invalid expression");
    }

    // Match token and move forward
    private boolean match(TokenType type) {
        if (check(type)) {
            pos++;
            return true;
        }
        return false;
    }

    // Check current token safely
    private boolean check(TokenType type) {
        if (pos >= tokens.size()) return false;
        return tokens.get(pos).type() == type;   
    }

    // Ensure expected token
    private Token consume(TokenType type) {
        if (check(type)) return tokens.get(pos++);
        throw error("Expected " + type);
    }

    // Get previous token
    private Token previous() {
        return tokens.get(pos - 1);
    }

    // Error with line number
    private RuntimeException error(String msg) {
        int line = (pos < tokens.size()) ? tokens.get(pos).line() : -1;
        return new RuntimeException(msg + " at line " + line);
    }
}
