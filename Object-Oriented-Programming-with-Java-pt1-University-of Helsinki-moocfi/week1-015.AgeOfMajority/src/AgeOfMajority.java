
import java.util.Scanner;

public class AgeOfMajority {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        System.out.print("How old are you? ");
        int num = Integer.parseInt(reader.nextLine());

        if (num < 18){
            System.out.println("You have not reached the age of majority yet!");
        }else{
            System.out.println("You have reached the age of majority!");
        }
    }
}
