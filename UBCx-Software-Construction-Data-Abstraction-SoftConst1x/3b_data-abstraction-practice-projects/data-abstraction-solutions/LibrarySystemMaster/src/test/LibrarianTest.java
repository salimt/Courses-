package test;

import model.Book;
import model.Librarian;
import model.Library;
import org.junit.Before;
import org.junit.Test;

import static model.BookType.*;
import static model.BookType.COOKING;
import static org.junit.Assert.*;

public class LibrarianTest {

    private Book refBook;
    private Book ficBook;
    private Book nonficBook;
    private Book textBook;
    private Book cookBook;
    private Library testLib, testLib2;
    private Librarian testLibrarian;

    @Before
    public void setUp() {
        testLib = new Library("Vancouver Public Library", testLibrarian);
        testLib2 = new Library("Coquitlam Public Library", null);


        refBook = new Book("Encyclopedia Britannica", "John Williams", REFERENCE, 1850, 1);
        refBook.setHomeLibrary(testLib);
        ficBook = new Book("Harry Potter and the Deathy Hallows", "J.K. Rowling", FICTION, 2007, 1);
        ficBook.setHomeLibrary(testLib);
        nonficBook = new Book("The Immmortal Life of Henrietta Lacks", "Rebecca Skloot", NONFICTION, 2008, 1);
        nonficBook.setHomeLibrary(testLib);
        textBook = new Book("Introduction to Algorithms", "Thomas H. Cormen", TEXTBOOK, 1992, 1);
        textBook.setHomeLibrary(testLib);
        cookBook = new Book("Mastering the Art of French Cooking", "Julia Child", COOKING, 1960, 1);
        cookBook.setHomeLibrary(testLib);

        refBook.setHomeLibrary(testLib);
        testLibrarian = new Librarian("Alan Kay", 11, testLib, ficBook);
    }

   @Test
   public void testgetters() {
       assertEquals(testLibrarian.getName(), "Alan Kay");
       assertEquals(testLibrarian.getManagingLibrary(), testLib);
       assertEquals(testLibrarian.getAge(), 11);
       assertEquals(testLibrarian.getFavBook(), ficBook);
   }

    @Test
    public void changeLibrary() {
        testLibrarian.changeLibrary(testLib2);
        assertEquals(testLibrarian.getManagingLibrary(), testLib2);
        testLibrarian.changeLibrary(testLib);
        assertEquals(testLibrarian.getManagingLibrary(), testLib);
    }


}