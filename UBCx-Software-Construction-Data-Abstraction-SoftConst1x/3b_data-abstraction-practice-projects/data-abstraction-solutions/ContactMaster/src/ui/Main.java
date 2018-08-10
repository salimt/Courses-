package ui;

import model.Contact;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("To add a new contact, type in their name, then press 'enter':");
        Contact c = new Contact(s.next());
        System.out.println("Add a phone number for " + c.getName() + ":");
        c.setNumber(s.next());
        System.out.println("Would you like to call " + c.getName() + " now? Type 'y' for yes, 'n' for no.");
        options(c, s);
    }

    private static void options(Contact c, Scanner s){
        String option = s.next();
        if(option.equals("y")) {
            System.out.println("Enter a date for the call in the format MM/DD/YY:");
            c.call(s.next());
            System.out.println("Here is your updated call history:");
        } else if (option.equals("n")) {
            System.out.println("Call history: No Calls.");
        }
    }


}
