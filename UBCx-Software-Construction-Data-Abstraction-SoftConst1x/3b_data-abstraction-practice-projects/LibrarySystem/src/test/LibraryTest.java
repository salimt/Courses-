package test;

import model.book.Book;
import model.library.Librarian;
import model.library.Library;
import org.junit.Before;
import org.junit.Test;

import static model.book.BookType.*;
import static org.junit.Assert.assertEquals;

public class LibraryTest {

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
    }

    @Test
    public void testGetName() {
        assertEquals(testLib.getName(), "Vancouver Public model.library.Library");
    }

    @Test
    public void testinCatalogue() {
        assertEquals(testLib.inCatalogue(refBook), true);
        assertEquals(testLib.inCatalogue(cookBook), true);
        assertEquals(testLib.inCatalogue(ficBook), true);
        assertEquals(testLib.inCatalogue(textBook), true);
    }

    @Test
    public void testcanLoan() {
        assertEquals(testLib.canLoan(refBook), true);
        testLib.checkOutBook(refBook);
        assertEquals(testLib.canLoan(refBook), false);

        assertEquals(testLib.canLoan(cookBook), true);
        testLib.checkOutBook(cookBook);
        assertEquals(testLib.canLoan(cookBook), false);
    }

    @Test
    public void testreturnBook() {
        assertEquals(testLib.canLoan(textBook), true);
        assertEquals(testLib.checkOutBook(textBook), true);
        assertEquals(testLib.canLoan(textBook), false);
        assertEquals(testLib.returnBook(textBook), true);
        assertEquals(testLib.canLoan(textBook), true);
    }


}