
import java.util.Scanner;

public class SeparatingCharacters {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Type your name: ");
        String name = reader.nextLine();
        for(int i=1; i<name.length()+1; i++){ System.out.println(i + ". character: " + name.charAt(i-1)); }
    }
}
