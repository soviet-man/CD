import java.util.*;

public class LeftFactoring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> productions = new ArrayList<>();
        System.out.println("Enter productions (type 'done' to finish):");
        
        while (true) {
            String s = sc.nextLine();
            if (s.equals("done")) break;
            productions.add(s);
        }
        
        for (String production : productions)
            eliminateLeftFactoring(production);
        
        sc.close();
    }

    private static void eliminateLeftFactoring(String production) {
        String[] parts = production.split("->");
        String lhs = parts[0].trim();
        String[] rhs = parts[1].split("\\|");
        String prefix = findCommonPrefix(rhs);

        if (prefix.isEmpty()) {
            System.out.println(production);
            return;
        }

        System.out.println(lhs + " -> " + prefix + lhs + "'");
        List<String> newRhs = new ArrayList<>();
        for (String r : rhs) {
            String suffix = r.startsWith(prefix) ? r.substring(prefix.length()).trim() : r.trim();
            newRhs.add(suffix.isEmpty() ? "ɛ" : suffix);
        }
        System.out.println(lhs + "' -> " + String.join(" | ", newRhs));
    }

    private static String findCommonPrefix(String[] rhs) {
        String prefix = rhs[0];
        for (String r : rhs)
            while (!r.startsWith(prefix)) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }
}
