import java.util.Scanner;

public class abc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter string:");
        String inp = sc.nextLine(), state = "q0";
        int k=0;sc.close();
        System.out.print("States are: q0->");
        for (char ch : inp.toCharArray()) {
            ++k;
            switch (state) {
                case "q0":
                    state = (ch == 'a') ? "q1" : "q0";
                    break;
                case "q1":
                    state = (ch == 'a') ? "q1" : (ch == 'b') ? "q2" : "q0";
                    break;
                case "q2":
                    state = (ch == 'a') ? "q1" : (ch == 'b') ? "q0" : "q3";
                    break;
                case "q3":
                    state = (ch == 'a') ? "q1" : "q0";
                    break;
            }
            System.out.print(state + ( k==inp.length() ? "" : "->"));
        }
        System.out.println("\nString is " + (state.equals("q3") ? "accepted" : "not accepted"));
    }
}