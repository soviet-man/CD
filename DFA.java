import java.util.*;

public class DFA {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("1. Enter the string");
        System.out.println("2. Exit");
        System.out.println("Enter a choice");
        int n = sc.nextInt();

        while (n != 2) {
            System.out.println("Enter the string:");
            String s = sc.next();

            if (s.endsWith("abc")) {
                System.out.println(s + " is Accepted");
            } else {
                System.out.println(s + " is Not Accepted");
            }

            System.out.println("1. Enter the string\n2. Exit");
            System.out.println("Enter a choice");
            n = sc.nextInt();
        }

        sc.close();
    }
}
