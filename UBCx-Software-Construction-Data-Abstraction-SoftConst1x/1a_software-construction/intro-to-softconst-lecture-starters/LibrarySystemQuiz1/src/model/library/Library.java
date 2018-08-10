package model.library;

import model.literature.Book;
import model.literature.BookType;

import java.util.LinkedList;
import java.util.List;

import static model.literature.BookType.*;

public class Library {

    private Librarian manager;
    private String name;
    private List<Library> branches;
    private List<Book> referenceBooks;
    private List<Book> fictionBooks;
    private List<Book> nonfictionBooks;
    private List<Book> textBooks;
    private List<Book> cookBooks;

    public Library(String name) {
        this.name = name;
        manager = null;
        branches = new LinkedList<>();
        referenceBooks = new LinkedList<>();
        fictionBooks = new LinkedList<>();
        nonfictionBooks = new LinkedList<>();
        textBooks = new LinkedList<>();
        cookBooks = new LinkedList<>();
    }

    // getters
    public String getName() { return name; }
    public Librarian getManager() { return manager; }

    // REQUIRES: bk != null
    // MODIFIES: this
    // EFFECTS: stores the given Book bk into the appropriate container within this class
    public void storeBook(Book bk) {
       BookType genre = bk.getType();

       if (genre.equals(REFERENCE)) {
           referenceBooks.add(bk);
       } else if (genre.equals(NONFICTION)) {
           nonfictionBooks.add(bk);
       } else if (genre.equals(FICTION)) {
           fictionBooks.add(bk);
       } else if (genre.equals(TEXTBOOK)) {
           textBooks.add(bk);
       } else if (genre.equals(COOKING)) {
           cookBooks.add(bk);
       }
    }

    // REQUIRES: bk != null
    // EFFECTS: return true if the given literature is in the catalogue,
    //          regardless of its loan status, else return false
    public boolean inCatalogue(Book bk) {
        BookType genre = bk.getType();

        if (genre.equals(REFERENCE)) {
            return referenceBooks.contains(bk);
        } else if (genre.equals(NONFICTION)) {
            return nonfictionBooks.contains(bk);
        } else if (genre.equals(FICTION)) {
            return fictionBooks.contains(bk);
        } else if (genre.equals(TEXTBOOK)) {
            return textBooks.contains(bk);
        } else if (genre.equals(COOKING)) {
            return cookBooks.contains(bk);
        }

        return false;
    }

    // REQUIRES: bk != null
    // EFFECTS: return true if the given Book is available to loan
    //          Note: What requirements should a Book meet to be available?
    public boolean canLoan(Book bk) {
        return inCatalogue(bk) && !bk.onLoan();
    }

    // REQUIRES: bk != null
    // MODIFIES: this
    // EFFECTS: set bk as being checked out from this library if possible
    //          return true if successful, else false
    public boolean checkOutBook(Book bk) {
        if (this.canLoan(bk)) {
            bk.setLoanStatus(true);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: bk != null
    // MODIFIES: this
    // EFFECTS: set bk as being back in the library if it has been borrowed previously
    //          return true if successful, otherwise false
    public boolean returnBook(Book bk) {
        if (bk.onLoan()) {
            bk.setLoanStatus(false);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: manager != null
    // MODIFIES: this
    // EFFECTS: sets this library's librarian to manager; return true if successful, else false
    public boolean hireLibrarian(Librarian manager) {
        if (this.manager == null) {
            this.manager = manager;
            return true;
        } else {
            return false;
        }

    }

    // Utility method, do not touch its implementation
    public void printCatalogue() {
        List<Book> totalCatalogue = new LinkedList<>();
        totalCatalogue.addAll(this.cookBooks);
        totalCatalogue.addAll(this.fictionBooks);
        totalCatalogue.addAll(this.nonfictionBooks);
        totalCatalogue.addAll(this.textBooks);
        totalCatalogue.addAll(this.referenceBooks);

        System.out.println("Here's the catalogue: \n");
        for (Book b : totalCatalogue) {
            System.out.println("'" + b.getTitle() + "'" + " by " + b.getAuthor());
        }
    }


}