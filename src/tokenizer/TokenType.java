package src.tokenizer;
//Speek keywords : let,be,say,if,then,repeat,times
//Special phrases :"is greater than" collapses into GREATER_THAN

public enum TokenType {
    //Literals
    NUMBER, //eg. 10,3.14
    STRING, //eg "hello"
    IDENTIFIER, //eg. x,score,result

    //Arithmetic operators
    PLUS, //+
    MINUS, //-
    MUL,  //* 
    DIV, // /

    //Comparison operators
    GREATER_THAN, //> or the phrase "is greater than"
    LESS_THAN, //< or "is less than"
    EQUALS, // ==

    //SPEEK keywords
    LET, //let
    BE, //be
    SAY, //say
    IF, //if
    THEN, //then
    REPEAT, //repeat
    TIMES, //times
    IS, //is
    GREATER, //greater(intermediate)
    THAN, //than

    //Structure
    NEWLINE, //end of a logical line
    EOF //end of source



    
}
