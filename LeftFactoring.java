import java.util.*;

public class LeftFactoring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of productions:");
        int n = sc.nextInt();
        sc.nextLine();
        String[] productions = new String[n];

        System.out.println("Enter the productions:");
        for (int i = 0; i < n; i++) {
            productions[i] = sc.nextLine();
        }

        eliminateLeftFactoring(productions);
        sc.close();   
    }

    private static void eliminateLeftFactoring(String[] productions) {
        boolean leftFactored = false;

        for (String production : productions) {
            String[] parts = production.split("->");
            String lhs = parts[0].trim();
            String[] rhs = parts[1].split("\\|");
            String prefix = findCommonPrefix(rhs);

            if (!prefix.isEmpty()) {
                leftFactored = true;
                System.out.println(lhs + " -> " + prefix + lhs + "'");

                List<String> newRhs = new ArrayList<>();
                for (String r : rhs) {
                    if (r.startsWith(prefix)) {
                        String suffix = r.substring(prefix.length()).trim();
                        if (suffix.isEmpty()) {
                            suffix = "";
                        }
                        newRhs.add(suffix);
                    } else {
                        newRhs.add(r);
                    }
                }

                System.out.println(lhs + "' -> " + String.join("|", newRhs));
            }
        }

        if (!leftFactored) {
            System.out.println("Given productions do not have left factoring");
        }

     
    }

    private static String findCommonPrefix(String[] rhs) {
        String prefix = rhs[0];
        for (int i = 1; i < rhs.length; i++) {
            while (rhs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }
        return prefix;
    }
}
