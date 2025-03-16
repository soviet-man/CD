import java.util.*;
public class OperatorPrecedenceParser {
    public static void main(String[] args) {
        char[] stack = new char[20],ip = new char[20],ter = new char[10];
        char[][]opt = new char[10][10];
        int i, j, k, n, top = 0, col = 0, row = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the no. of terminals:");
        n = sc.nextInt();
        System.out.println("Enter the terminals:");
        ter = sc.next().toCharArray();
        System.out.println("Enter the table values:");
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print("Enter the value for "+ ter[i]+" "+ ter[j]+":");
                opt[i][j] = sc.next().charAt(0);
            }
        }

        System.out.println("OPERATOR PRECEDENCE TABLE:");
        for (i = 0; i < n; i++) {
            System.out.print("\t" + ter[i]);
        }
        for (i = 0; i < n; i++) {
            System.out.println();
            System.out.print(ter[i]);
            for (j = 0; j < n; j++) {
                System.out.print("\t" + opt[i][j]);
            }
        }
        stack[top] = '$';
        System.out.println("\nEnter the input string:");
        ip = sc.next().toCharArray();
        i = 0;
        System.out.println("\nSTACK\tINPUT\tACTION");
        System.out.print( "$\t\t"+ String.valueOf(ip)+"\t\t");
        while (i <= ip.length) {
            for (k = 0; k < n; k++) {
                if (stack[top] == ter[k])
                    col = k;
                if (ip[i] == ter[k])
                    row = k;
            }

            if ((stack[top] == '$') && (ip[i] == '$')) {
                System.out.println("String is accepted");
                break;
            } else if ((opt[col][row] == '<') || (opt[col][row]== '=')) {
                stack[++top] = opt[col][row];
                stack[++top] = ip[i];
                System.out.println("Shift " + ip[i]);
                i++;
            } else {
                if (opt[col][row] == '>') {
                    while (stack[top] != '<') {
                        --top;
                    }
                    top = top - 1;
                    System.out.println("Reduce");
                } else {
                    System.out.println("String is not accepted");
                    break;
                }
            }
            System.out.println();
            for (k = 0; k <= top; k++) {
                System.out.print(stack[k]);
            }
            System.out.print("\t\t");
            for (k = i; k < ip.length; k++) {
                System.out.print(ip[k]);
            }
            System.out.print("\t\t");
        }
    }
}