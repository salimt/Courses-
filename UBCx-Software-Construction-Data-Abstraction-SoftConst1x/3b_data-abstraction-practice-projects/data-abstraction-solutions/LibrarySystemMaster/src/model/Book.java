package model;

public class Book {

    private String title;
    private String author;
    private BookType type;
    private int year;
    private int edition;
    private Library homeLibrary;
    private boolean isOnLoan;

    public Book(String title, String author, BookType type, int year, int ed) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.year = year;
        edition = ed;
        homeLibrary = null;
        isOnLoan = false;
    }

    // getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookType getType() { return type; }
    public int getYear() { return year; }
    public int getEdition() { return edition; }
    public Library getHomeLibrary() { return homeLibrary; }
    public boolean onLoan() { return isOnLoan; }

    // MODIFIES: this
    // EFFECTS: sets a field in this book such that it is on loan
    public void nowOnLoan() { isOnLoan = true;  }

    // MODIFIES: this
    // EFFECTS: set a field in this book such that it is no longer on loan
    public void notOnLoan() { isOnLoan = false; }

    // REQUIRES: home != null
    // MODIFIES: this
    // EFFECTS: sets this book's home library to the one passed as parameter
    public void setHomeLibrary(Library home) {
        homeLibrary = home;
        home.storeBook(this);
    }


}
