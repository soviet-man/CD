import java.util.*;

public class pp1 {
    static Map<Character, List<String>> grammar = new HashMap<>();
    static String input;
    static int i = 0;

    static boolean parse(char nonTerminal) {
        int backtrack = i;
        for (String prod : grammar.get(nonTerminal)) {
            i = backtrack;
            boolean success = true;
            for (char symbol : prod.toCharArray()) {
                if (symbol == '@') continue;
                else if (Character.isUpperCase(symbol)) success &= parse(symbol);
                else if (i < input.length() && input.charAt(i) == symbol) i++;
                else { success = false; break; }
            }
            if (success) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of productions:");
        int n = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter productions (Use '@' for epsilon, e.g., A->aA|@):");
        for (int j = 0; j < n; j++) {
            String[] rule = sc.nextLine().split("->");
            grammar.put(rule[0].charAt(0), Arrays.asList(rule[1].split("\\|")));
        }
        System.out.println("Enter the string to check:");
        input = sc.next() + "$";
        System.out.println(parse('E') && i == input.length() - 1 ? "String is accepted" : "String is rejected");
    }
}
