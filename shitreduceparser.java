import java.util.Scanner;

class ProductionRule {
    String left;
    String right;

    ProductionRule(String left, String right) {
        this.left = left;
        this.right = right;
    }
}

public class shitreduceparser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input, stack = "";
        int ruleCount;

        System.out.println("Enter the number of production rules: ");
        ruleCount = scanner.nextInt();
        scanner.nextLine();
        ProductionRule[] rules = new ProductionRule[ruleCount];

        System.out.println("Enter the production rules (in the form 'left->right'): ");
        for (int i = 0; i < ruleCount; i++) {
            String[] temp = scanner.nextLine().split("->");
            rules[i] = new ProductionRule(temp[0], temp[1]);
        }

        System.out.println("Enter the input string: ");
        input = scanner.nextLine();

        int i = 0;
        System.out.println("Stack\tInputBuffer\tAction");

        while (true) {
            if (i < input.length()) {
                char ch = input.charAt(i);
                i++;
                stack += ch;
                System.out.print(stack + "\t");
                System.out.print(input.substring(i) + "\t\tShift " + ch + "\n");
            }

            for (int j = 0; j < ruleCount; j++) {
                int substringIndex = stack.indexOf(rules[j].right);
                if (substringIndex != -1) {
                    stack = stack.substring(0, substringIndex) + rules[j].left;
                    System.out.print(stack + "\t");
                    System.out.print(input.substring(i) + "\t\tReduce " + rules[j].left + "->" + rules[j].right + "\n");
                    j = -1;
                }
            }

            if (stack.equals(rules[0].left) && i == input.length()) {
                System.out.println("\nAccepted");
                break;
            }
            if (i == input.length()) {
                System.out.println("\nNot Accepted");
                break;
            }
        }

        scanner.close();
    }
}
