package br.com.mayara.math;

public interface SimpleMath {
    static double sum(double numberOne, double numberTwo) {
        return numberOne + numberTwo;
    }
    static double sub(double numberOne, double numberTwo) {
        return numberOne - numberTwo;
    }
    static double mult(double numberOne, double numberTwo) {
        return numberOne * numberTwo;
    }
    static double div(double numberOne, double numberTwo) {
        return numberOne / numberTwo;
    }
    static double med(double numberOne, double numberTwo) {
        return (numberOne + numberTwo) / 2;
    }
    static double raiz(double number) {
        return Math.sqrt(number);
    }
}
