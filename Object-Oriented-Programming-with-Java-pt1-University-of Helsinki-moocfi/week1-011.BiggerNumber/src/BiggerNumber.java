
import java.util.Scanner;

public class BiggerNumber {

    public static void main(String[] args) {

//        Scanner reader = new Scanner(System.in);
//
//        System.out.print("Type a number: ");
//        int a = Integer.parseInt(reader.nextLine());
//        System.out.print("Type another number: ");
//        int b = Integer.parseInt(reader.nextLine());
//        int bigger = Math.max(a,b);
//        System.out.println("The bigger number of the two numbers given was: " + bigger);

        Scanner reader = new Scanner(System.in);
        System.out.print("Type a number: ");
        int num1 = Integer.parseInt(reader.nextLine());
        System.out.print("Type another number: ");
        int num2 = Integer.parseInt(reader.nextLine());
        if (num1 >= num2) {
            System.out.println("The bigger number of the two numbers given was: " + num1);
        }else if (num2 > num1){
            System.out.println("The bigger number of the two numbers given was: " + num2);
            }
    }
}
