package com.friska.calculator;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator("(3*(4-(4-4-2-6-7-2+-23.53+-235*-236.25)-(45*(252-5.457*-23/(65-5.34-5-5-5-5/2))+10-23/(234-5)))*(3.14159256+3+3+2.78/9-2+25-26/23/6/2/1*46+23-24+222-(567-2-5*2*2*5/4/4*2))/(4-2+-2-2-3.36)/(45-64+(23.236323*56)*-12/(42/(23+23)-(-245+7*2))))");
        System.out.println(calculator.getResult());
    }
}
