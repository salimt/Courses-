package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Library {

    private Librarian manager;
    private String name;
    private List<Library> branches;
    private List<Book> referenceBooks;
    private List<Book> fictionBooks;
    private List<Book> nonfictionBooks;
    private List<Book> textBooks;
    private List<Book> cookBooks;

    public Library(String nm, Librarian manager) {
        name = nm;
        this.manager = manager;
        branches = new ArrayList<>();
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
    // EFFECTS: stores the given Book (bk) into the appropriate container within this class
    public void storeBook(Book bk) {
        BookType genre = bk.getType();

        switch (genre) {
            case REFERENCE:
                this.referenceBooks.add(bk);
                break;
            case NONFICTION:
                this.nonfictionBooks.add(bk);
                break;
            case FICTION:
                this.fictionBooks.add(bk);
                break;
            case TEXTBOOK:
                this.textBooks.add(bk);
                break;
            case COOKING:
                this.cookBooks.add(bk);
                break;
        }
    }

    // REQUIRES: bk != null
    // EFFECTS: return true if the given book is in the catalogue, regardless
    //          of its loan status, else return false
    public boolean inCatalogue(Book bk) {
        BookType searchType = bk.getType();

        switch(searchType) {
            case REFERENCE:
                return referenceBooks.contains(bk);
            case NONFICTION:
                return nonfictionBooks.contains(bk);
            case FICTION:
                return fictionBooks.contains(bk);
            case TEXTBOOK:
                return textBooks.contains(bk);
            case COOKING:
                return cookBooks.contains(bk);
            default:
                return false;
        }
    }

    // REQUIRES: bk != null
    // EFFECTS: return true if the given book is available to loan. Note: is it enough for the book to be
    //          in the catalogue? What if the books is already on loan?
    public boolean canLoan(Book bk) {
        return !bk.onLoan() && inCatalogue(bk);
    }

    // REQUIRES: bk != null
    // EFFECTS: return true if the given book is available in the catalogue of this library's
    //          other associated branches, regardless of whether it is on loan or not
    public boolean isInDiffBranch(Book bk) {
        for (Library lib : branches) {
            if (lib.inCatalogue(bk)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: bk != null
    // MODIFIES: this
    // EFFECTS: set bk as being checked out from this library if it is currently not borrowed. Return true if
    //          this is successful, else, return false
    public boolean checkOutBook(Book bk) {
        if (this.canLoan(bk)) {
            bk.nowOnLoan();
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: bk != null
    // MODIFIES: this
    // EFFECTS: set bk as being back in the library if it has been borrowed previously,
    //          return true if successful. Otherwise, the method should return false
    public boolean returnBook(Book bk) {
        if (bk.onLoan()) {
            bk.notOnLoan();
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: manager != null
    // MODIFIES: this
    // EFFECTS: sets this library's librarian to manager, return true if successful, else return false
    public boolean hireLibrarian(Librarian manager) {
        this.manager = manager;
        manager.changeLibrary(this);
        return true;
    }

    // Do not touch the implementation of this method
    public void printCatalogue() {
        List<Book> totalCatalogue = new LinkedList<>();
        totalCatalogue.addAll(this.cookBooks);
        totalCatalogue.addAll(this.fictionBooks);
        totalCatalogue.addAll(this.nonfictionBooks);
        totalCatalogue.addAll(this.textBooks);
        totalCatalogue.addAll(this.referenceBooks);

        for (Book b : totalCatalogue) {
            System.out.println(b.getTitle() + " by " + b.getAuthor());
        }
    }


}
