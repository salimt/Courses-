
import java.util.Scanner;

public class Circumference {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.print("Type the radius: ");
        int radius = Integer.parseInt(reader.nextLine());
        System.out.println("Circumference of the circle: " + (2*Math.PI*radius));
    }
}
