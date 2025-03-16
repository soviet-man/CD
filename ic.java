import java.util.*;

class Instruction {
    String op, arg1, arg2, result;
    Instruction(String op, String arg1, String arg2, String result) {
        this.op = op; this.arg1 = arg1; this.arg2 = arg2; this.result = result;
    }
    public String toString() {
        return result + " = " + arg1 + " " + op + " " + arg2;
    }
}

class IntermediateCodeGenerator {
    private List<Instruction> instructions = new ArrayList<>();
    private Stack<String> operands = new Stack<>();
    private Stack<Character> operators = new Stack<>();
    private int tempCount = 0;

    public List<Instruction> generate(String expr) {
        StringBuilder operand = new StringBuilder();
        for (char token : expr.toCharArray()) {
            if (Character.isWhitespace(token)) continue;
            if (Character.isLetterOrDigit(token)) {
                operand.append(token);
                if (!operands.isEmpty() && !Character.isLetterOrDigit(operands.peek().charAt(0))) {
                    operands.push(operand.toString());
                    operand.setLength(0);
                }
            } else {
                if (operand.length() > 0) operands.push(operand.toString());
                operand.setLength(0);
                if (token == '(') operators.push(token);
                else if (token == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') processOperator(operators.pop());
                    operators.pop();
                } else {
                    while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) processOperator(operators.pop());
                    operators.push(token);
                }
            }
        }
        if (operand.length() > 0) operands.push(operand.toString());
        while (!operators.isEmpty()) processOperator(operators.pop());
        return instructions;
    }

    private void processOperator(char op) {
        String b = operands.pop(), a = operands.pop(), t = "t" + tempCount++;
        instructions.add(new Instruction(String.valueOf(op), a, b, t));
        operands.push(t);
    }

    private int precedence(char op) {
        return (op == '+' || op == '-') ? 1 : (op == '*' || op == '/') ? 2 : -1;
    }
}

public class exp10 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter an arithmetic expression:");
        List<Instruction> code = new IntermediateCodeGenerator().generate(sc.nextLine());
        System.out.println("Intermediate Code:");
        code.forEach(System.out::println);
        sc.close();
    }
}
