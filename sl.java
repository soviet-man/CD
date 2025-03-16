import java.util.Scanner;

public class Main {
    static int[] stack;
    static int top, n;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        top = -1;

        System.out.print("Enter the size of stack: ");
        n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Invalid stack size.");
            return;
        }

        stack = new int[n];

        int choice;
        do {
            System.out.println("\nStack Operations:");
            System.out.println("1. Push");
            System.out.println("2. Pop");
            System.out.println("3. Display");
            System.out.println("4. EXIT");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            switch (choice) {
                case 1 -> push(scanner);
                case 2 -> pop();
                case 3 -> display();
                case 4 -> System.out.println("\nExiting...");
                default -> System.out.println("Please enter a valid choice.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    static void push(Scanner scanner) {
        if (top >= n - 1) {
            System.out.println("\nStack overflow");
        } else {
            System.out.print("Enter a value to push: ");
            stack[++top] = scanner.nextInt();
        }
    }

    static void pop() {
        if (top == -1) {
            System.out.println("\nStack underflow");
        } else {
            System.out.println("\nPopped element: " + stack[top--]);
        }
    }

    static void display() {
        if (top >= 0) {
            System.out.println("\nStack elements:");
            for (int i = top; i >= 0; i--) {
                System.out.println(stack[i]);
            }
        } else {
            System.out.println("\nThe stack is empty.");
        }
    }
}
