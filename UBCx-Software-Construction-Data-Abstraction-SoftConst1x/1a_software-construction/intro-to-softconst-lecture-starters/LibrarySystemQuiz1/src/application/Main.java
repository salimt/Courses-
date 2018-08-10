package application;

import model.literature.Book;
import model.library.Librarian;
import model.library.Library;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import static model.literature.BookType.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Book hp1 = new Book("Harry Potter and the Sorcerer's Stone", "JK Rowling", FICTION, 1998, 1);
        Book cpsc1 = new Book("Discrete Mathematics", "Susanna Smith", TEXTBOOK, 2014, 4);
        Book cook = new Book("The Art of French Cooking", "Julia Child", COOKING, 1950, 1);
        Book dict = new Book("Encyclopedia Britannia", "University of Cambridge", REFERENCE, 1066, 3);
        Book nonfic = new Book("Visions of Infinity", "Ian Stewart", NONFICTION, 2014, 1);
        Library ikb = new Library("Irving K. Barber Learning Centre");
        ikb.storeBook(hp1);
        ikb.storeBook(cpsc1);
        ikb.storeBook(cook);
        ikb.storeBook(dict);
        ikb.storeBook(nonfic);
        Librarian librarian = new Librarian("Santa J. Ono", 45, ikb, hp1);

        System.out.println("Welcome to LibrarySystem version 1.0");
        System.out.println("====================================");
        System.out.println("Print catalogue..................[1]");
        System.out.println("Display librarian information....[2]");
        System.out.println("Random fact......................[3]\n");

        String input = sc.nextLine();
        System.out.println(input);

        if (input.equalsIgnoreCase("1")) {
            ikb.printCatalogue();
        } else if (input.equalsIgnoreCase("2")) {
            System.out.println("Name: " + ikb.getManager().getName());
            System.out.println("Age: " + ikb.getManager().getAge());
            System.out.println("Favourite literature: " + "'" + ikb.getManager().getFavBook().getTitle() + "'");
        } else if (input.equalsIgnoreCase("3")) {
            int min = 0;
            int max = 4;
            int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
            switch(randomNum) {
                case 1 :
                    System.out.println("July 21, 1969 was the date that humankind first walked on the moon");
                    break;
                case 2 :
                    System.out.println("Strawberries on average, have about 200 seeds on their skin");
                    break;
                case 3 :
                    System.out.println("It is illegal to eat mince pies on Christmas day in the UK");
                    break;
                case 4 :
                    System.out.println("A flock of crows is known as a 'murder'");
                    break;
                default:
                    System.out.println("Cherophobia is the fear of having fun");
            }

        }
    }


}
