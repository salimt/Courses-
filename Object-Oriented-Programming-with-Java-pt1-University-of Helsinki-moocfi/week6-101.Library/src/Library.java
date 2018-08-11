/**
 * author: salimt
 */

import java.util.*;

public class Library {

    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<Book>();
    }

    //adds the book into the library
    public void addBook(Book newBook) {
        books.add(newBook);
    }

    //prints the all books in the library
    public void printBooks() {
        for (Book b : books) {
            System.out.println(b);
        }
    }

    //searches the library by the book title returns ArrayList if found
    public ArrayList <Book> searchByTitle(String title) {
        ArrayList <Book> found = new ArrayList <Book>();

        for (Book book : books) {
            if(StringUtils.included(book.title(), title)) {
                found.add(book);
            }
        }return found;
    }

    //searches the library by the publisher returns ArrayList if found
    public ArrayList <Book> searchByPublisher(String publisher) {
        ArrayList <Book> found = new ArrayList <Book>();

        for (Book book : books) {
            if(StringUtils.included(book.publisher(), publisher)) {
                found.add(book);
            }
        }return found;
    }

    //searches the library by the year returns ArrayList if found
    public ArrayList <Book> searchByYear(int year) {
        ArrayList <Book> found = new ArrayList <Book>();

        for (Book b : books) {
            if (b.year() == year) { found.add(b); }
        }return found;
    }


}
