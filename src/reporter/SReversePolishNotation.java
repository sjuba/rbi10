package reporter;

/**
 *
 * @author Alfredo Pérez
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class SReversePolishNotation {

    public static void main(String[] args) throws IOException {
        System.out.println("Ingrese cadena RPN (ejemplo: '5 5 +':");
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        String expr=buffer.readLine();
        System.out.println("Expresion: " + expr);
        System.out.println("Resultado: " + runNpi(expr));
    }

    public static String runNpi(final String expr) {
        return npi(expr);
    }

    private static String npi(final String expr) {
        String[] post = expr.split(" ");
        Stack< String> E = new Stack<>();
        Stack< String> P = new Stack<>();
        if (expr.length() <= 1) {
            return expr;
        }

        for (int i = post.length - 1; i >= 0; i--) {
            E.push(post[i]);
        }

        String operadores = "+-*/%";
        while (!E.isEmpty()) {
            if (operadores.contains("" + E.peek())) {
                try {
                    P.push(evaluar(E.pop(), P.pop(), P.pop()) + "");
                } catch (Exception e) {
                    System.out.println("Error al ingresar la ecuacion en RPN: " + expr);
                }
            }
            else {
                P.push(E.pop());
            }
        }

        return P.peek();
    }

    private static double evaluar(final String op, final String n2, final String n1) {
        double num1 = Double.parseDouble(n1);
        double num2 = Double.parseDouble(n2);
        if (op.equals("+")) {
            return (num1 + num2);
        }
        if (op.equals("-")) {
            return (num1 - num2);
        }
        if (op.equals("*")) {
            return (num1 * num2);
        }
        if (op.equals("/")) {
            return (num1 / num2);
        }
        if (op.equals("%")) {
            return (num1 % num2);
        }
        return 0;
    }

    private static boolean isSigno(String posibleSigno) {
        switch (posibleSigno) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                return true;
            default:
                return false;
        }
    }

}
