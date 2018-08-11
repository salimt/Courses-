
import java.util.Scanner;


public class TheSumBetweenTwoNumbers {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.print("First: ");
        int first = Integer.parseInt(reader.nextLine());
        System.out.print("Last: ");
        int last = Integer.parseInt(reader.nextLine());

        int sum = 0;
        while (first <= last){
            sum += first;
            first += 1;
        }
        System.out.print("The sum is " + sum);
    }
}
