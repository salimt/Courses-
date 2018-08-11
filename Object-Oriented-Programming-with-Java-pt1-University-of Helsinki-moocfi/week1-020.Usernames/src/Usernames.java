
import java.util.Scanner;

public class Usernames {

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        String username1 = "alex";
        String pass1 = "mightyducks";
        String username2 = "emily";
        String pass2 = "cat";

        System.out.print("Type your username: ");
        String username = reader.nextLine();
        System.out.print("Type your password: ");
        String pass = reader.nextLine();

        if ((username.equals(username1) && pass.equals(pass1)) || (username.equals(username2) && pass.equals(pass2))){
            System.out.println("You are now logged into the system!");
        }else{
            System.out.println("Your username or password was invalid!");
        }


    }
}
