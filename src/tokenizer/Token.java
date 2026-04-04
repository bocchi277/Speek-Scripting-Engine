package tokenizer;
 /* IMPORTANT DESIGN DECISION:
 * This class is IMMUTABLE — once created, nothing can be changed.
 * There are NO setters, only getters.
 * This is intentional: a token should never change after it is created.
 */

public class Token {

    private final TokenType type;   //specify Token type
    private final String value;     // The exact text from the source code (e.g. "let", "42")
    private final int line;         // Line number in the source file where this token appeare
    /* 
    * @param type  The category of this token (e.g. NUMBER, IDENTIFIER, KEYWORD_LET)
    * @param value The raw text of this token as it appeared in source code
    * @param line  The line number where this token was found (starts at 1)
    */
       public Token(TokenType type, String value, int line) {
        this.type = type;
        this.value = value;
        this.line = line;
    }
    //Getters method is the only way to read fields from outside

        public TokenType getType(){
            return type;

        }
    //return raw text as it appears in the source code
    //ex-['let','x','10']

    public String getValue(){
        return value;
    }

    //returns the line number where the token was found
    //helps in telling error msg on specific line
    public int getLine(){
        return line;
    }

    //to string -very helpul for debugging purpuse
    //returns a readable description of this token
    //* Example output:  Token[LINE:1  TYPE:KEYWORD_LET  VALUE:"let"]

    @Override
    public String toString(){
        return "Token[LINE:" + line + "  TYPE:" + type + "  VALUE:\"" + value + "\"]";
    }


}


