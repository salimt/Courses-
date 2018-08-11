
import java.util.Random;
import java.util.Scanner;

public class GuessingNumberGame {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int numberDrawn = drawNumber();

        // program your solution here. Do not touch the above lines!
//        System.out.println("Random num: " + numberDrawn);
        int numofTries = 1;
        while(true){
            System.out.print("Guess a number: ");
            int num = Integer.parseInt(reader.nextLine());
            if(num<numberDrawn){System.out.println("The number is greater, guesses made: " + numofTries); numofTries++;}
            else if(num>numberDrawn){System.out.println("The number is lesser, guesses made: " + numofTries); numofTries++;}
            else{System.out.println("Congratulations, your guess is correct!"); break;}
        }
    }

    // DO NOT MODIFY THIS!
    private static int drawNumber() {
        return new Random().nextInt(101);
    }
}
