package model.literature;

import model.library.Library;

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

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public BookType getType() { return type; }
    public int getYear() { return year; }
    public int getEdition() { return edition; }
    public Library getHomeLibrary() { return homeLibrary; }
    public boolean onLoan() { return isOnLoan; }

    // setters
    public void setLoanStatus(boolean b) { isOnLoan = b; }

    // REQUIRES: home != null
    // MODIFIES: this
    // EFFECTS: sets this literature's home library to the one passed as parameter, and return true.
    //          If unsuccessful, the method should return false
    public boolean setHomeLibrary(Library home) {
        if (home == null) {
            return false;
        } else {
            homeLibrary = home;
            home.storeBook(this);
            return true;
        }
    }


}