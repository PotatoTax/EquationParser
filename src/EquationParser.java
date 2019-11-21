public class EquationParser {
    private EquationParser term1;
    private EquationParser term2;
    private double value;

    public enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, EXPONENT, NONE
    }

    private Operation operation = Operation.NONE;

    private void Parse(String equation) {
        // TODO : Parentheses, Exponents
        if (isConst(equation)) {
            value = Double.parseDouble(equation);
            return;
        }

        if (equation.contains("+") && equation.contains("-")) {
            if (equation.indexOf("+") < equation.indexOf('-')) {
                Split(equation, "-");
            } else  {
                Split(equation, "+");
            }
        }
        else if (equation.contains("+")) {
            Split(equation, "+");
        }
        else if (equation.contains("-")) {
            Split(equation, "-");
        }
        else if (equation.contains("*") && equation.contains("/")) {
            if (equation.indexOf("*") < equation.indexOf('/')) {
                Split(equation, "/");
            } else  {
                Split(equation, "*");
            }
        }
        else if (equation.contains("*")) {
            Split(equation, "*");
        }
        else if (equation.contains("/")) {
            Split(equation, "/");
        }
        else if (equation.contains("^")) {
            Split(equation, "^");
        }

    }

    private void Split(String equation, String operator) {
        switch (operator) {
            case "+":
                operation = Operation.ADD;
                break;
            case "-":
                operation = Operation.SUBTRACT;
                break;
            case "*":
                operation = Operation.MULTIPLY;
                break;
            case "/":
                operation = Operation.DIVIDE;
                break;
            case "^":
                operation = Operation.EXPONENT;
                break;
        }

        term1 = new EquationParser();
        term2 = new EquationParser();

        term1.Parse(equation.substring(0, equation.indexOf(operator)));
        term2.Parse(equation.substring(equation.indexOf(operator)+1));

    }


    private boolean isConst(String x) {
        try {
            Double.parseDouble(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double Evaluate(double x) {
        switch (operation) {
            case NONE:
                return value;
            case ADD:
                return term1.Evaluate(x) + term2.Evaluate(x);
            case SUBTRACT:
                return term1.Evaluate(x) - term2.Evaluate(x);
            case MULTIPLY:
                return term1.Evaluate(x) * term2.Evaluate(x);
            case DIVIDE:
                return term1.Evaluate(x) / term2.Evaluate(x);
            case EXPONENT:
                return Math.pow(term1.Evaluate(x), term2.Evaluate(x));
            default:
                return x;
        }
    }

    @Override
    public String toString() {
        switch (operation) {
            case NONE:
                return Double.toString(value);
            case ADD:
                return "(" + term1.toString() + " + " + term2.toString() + ")";
            case SUBTRACT:
                return "(" + term1.toString() + " - " + term2.toString() + ")";
            case MULTIPLY:
                return "(" + term1.toString() + " * " + term2.toString() + ")";
            case DIVIDE:
                return "(" + term1.toString() + " / " + term2.toString() + ")";
            case EXPONENT:
                return "(" + term1.toString() + " ^ " + term2.toString() + ")";
            default:
                return "";
        }
    }

    public static void main(String[] args) {
        EquationParser equationParser = new EquationParser();
        equationParser.Parse("2+7^20");
        System.out.println(equationParser.Evaluate(1));
        System.out.println(equationParser);
    }
}
