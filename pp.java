package Exp1;
import java.util.*;

public class pp {
    static Map<String, String[]> grammar = new HashMap<>();
    static Map<String, Set<Character>> first = new HashMap<>(), follow = new HashMap<>();
    static Map<String, Map<Character, String>> table = new HashMap<>();
    static List<String> nonTerminals = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the number of productions: ");
        int n = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter the productions (use '@' for epsilon):");
        for (int i = 0; i < n; i++) {
            String[] parts = sc.nextLine().split("->");
            String nt = parts[0].trim();
            String[] rhs = parts[1].split("\\|");
            grammar.put(nt, rhs);
            nonTerminals.add(nt);
            table.put(nt, new HashMap<>());
        }

        for (String nt : nonTerminals) {
            first.put(nt, findFirst(nt));
            follow.put(nt, new HashSet<>());
        }
        follow.get(nonTerminals.get(0)).add('$'); // Start symbol follow

        for (String nt : nonTerminals) findFollow(nt);
        buildTable();

        System.out.print("Enter string to check: ");
        String input = sc.nextLine() + "$";

        System.out.println(parse(input) ? "String is accepted" : "String is rejected");
        sc.close();
    }

    static Set<Character> findFirst(String nt) {
        if (first.get(nt) != null) return first.get(nt);
        Set<Character> res = new HashSet<>();
        for (String prod : grammar.get(nt)) {
            char firstChar = prod.charAt(0);
            if (!nonTerminals.contains(firstChar + "")) res.add(firstChar);
            else {
                Set<Character> subFirst = findFirst(firstChar + "");
                res.addAll(subFirst);
                if (subFirst.contains('@') && prod.length() > 1)
                    res.addAll(findFirst(prod.charAt(1) + ""));
            }
        }
        return res;
    }

    static void findFollow(String nt) {
        for (String lhs : nonTerminals) {
            for (String prod : grammar.get(lhs)) {
                int idx = prod.indexOf(nt);
                if (idx != -1 && idx < prod.length() - 1) {
                    char next = prod.charAt(idx + 1);
                    if (!nonTerminals.contains(next + "")) follow.get(nt).add(next);
                    else follow.get(nt).addAll(first.get(next + ""));
                }
                if (idx == prod.length() - 1 || follow.get(nt).contains('@'))
                    follow.get(nt).addAll(follow.get(lhs));
            }
        }
    }

    static void buildTable() {
        for (String nt : nonTerminals) {
            for (String prod : grammar.get(nt)) {
                for (char c : findFirst(nt)) 
                    if (c != '@') table.get(nt).put(c, prod);
                if (findFirst(nt).contains('@'))
                    for (char c : follow.get(nt)) table.get(nt).put(c, "@");
            }
        }
    }

    static boolean parse(String input) {
        Stack<Character> stack = new Stack<>();
        stack.push('$');
        stack.push(nonTerminals.get(0).charAt(0));
        int i = 0;

        while (!stack.isEmpty()) {
            char top = stack.pop();
            if (top == input.charAt(i)) i++;
            else if (table.containsKey(top + "") && table.get(top + "").containsKey(input.charAt(i))) {
                String prod = table.get(top + "").get(input.charAt(i));
                if (!prod.equals("@")) for (int j = prod.length() - 1; j >= 0; j--) stack.push(prod.charAt(j));
            } else return false;
        }
        return i == input.length();
    }
}
