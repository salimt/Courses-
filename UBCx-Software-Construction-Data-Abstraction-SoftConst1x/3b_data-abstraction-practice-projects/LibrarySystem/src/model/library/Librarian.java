package model.library;

import model.book.Book;

public class Librarian {

    private String name;
    private int age;
    private Library managingLibrary;
    private Book favBook;

    public Librarian(String name, int age, Library lib, Book bk) {
        this.name = name;
        this.age = age;
        this.managingLibrary = lib;
        this.favBook = bk;
    }

    // getters
    public String getName() { return name; }

    public int getAge() { return age; }

    public Library getManagingLibrary() { return managingLibrary; }

    public Book getFavBook() { return favBook; }

    // REQUIRES: lib != null
    // MODIFIES: this
    // EFFECTS: changes this librarian's managing library to the one given.
    public boolean changeLibrary(Library lib) {
        if(managingLibrary.getManager() != null){ return false; }
        this.managingLibrary = lib;
        return true;
    }


}
