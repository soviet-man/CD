import java.util.*;

public class Lexicalanalyzer {

    public static void main(String[] args) {

        ArrayList<String> keywords = new ArrayList<>(Arrays.asList(
            "if", "else", "while", "for", "int", "float", "double", "char", "String", "boolean"
        ));

        ArrayList<String> operators = new ArrayList<>(Arrays.asList(
            "+", "-", "*", "/", "=", ">", "<", "!", "&", "|", "++", "--"
        ));

        ArrayList<String> delimiters = new ArrayList<>(Arrays.asList(
            "(", ")", "{", "}", "[", "]", ",", ";"
        ));

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter program with single spaces between tokens:");
        String input = sc.nextLine();
        sc.close();

        if (input.trim().isEmpty()) {
            System.out.println("No input provided.");
            return;
        }

        String[] arr = input.split(" ");
        int len = arr.length;
        String[] ans = new String[len];

        for (int i = 0; i < len; i++) {
            if (keywords.contains(arr[i])) {
                ans[i] = "keyword";
            } else if (operators.contains(arr[i])) {
                ans[i] = "operator";
            } else if (delimiters.contains(arr[i])) {
                ans[i] = "delimiter";
            } else if (isIdentifier(arr[i])) {
                ans[i] = "identifier";
            } else if (isLiteral(arr[i])) {
                ans[i] = "literal";
            } else {
                ans[i] = "unknown";
            }
        }

        for (int i = 0; i < len; i++) {
            System.out.println(arr[i] + ": " + ans[i]);
        }
    }

    private static boolean isIdentifier(String str) {
        if (str == null || str.isEmpty()) return false;

        if (Character.isDigit(str.charAt(0))) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_') {
                return false;
            }
        }

        return true;
    }

    private static boolean isLiteral(String str) {
        if (str.equals("true") || str.equals("false")) {
            return true; // boolean literals
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e1) {
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e2) {
                return false;
            }
        }
    }
}
