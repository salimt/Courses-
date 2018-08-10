package model.library;

import model.literature.Book;

public class Librarian {

    private String name;
    private int age;
    private Library managingLibrary;
    private Book favBook;

    public Librarian(String name, int age, Library lib, Book bk) {
       this.name = name;
       this.age = age;
       managingLibrary = lib;
       lib.hireLibrarian(this);
       favBook = bk;
    }

    // getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public Library getManagingLibrary() { return managingLibrary; }
    public Book getFavBook() { return favBook; }


}