import java.util.*;

public class FirstFollow {
    static char[] nonTerminals, terminals;
    static String[][] grammar;
    static String[] first, follow;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter non-terminals: ");
        nonTerminals = sc.nextLine().toCharArray();

        System.out.print("Enter terminals: ");
        terminals = sc.nextLine().toCharArray();

        int n = nonTerminals.length;
        grammar = new String[n][];
        first = new String[n];
        follow = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Number of productions for " + nonTerminals[i] + ": ");
            int prodCount = sc.nextInt();
            sc.nextLine();
            grammar[i] = new String[prodCount];

            System.out.println("Enter productions:");
            for (int j = 0; j < prodCount; j++)
                grammar[i][j] = sc.nextLine();
        }

        for (int i = 0; i < n; i++) first[i] = findFirst(i);
        for (int i = 0; i < n; i++) follow[i] = findFollow(i);

        System.out.println("\nFirst Sets:");
        for (int i = 0; i < n; i++)
            System.out.println(nonTerminals[i] + " : " + removeDuplicates(first[i]));

        System.out.println("\nFollow Sets:");
        for (int i = 0; i < n; i++)
            System.out.println(nonTerminals[i] + " : " + removeDuplicates(follow[i]));

        sc.close();
    }

    static String findFirst(int i) {
        StringBuilder res = new StringBuilder();
        for (String prod : grammar[i]) {
            for (char c : prod.toCharArray()) {
                if (isTerminal(c) || c == 'ε') {
                    res.append(c);
                    break;
                } else {
                    res.append(findFirst(indexOf(c)));
                    if (!first[indexOf(c)].contains("ε")) break;
                }
            }
        }
        return res.toString();
    }

    static String findFollow(int i) {
        StringBuilder res = new StringBuilder(i == 0 ? "$" : "");
        for (int j = 0; j < nonTerminals.length; j++) {
            for (String prod : grammar[j]) {
                int idx = prod.indexOf(nonTerminals[i]);
                if (idx != -1) {
                    if (idx < prod.length() - 1) {
                        char next = prod.charAt(idx + 1);
                        if (isTerminal(next)) res.append(next);
                        else {
                            res.append(first[indexOf(next)].replace("ε", ""));
                            if (first[indexOf(next)].contains("ε")) res.append(findFollow(j));
                        }
                    } else if (j != i) res.append(findFollow(j));
                }
            }
        }
        return res.toString();
    }

    static boolean isTerminal(char c) {
        return new String(terminals).indexOf(c) != -1;
    }

    static int indexOf(char c) {
        return new String(nonTerminals).indexOf(c);
    }

    static String removeDuplicates(String str) {
        return str.chars().distinct().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
