package com.friska.calculator;

import java.util.Stack;

public class Calculator {

    private String equationString;

    public Calculator(String equationString){
        this.equationString = equationString;
        checkBracketSyntax();
        run();
    }

    private void checkBracketSyntax(){
        int bracketStacker = 0;

        for(int i = 0; i < equationString.length(); i++){
            if(equationString.charAt(i) == '(') bracketStacker++;
            else if(equationString.charAt(i) == ')') bracketStacker --;
        }

        if(bracketStacker != 0) throw new SyntaxException("Open brackets cannot be" + (bracketStacker >0 ? "more than " : "fewer than ") + "closing brackets.");

    }

    private void run(){
        StringBuilder subequation = new StringBuilder();
        char current;

        Stack<Integer> openBrackets = new Stack<>();

        //while(equationString.contains("(") || equationString.contains(")")) {
            for (int i = 0; i < equationString.length(); i++) {
                current = equationString.charAt(i);
                if (current != '(') {
                    if (current != ')') {
                        subequation.append(current);
                    } else {
                        if (openBrackets.empty()) throw new SyntaxException("Cannot process closing bracket without open ones.");
                        int pop = openBrackets.pop();
                        i = chopAndGetIndex(String.valueOf(new Equation(equationString.substring(pop + 1, i)).calculate()), pop, i, i);
                    }
                } else {
                    openBrackets.push(i);
                    subequation = new StringBuilder();
                }
                //System.out.println(openBrackets.peek());
            }
        }
        //equationString = String.valueOf(new Equation(equationString).calculate());
    //}

    private int chopAndGetIndex(String replacer, int f, int l, int index){
        equationString = equationString.substring(0, f) + replacer + equationString.substring(l + 1);
        return index - l + f + replacer.length() - 1;
    }

    public String getResult() {
        return equationString;
    }
}
