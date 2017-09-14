package reporter;

/**
 *
 * @author Alphalapz
 */
import java.util.Stack;

public class SReversePolishNotation {

    public static void main(String[] args) {
        String expr = "15 7 1 1 + - / 3 * 2 1 1 + + -";
        System.out.println("Expresion: " + expr);
        System.out.println("Resultado: " + runNpi(expr));
    }

    public static String nueva(final String expr) {
        String[] postfix = expr.split(" ");
        Stack< String> pila = new Stack<>();
        int cantidadDeElementos = 0;
        for (int i = 0; i <= postfix.length; i++) {
            if (!isSigno(postfix[i])) {
                pila.push(postfix[i]);
            }
            else {
                cantidadDeElementos = pila.size();
            }

        }
        return null;
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
