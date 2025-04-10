import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FirstnFollow {
    static String first[], follow[], grammar[][];
    static List<String> nonTerminals = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the no of productions");
        int n = sc.nextInt();
        grammar = new String[n][2];

        System.out.println("Enter the productions that are free from Left Recursion");
        sc.nextLine();

        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            String p[] = s.split("->");
            nonTerminals.add(p[0].trim());
            grammar[i][0] = p[0].trim();
            grammar[i][1] = p[1];
        }

        first = new String[n];
        follow = new String[n];

        for (int i = 0; i < n; i++)
            first[i] = calculateFirst(i);

        System.out.println("First :-");
        for (int i = 0; i < n; i++)
            System.out.println(nonTerminals.get(i) + " : " + print(first[i]));

        for (int i = 0; i < n; i++)
            follow[i] = calculateFollow(i);

        System.out.println("Follow :-");
        for (int i = 0; i < n; i++)
            System.out.println(nonTerminals.get(i) + " : " + print(follow[i]));
    }

    static String print(String s) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append(s.charAt(0));
        for (char c : s.toCharArray()) {
            if (sb.indexOf(c + "") == -1)
                sb.append("," + c);
        }
        sb.append('}');
        return sb.toString();
    }

    static String calculateFirst(int i) {
        String s[] = grammar[i][1].split("\\|"), temp = "";
        for (String p : s) {
            if (!nonTerminals.contains(p.charAt(0) + ""))
                temp += p.charAt(0);
            else
                temp += calculateFirst(nonTerminals.indexOf(p.charAt(0) + ""));
        }
        return temp;
    }

    static String calculateFollow(int i) {
        Set<Character> temp = new HashSet<>();
        if (i == 0)
            temp.add('$');

        for (int idx = 0; idx < grammar.length; idx++) {
            if (grammar[idx][0].equals(nonTerminals.get(i)))
                continue;

            String s[] = grammar[idx][1].split("\\|");
            for (String p : s) {
                String nt = nonTerminals.get(i);
                if (p.contains(nt)) {
                    int x = p.indexOf(nt);

                    if (x == p.length() - 1) {
                        String t = follow[nonTerminals.indexOf(grammar[idx][0])];
                        for (char c : t.toCharArray())
                            temp.add(c);
                    } else {
                        if (!nonTerminals.contains(p.charAt(x + 1) + "")) {
                            temp.add(p.charAt(x + 1));
                        } else {
                            int nextIndex = nonTerminals.indexOf(p.charAt(x + 1) + "");
                            String t = first[nextIndex];
                            if (t.contains("e")) {
                                for (char c : t.toCharArray())
                                    temp.add(c);
                                temp.remove('e');

                                t = follow[nonTerminals.indexOf(grammar[idx][0])];
                                for (char c : t.toCharArray())
                                    temp.add(c);
                            } else {
                                for (char c : t.toCharArray())
                                    temp.add(c);
                            }
                        }
                    }
                }
            }
        }

        String ans = "";
        for (char x : temp)
            ans += x;
        return ans;
    }
}
