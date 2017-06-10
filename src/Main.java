import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;

/**
 * Simple calculator.
 * Accepted operators are:
 * +, -, *, /, ^, %, !, ()
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input;
        String output;

        System.out.println("Press q to quit");

        //program loop
        System.out.print("\nEnter calculation: ");
        input = sc.nextLine();
        while (!input.equals("q")) {
            try {
                output = String.valueOf(interp(input));
                System.out.print(">" + output);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            System.out.print("\n$");
            input = sc.nextLine();
        }

        sc.close();
    }

    /**
     * accepted symbols:
     * "!"
     * "%"
     * "^"
     * "*"
     * "("
     * ")"
     * "-"
     * "+"
     * "/"
     * "." \\TODO: implement parse, interp
     * "\\W" whitespace, will be removed
     */
    static String sanitize(String s) throws Exception {
        assert s != null;
        // accepted symbols
        if (s.replaceAll("[\\-!%^*()+/1234567890.\\s]", "").length() > 0) {
            throw new Exception(
                    "ERROR!\n" +
                            "unaccepted symbols in string\n" +
                            "[String s]\t" + s + "\n" +
                            "[String s*]\t" + s.replaceAll("[!%^*()\\-+/1234567890.\\s]", "")
            );
        }
        // balanced brackets
        if (s.replaceAll("\\(", "").length() != s.replaceAll("\\)", "").length()) {
            throw new Exception(
                    "ERROR!\n" +
                            "brackets aren't balanced\n" +
                            "[String s]\t" + s + "\n" +
                            "[String s*]\t" + s.replaceAll("[^\\(\\)]", "")

            );
        }
        // remove whitespace
        return s.replaceAll("\\s", "");
    }

    static Expr parse(String s) throws Exception {
        // empty base case: ""
        if (s.isEmpty()) {
            throw new Exception("ERROR!\nempty string");
        }
        // float base case : "1"
        if (s.length() == 1) {
            if (s.matches("\\d")) {
                return new Num(Float.valueOf(s));
            } else {
                throw new Exception("ERROR!\nloose operator\n[String s]\t" + s);
            }
        }

        // floating point numbers: "1000", "9.3"
        if (s.matches("^([+-]?\\d*\\.?\\d*)$")) {
            return new Num(Float.valueOf(s));
        }

        // minus: "-e"
        if (s.matches("-.+")) {
            return new Min(parse(s.substring(1)));
        }
        //TODO: fix brackets
        // brackets: "(e)"
        if (s.matches("\\(.*?\\)")) {
            return new Brack(parse(s.substring(1, s.length() - 1)));
        }
        // power: "e^e"
        if (s.matches(".+\\^.+")) {
            int i = s.indexOf('^');
            return new Pow(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }
        // fac: "!e"
        if (s.matches("!.+")) {
            return new Fac(parse(s.substring(1)));
        }
        // multiply: "e*e"
        if (s.matches(".+\\*.+")) {
            int i = s.indexOf('*');
            return new Mul(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }
        // divide: "e/e"
        if (s.matches(".+\\/.+")) {
            int i = s.indexOf('/');
            return new Div(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }
        // plus: "e+e"
        if (s.matches(".+\\+.+")) {
            int i = s.indexOf('+');
            return new Plus(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }
        // subtract: "e-e"
        if (s.matches(".+\\-.+")) {
            int i = s.indexOf('-');
            return new Sub(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }
        // modulo "e%e"
        if (s.matches(".+\\%.+")) {
            int i = s.indexOf('%');
            return new Mod(parse(s.substring(0, i)), parse(s.substring(i + 1)));
        }

        throw new Exception(
                "ERROR!\n" +
                        "unexpected symbols in string\n" +
                        "[String s]\t" + s
        );
    }

    static float interp(Expr expr) throws Exception {
        // base
        if (expr.getClass() == Num.class) {
            Num _expr = (Num) expr;
            return _expr.n;
        }

        // single
        if (expr.getClass() == Min.class) {
            Min _expr = (Min) expr;
            return -1 * interp(_expr.e);
        }
        if (expr.getClass() == Fac.class) {
            Fac _expr = (Fac) expr;
            throw new Exception("ERROR!\nfactorial not implemented");
        }
        if (expr.getClass() == Brack.class) {
            Brack _expr = (Brack) expr;
            return interp(_expr.e);
        }

        // double
        if (expr.getClass() == Plus.class) {
            Plus _expr = (Plus) expr;
            return interp(_expr.e1) + interp(_expr.e2);
        }
        if (expr.getClass() == Sub.class) {
            Sub _expr = (Sub) expr;
            return interp(_expr.e1) - interp(_expr.e2);
        }
        if (expr.getClass() == Mul.class) {
            Mul _expr = (Mul) expr;
            return interp(_expr.e1) * interp(_expr.e2);
        }
        if (expr.getClass() == Div.class) {
            Div _expr = (Div) expr;
            if (interp(_expr.e2) == 0) {
                throw new Exception("ERROR!\ncan't divide by zero");
            }
            return interp(_expr.e1) / interp(_expr.e2);
        }
        if (expr.getClass() == Pow.class) {
            Pow _expr = (Pow) expr;
            return (float) Math.pow(interp(_expr.e1), interp(_expr.e2));
        }
        if (expr.getClass() == Mod.class) {
            Mod _expr = (Mod) expr;
            return interp(_expr.e1) % interp(_expr.e2);
        }

        throw new Exception("ERROR!\nexpression not of correct type\n");
    }

    static float interp(String s) throws Exception {
        return interp(parse(sanitize(s)));
    }
}
