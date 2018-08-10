package test;

import model.book.Book;
import model.library.Librarian;
import model.library.Library;
import org.junit.Before;
import org.junit.Test;

import static model.book.BookType.*;
import static org.junit.Assert.assertEquals;

public class BookTest {

    private Book refBook;
    private Book ficBook;
    private Book nonficBook;
    private Book textBook;
    private Book cookBook;
    private Library testLib;
    private Librarian testLibrarian;

    @Before
    public void setUp() {
        testLib = new Library("Vancouver Public model.library.Library", testLibrarian);
        testLibrarian = new Librarian("Alan Kay", 11, testLib, ficBook);

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
    }

    @Test
    public void testgetters() {
        assertEquals(refBook.getTitle(), "Encyclopedia Britannica");
        assertEquals(textBook.getAuthor(), "Thomas H. Cormen");
        assertEquals(cookBook.getType(), COOKING);
        assertEquals(ficBook.getYear(), 2007);
        assertEquals(cookBook.getEdition(), 1);
        assertEquals(cookBook.getHomeLibrary(), testLib);
    }

    @Test
    public void testloanStatus() {
        assertEquals(refBook.onLoan(), false);
        refBook.nowOnLoan();
        assertEquals(refBook.onLoan(), true);
        refBook.notOnLoan();
        assertEquals(refBook.onLoan(), false);
    }


}