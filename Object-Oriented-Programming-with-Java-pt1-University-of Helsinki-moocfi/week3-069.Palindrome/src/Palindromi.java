import java.util.Scanner;

public class Palindromi {

    public static boolean palindrome(String text) {
        int total = 0;
        if(text.isEmpty()){return true;}
        for(int i=0; i<text.length(); i++){
            if(text.charAt(i) == text.charAt(text.length()-i-1)){
                total++;
                if(total==text.length()){return true;}
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        
        System.out.println("Type a text: ");
        String text = reader.nextLine();    
        if (palindrome(text)) {
            System.out.println("The text is a palindrome!");
        } else {
            System.out.println("The text is not a palindrome!");
        }
    }
}
