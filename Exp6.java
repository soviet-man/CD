import java.util.*;

public class Exp6 {
    static String first[], follow[], grammar[][];
    static List<String> nonTerminals = new ArrayList<>();
    static Map<String, Map<Character, String>> table = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no of productions");
        int n = sc.nextInt();
        grammar = new String[n][2];

        System.out.println("Enter the productions that are free from Left Recursion (Use '@' Symbol for Epsilon)");
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            String s = sc.nextLine();
            String p[] = s.split("->");
            nonTerminals.add(p[0].trim());
            grammar[i][0] = p[0].trim();
            grammar[i][1] = p[1];
        }

        for (String s : nonTerminals)
            table.put(s, new HashMap<>());

        first = new String[n];
        follow = new String[n];

        for (int i = 0; i < n; i++)
            first[i] = calculateFirst(i);

        for (int i = 0; i < n; i++)
            follow[i] = calculateFollow(i);

        for (int i = 0; i < n; i++) {
            if (first[i].contains("@")) {
                for (char c : follow[i].toCharArray()) {
                    table.get(grammar[i][0]).put(c, "@");
                }
            }
        }

        System.out.println("Enter the String to Check whether it is accepted or not");
        String s = sc.nextLine();

        if (check(s + "$"))
            System.out.println("String is accepted");
        else
            System.out.println("String is rejected");
    }

    static String calculateFirst(int i) {
        String s[] = grammar[i][1].split("\\|"), temp = "";
        for (String p : s) {
            if (!nonTerminals.contains(p.charAt(0) + ""))
                temp += p.charAt(0);
            else {
                String t = calculateFirst(nonTerminals.indexOf(p.charAt(0) + ""));
                if (t.contains("@")) {
                    if (p.length() > 1 && !nonTerminals.contains(p.charAt(1) + ""))
                        temp += p.charAt(1);
                    else if (p.length() > 1)
                        temp += calculateFirst(nonTerminals.indexOf(p.charAt(1) + ""));
                }
                temp += t;
            }
            table.get(grammar[i][0]).put(temp.charAt(temp.length() - 1), p);
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
                        if (t == null)
                            t = calculateFollow(nonTerminals.indexOf(grammar[idx][0]));
                        for (char c : t.toCharArray())
                            temp.add(c);
                    } else {
                        if (!nonTerminals.contains(p.charAt(x + 1) + ""))
                            temp.add(p.charAt(x + 1));
                        else {
                            int ni = nonTerminals.indexOf(p.charAt(x + 1) + "");
                            String t = first[ni];
                            if (t.contains("@")) {
                                for (char c : t.toCharArray())
                                    temp.add(c);
                                temp.remove('@');

                                t = follow[nonTerminals.indexOf(grammar[idx][0])];
                                if (t == null)
                                    t = calculateFollow(nonTerminals.indexOf(grammar[idx][0]));
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

    static boolean check(String s) {
        Stack<Character> stk = new Stack<>();
        stk.add('$');
        stk.add(nonTerminals.get(0).charAt(0));
        int i = 0;

        while (i < s.length()) {
            if (stk.isEmpty())
                return false;

            if (stk.peek() == s.charAt(i)) {
                stk.pop();
                i++;
                continue;
            }

            char c = stk.pop();
            String t = table.get(c + "").get(s.charAt(i));
            if (t == null)
                return false;

            if (t.equals("@"))
                continue;

            for (int idx = t.length() - 1; idx >= 0; idx--)
                stk.push(t.charAt(idx));
        }

        return stk.isEmpty();
    }
}
