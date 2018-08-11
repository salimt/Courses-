
import java.util.Scanner;


public class UpToCertainNumber {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int i = 1;
        System.out.print("Up to what number? ");
        int num = Integer.parseInt(reader.nextLine());
        while(i <= num){
            System.out.println(i);
            i++;
        }
        
    }
}
