
import java.util.Scanner;

public class Adder {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.print("Type a number: ");
        int num1 = Integer.parseInt(reader.nextLine());
        System.out.print("Type another number: ");
        int num2 = Integer.parseInt(reader.nextLine());
        System.out.println("\nSum of the numbers: " + (num1 + num2));
        System.out.println("\nDivision: " + num1 + " / " + num2 + " = " + ((double)num1/(double)num2));

    }
}
