import java.util.Random;

public class PasswordRandomizer {
    private Random random;
    private int length;

    public PasswordRandomizer(int length) {
        this.length = length;
        this.random = new Random();
    }

    public String createPassword() {
        String pass = "";
        for(int i = 0; i<this.length; i++){
            int number = random.nextInt(26);
            char symbol = "abcdefghijklmnopqrstuvwxyz".charAt(number);
            pass = pass + symbol;
        }
        return pass;
    }
}
