package parser;

import evaluator.*;
import evaluator.instructions.*;
import java.util.*;
import parser.nodes.*;
import tokenizer.*;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Instruction> parse() {
        List<Instruction> instructions = new ArrayList<>();

        while (!check(TokenType.EOF)) {
            if (match(TokenType.NEWLINE)) continue;
            instructions.add(parseStatement());
        }

        return instructions;
    }

    private Instruction parseStatement() {

        if (match(TokenType.LET)) return parseAssign();
        if (match(TokenType.SAY)) return parsePrint();
        if (match(TokenType.IF)) return parseIf();
        if (match(TokenType.REPEAT)) return parseRepeat();

        throw new RuntimeException("Invalid statement");
    }

    private Instruction parseAssign() {
        String name = consume(TokenType.IDENTIFIER).getValue();
        consume(TokenType.BE);

        Expression expr = parseExpression();
        return new AssignInstruction(name, expr);
    }

    private Instruction parsePrint() {
        Expression expr = parseExpression();
        return new PrintInstruction(expr);
    }

    private Instruction parseIf() {
        Expression condition = parseExpression();
        consume(TokenType.THEN);

        List<Instruction> body = new ArrayList<>();

        if (match(TokenType.NEWLINE)) {
            while (!check(TokenType.EOF)) {
                if (check(TokenType.LET) || check(TokenType.SAY)) {
                    body.add(parseStatement());
                } else break;
            }
        }

        return new IfInstruction(condition, body);
    }

    private Instruction parseRepeat() {
        Expression countExpr = parseExpression();
        consume(TokenType.TIMES);

        List<Instruction> body = new ArrayList<>();

        if (match(TokenType.NEWLINE)) {
            while (!check(TokenType.EOF)) {

                if (match(TokenType.NEWLINE)) continue;

                if (check(TokenType.LET) || 
                    check(TokenType.SAY) || 
                    check(TokenType.IF) || 
                    check(TokenType.REPEAT)) {

                    body.add(parseStatement());
                } else break;
            }
        }

        return new RepeatInstruction(countExpr, body);
    }

    private Expression parseExpression() {
        Expression expr = parseTerm();

        while (match(TokenType.PLUS) ||
               match(TokenType.MINUS) ||
               match(TokenType.GREATER_THAN) ||
               match(TokenType.LESS_THAN) ||
               match(TokenType.EQUALS)) {

            String op = previous().getValue();
            Expression right = parseTerm();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private Expression parseTerm() {
        Expression expr = parsePrimary();

        while (match(TokenType.MUL) || match(TokenType.DIV)) {
            String op = previous().getValue();
            Expression right = parsePrimary();
            expr = new BinaryOpNode(expr, op, right);
        }

        return expr;
    }

    private Expression parsePrimary() {

        if (match(TokenType.NUMBER))
            return new NumberNode(Double.parseDouble(previous().getValue()));

        if (match(TokenType.STRING))
            return new StringNode(previous().getValue());

        if (match(TokenType.IDENTIFIER))
            return new VariableNode(previous().getValue());

        throw new RuntimeException("Invalid expression");
    }

    private boolean match(TokenType type) {
        if (check(type)) {
            pos++;
            return true;
        }
        return false;
    }

    private boolean check(TokenType type) {
        return tokens.get(pos).getType() == type;
    }

    private Token consume(TokenType type) {
        if (check(type)) return tokens.get(pos++);
        throw new RuntimeException("Expected " + type);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }
}
