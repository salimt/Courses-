
import java.util.Scanner;

public class SumOfThePowers {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.print("Type a number: ");
        int num = Integer.parseInt(reader.nextLine());

        int sum = 2;
        while(num > 0) {
            sum *= 2;
            num -= 1;
        }
        System.out.println("The result is " + (sum-1));
    }
}
