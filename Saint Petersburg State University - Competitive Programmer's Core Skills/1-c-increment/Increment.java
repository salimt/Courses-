//@author: salimt

import java.io.PrintWriter;
import java.util.Scanner;

public class Increment {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        String n = in.next();
        Boolean s = n.matches("9*");
        out.println(!s? n.length() : n.length()+1);

        in.close();
        out.close();
    }
}