
import java.util.*;

class ProductionRule {
    String left, right;
    ProductionRule(String l, String r) { left = l; right = r; }
}

public class ShiftReduceParser {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of production rules: ");
        int n = sc.nextInt();
        sc.nextLine();
        ProductionRule[] rules = new ProductionRule[n];

        System.out.println("Enter production rules (format: left->right):");
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split("->");
            rules[i] = new ProductionRule(parts[0], parts[1]);
        }

        System.out.print("Enter input string: ");
        String input = sc.nextLine(), stack = "";
        int i = 0;

        while (true) {
            if (i < input.length()) {
                stack += input.charAt(i++);
                System.out.println(stack + "\t" + input.substring(i) + "\tShift");
            }

            for (ProductionRule rule : rules) {
                while (stack.contains(rule.right)) {
                    stack = stack.replaceFirst(rule.right, rule.left);
                    System.out.println(stack + "\t" + input.substring(i) + "\tReduce " + rule.left + "->" + rule.right);
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
        sc.close();
    }
}
