package com.friska.calculator;

public class SyntaxException extends RuntimeException{

    public SyntaxException(String msg){
        super("An syntax error had occurred. " + msg);

    }
}
