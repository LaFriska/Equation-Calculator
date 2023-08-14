package com.friska.calculator;

public class Equation{

    public final EquationLinkedList equation;
    private final String equationString;

    public Equation(String equationString){
        this.equationString = equationString;
        equation = new EquationLinkedList();
        serialize();
    }

    private void serialize(){
        try {
            char[] chars = equationString.toCharArray();
            StringBuilder floatString = new StringBuilder();
            boolean possibleModifier = true;

            Character operator = null;

            for (char aChar : chars) {
                if (isOperator(aChar) && !possibleModifier) {
                    if (operator == null) {
                        equation.add(Float.parseFloat(floatString.toString()));
                    } else {
                        equation.add(operator, Float.parseFloat(floatString.toString()));
                    }
                    operator = aChar;
                    floatString = new StringBuilder();
                    possibleModifier = true;
                } else {
                    floatString.append(aChar);
                    if (possibleModifier) possibleModifier = false;
                }
            }
            assert (operator != null);
            equation.add(operator, Float.parseFloat(floatString.toString()));
        }catch (NumberFormatException e){
            throw new SyntaxException("Invalid number format.");
        }
    }

    public float calculate(){
        return getEquationLinkedList().calculate();
    }

    public static void main(String[] args) {
        Equation equation1 = new Equation("45+23/5/2/7/5/3*25*-1/2^2^2+5+2-26/67^1^1/7*23/205*2.532462256/368^1^1^1.25/23-24663+29582--2352/-23");

        System.out.println(equation1.getEquationLinkedList().calculate());
    }

    public EquationLinkedList getEquationLinkedList() {
        return equation;
    }

    private boolean isOperator(char c){
        return c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/' ||
                c == '^' ;
    }
}