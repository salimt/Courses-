import java.util.Scanner;

public class FirstCharacters {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Type your name: ");
        String name = reader.nextLine();
        if(name.length()>=3){
            for(int i=1; i<4; i++){ System.out.println(i + ". character: " + name.charAt(i-1)); }
        }
    }
}
