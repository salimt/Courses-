
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import fi.helsinki.cs.tmc.edutestutils.Reflex.ClassRef;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class LibraryTest<_Book> {

    private String bookLuokka = "Book";
    private String titleMetodi = "title";
    private String publisherMetodi = "publisher";
    private String julkaisuvuosiMetodi = "year";
    private String bookstoLuokka = "Library";
    private String addBookMetodi = "addBook";
    private String printBooksMetodi = "printBooks";
    private String searchByTitleMetodi = "searchByTitle";
    private String haeBookJulkaisijallaMetodi = "searchByPublisher";
    private String haeBookJulkaisuvuodellaMetodi = "searchByYear";
    private String stringUtilsLuokka = "StringUtils";
    private String includedMetodi = "included";
    private Class bookClass;
    private Class bookstoClass;
    private Class stringUtilsClass;

    @Before
    public void setup() {
        try {
            bookClass = ReflectionUtils.findClass(bookLuokka);
            bookstoClass = ReflectionUtils.findClass(bookstoLuokka);
            stringUtilsClass = ReflectionUtils.findClass(stringUtilsLuokka);
        } catch (Throwable t) {
        }
    }

    @Test
    @Points("101.1")
    public void test1() {
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("Class " + klassName + " should be publid, define it as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("101.1")
    public void test2() {
        saniteettitarkastus(bookLuokka, 3, ""
                + "object variables for title, publisher and publishing year");
    }

    @Test
    @Points("101.1")
    public void test3() throws Throwable {
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.MethodRef3<Object, Object, String, String, Integer> ctor =
                klass.constructor().taking(String.class, String.class, int.class).withNiceError();
        assertTrue("Define the class " + klassName + " the constructor: public " + klassName + "(String title, String publisher, int year)", ctor.isPublic());
        ctor.invoke("Kalevala", "The Carelia publishers", 1700);
    }

    @Test
    @Points("101.1")
    public void test4() throws Throwable {
        String metodi = titleMetodi;
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Carelia Publishers", 1700);;

        assertTrue("add the class " + klassName + " the method public String " + metodi + "() ", klass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());

        String c =
                "Book k = new Book(\"Kalevala\", \"Carelia Publishers\", 1700); "
                + "k.haeNimike();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, "Kalevala", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void booknNimikeMetodi2() throws Throwable {
        String metodi = titleMetodi;
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Book k = new Book(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.haeNimike();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, "Core Java", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void booknJulkaisijaMetodi() throws Throwable {
        String metodi = publisherMetodi;
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Carelia Publishers", 1700);;

        assertTrue("add the class " + klassName + " the method public String " + metodi + "() ", klass.method(olio, metodi)
                .returning(String.class).takingNoParams().isPublic());

        String c =
                "Book k = new Book(\"Kalevala\", \"Carelia Publishers\", 1700); "
                + "k.haeJulkaisija();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, "Carelia Publishers", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void booknJulkasijaMetodi2() throws Throwable {
        String metodi = publisherMetodi;
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Book k = new Book(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.haeJulkaisija();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, "Addison Wesley", klass.method(olio, metodi).returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void test5() throws Throwable {
        String metodi = "year";
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).invoke("Kalevala", "Carelia Publishers", 1700);;

        assertTrue("add the class " + klassName + " the method public int " + metodi + "() ", klass.method(olio, metodi)
                .returning(int.class).takingNoParams().isPublic());

        String c =
                "Book k = new Book(\"Kalevala\", \"The carelia publishers\", 1700); "
                + "k.searchByYear()();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, 1700, (int) klass.method(olio, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void test6() throws Throwable {
        String metodi = julkaisuvuosiMetodi;
        String klassName = bookLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object olio = klass.constructor().taking(String.class, String.class, int.class).
                invoke("Core Java", "Addison Wesley", 2012);

        String c =
                "Book k = new Book(\"Core Java\", \"Addison Wesley\", 2012); "
                + "k.searchByYear();";

        String v = "\nError was caused by the code " + c;

        assertEquals(c, 2012, (int) klass.method(olio, metodi).returning(int.class).takingNoParams().withNiceError(v).invoke());
    }

    @Test
    @Points("101.1")
    public void test7() {
        try {
            if (ReflectionUtils.requireMethod(bookClass, julkaisuvuosiMetodi).getReturnType() != int.class) {
                fail("noes!");
            }
        } catch (Throwable t) {
            fail("Add the class " + bookLuokka + " the method public int " + julkaisuvuosiMetodi + "()?");
        }
    }

    @Test
    @Points("101.1")
    public void test8() throws Throwable {
        assertFalse("Add the class Book the method toString", luoBook("title", "publisher", 51).toString().contains("@"));

        try {
            Object book = luoBook("title", "publisher", 51);
            String tulos = ReflectionUtils.invokeMethod(String.class, ReflectionUtils.requireMethod(bookClass, "toString"), book);

            Assert.assertTrue(tulos.contains("title") && tulos.contains("publisher") && tulos.contains("" + 51));
        } catch (Throwable t) {
            fail("Check that toString() of the class Book return the book information.");
        }
    }

    /*
     * 
     */
    @Test
    @Points("101.2")
    public void test9() {
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("Class " + klassName + " should be public"
                + ", define it as\npublic class " + klassName + " {...\n}", klass.isPublic());
    }

    @Test
    @Points("101.2")
    public void test10() {
        saniteettitarkastus(bookstoLuokka, 1, "object variable storing the books");
    }

    @Test
    @Points("101.2")
    public void test11() throws Throwable {
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.MethodRef0<Object, Object> ctor =
                klass.constructor().takingNoParams().withNiceError();
        assertTrue("Define the class " + klassName + " the constructor: public " + klassName + "()", ctor.isPublic());
        ctor.invoke();
    }

    @Test
    @Points("101.2")
    public void test12() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        assertTrue("add the class " + klassName + " the method public void " + metodi + "(Book newBook) ", klass.method(k, metodi)
                .returningVoid().taking(_BookRef.cls()).isPublic());

        _Book book = newBook(_BookRef, "Kalevala", "Carelia Publishers", 1700);

        String c = ""
                + "Library k = new Library(); \n"
                + "Book book = new Book(\"Kalevala\", \"Carelia Publishers\", 1700);\n"
                + "k.addBook(book)";


        String v = "\nError was caused by the code " + c;

        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError(v).invoke(book);
    }

    @Test
    @Points("101.2")
    public void test13() throws Throwable {
        String metodi = printBooksMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newLibrary(klass);

        assertTrue("add the class " + klassName + " the method public void " + metodi + "() ", klass.method(k, metodi)
                .returningVoid().takingNoParams().isPublic());

        String c = ""
                + "Library k = new Library(); \n"
                + "k.printBooks()";

        String v = "\nError was caused by the code " + c;

        klass.method(k, metodi).returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("101.2")
    public void test14() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        _Book book = newBook(_BookRef, "Kalevala", "Carelia Publishers", 1700);

        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book);

        String c = ""
                + "Library k = new Library(); \n"
                + "Book book = new Book(\"Kalevala\", \"Carelia Publishers\", 1700);\n"
                + "k.addBook(book);\n"
                + "k.printBooks();\n";

        String v = "\nError was caused by the code " + c;

        MockInOut io = new MockInOut("");

        klass.method(k, printBooksMetodi).returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertFalse("The code " + c + "does not print anything", out.isEmpty());
        assertTrue("The code " + c + "should print only one line line. Now it printed\n" + out, out.split("\n").length < 2);

        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Kalevala"));
        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Carelia Publishers"));
        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("170"));

    }

    @Test
    @Points("101.2")
    public void test15() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        _Book book1 = newBook(_BookRef, "Core Java", "Addison Wesley", 2001);
        _Book book2 = newBook(_BookRef, "Test Driven Development", "Addison Wesley", 2001);
        _Book book3 = newBook(_BookRef, "Java Poweruser Guide", "Prentice Hall", 2012);
        
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book1);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book2);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book3);
        
        String c = ""
                + "Library k = new Library(); \n"
                + "Book book1 = new Book(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Book book2 = new Book(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Book book3 = new Book(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.addBook(book1);\n"               
                + "k.addBook(book2);\n"                
                + "k.addBook(book3);\n"
                + "k.printBooks();\n";

        String v = "\nError was caused by the code " + c;

        MockInOut io = new MockInOut("");

        klass.method(k, printBooksMetodi).returningVoid().takingNoParams().withNiceError(v).invoke();

        String out = io.getOutput();

        assertFalse("The code " + c + "did not print anything.", out.isEmpty());
        assertTrue("The code " + c + "should print three lines. Now it printed\n" + out, out.split("\n").length==3);

        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Prentice Hall"));
        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Java Poweruser Guide"));
        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Core Java"));
        assertTrue("Check that " + c + "prints the added book. Now it printed\n" + out, out.contains("Test Driven Development"));
    }

    /*
     * 
     */
    
    @Test
    @Points("101.3")
    public void test16() throws Throwable {
        String metodi = searchByTitleMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newLibrary(klass);

        assertTrue("add the class " + klassName + " the method public ArrayList<Book> " + metodi + "(String title) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(String.class).isPublic());

        String c = ""
                + "Library k = new Library(); \n"
                + "k.searchByTitle(\"Java\")";

        String v = "\nError was caused by the code " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Java");
    }    

    @Test
    @Points("101.3")
    public void test17() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        _Book book1 = newBook(_BookRef, "Core Java", "Addison Wesley", 2001);
        _Book book2 = newBook(_BookRef, "Test Driven Development", "Addison Wesley", 2001);
        _Book book3 = newBook(_BookRef, "Java Poweruser Guide", "Prentice Hall", 2012);
        
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book1);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book2);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book3);
        
        String c = ""
                + "Library k = new Library(); \n"
                + "Book book1 = new Book(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Book book2 = new Book(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Book book3 = new Book(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.addBook(book1);\n"               
                + "k.addBook(book2);\n"                
                + "k.addBook(book3);\n";

        String code = c+"k.searchByTitle(\"Java\")\n";
        
        String v = "\nError was caused by the code " + code;

        ArrayList lista = klass.method(k, searchByTitleMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Java");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",2, lista.size());
        assertTrue("The code\n"+code+"returned incorrect list. It was "+lista.toString(), 
                lista.toString().contains("Core Java") && lista.toString().contains("Java Poweruser Guide") && 
                lista.toString().contains("Addison Wesley") );
               
        code = c+"k.searchByTitle(\"Kahvi\")\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, searchByTitleMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Kahvi");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",0, lista.size());
        
        code = c+"k.searchByTitle(\"Test Driven\")\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, searchByTitleMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Test Driven");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",1, lista.size());   
        assertTrue("The code\n"+code+"returned a wrong list. It was "+lista.toString(), 
                !lista.toString().contains("Core Java") && !lista.toString().contains("Java Poweruser Guide") && 
                lista.toString().contains("Test Driven Development") );           
    }    

    @Test
    @Points("101.3")
    public void team17() throws Throwable {
        String metodi = haeBookJulkaisijallaMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newLibrary(klass);

        assertTrue("add the class " + klassName + " the method public ArrayList<Book> " + metodi + "(String publisher) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(String.class).isPublic());

        String c = ""
                + "Library k = new Library(); \n"
                + "k.searchByTitle(\"Microsoft Press\")";

        String v = "\nError was caused by the code " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Microsoft Press");
    }  

    @Test
    @Points("101.3")
    public void team18() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        _Book book1 = newBook(_BookRef, "Core Java", "Addison Wesley", 2001);
        _Book book2 = newBook(_BookRef, "Test Driven Development", "Addison Wesley", 2001);
        _Book book3 = newBook(_BookRef, "Java Poweruser Guide", "Prentice Hall", 2012);
        
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book1);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book2);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book3);
        
        String c = ""
                + "Library k = new Library(); \n"
                + "Book book1 = new Book(\"Core Java\", \"Addison Wesley\", 2001);\n"
                + "Book book2 = new Book(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Book book3 = new Book(\"Java Poweruser Guide\", \"Prentice Hall\", 2012);\n"
                + "k.addBook(book1);\n"               
                + "k.addBook(book2);\n"                
                + "k.addBook(book3);\n";

        String code = c+"k.searchByPublisher(\"Addison Wesley\")\n";
        
        String v = "\nError was caused by the code " + code;

        ArrayList lista = klass.method(k, haeBookJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Addison Wesley");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",2, lista.size());
        assertTrue("The code\n"+code+"returned a wrong list. It was "+lista.toString(), 
                lista.toString().contains("Core Java") && lista.toString().contains("Test Driven Development") && 
                lista.toString().contains("Addison Wesley") );
               
        code = c+"k.searchByPublisher(\"Springer Verlag\")\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, haeBookJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Springer Verlag");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",0, lista.size());
        
        code = c+"k.searchByPublisher(\"Prentice Hall\")\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, haeBookJulkaisijallaMetodi).returning(ArrayList.class).taking(String.class).withNiceError(v).invoke("Prentice Hall");
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",1, lista.size());   
        assertTrue("The code\n"+code+"returned a wrong list. It was "+lista.toString(), 
                !lista.toString().contains("Core Java") && lista.toString().contains("Java Poweruser Guide") && 
                !lista.toString().contains("Test Driven Development") );           
    }  

    @Test
    @Points("101.3")
    public void team19() throws Throwable {
        String metodi = haeBookJulkaisuvuodellaMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);

        Object k = newLibrary(klass);

        assertTrue("add the class " + klassName + " the method public ArrayList<Book> " + metodi + "(int year) ", klass.method(k, metodi)
                .returning(ArrayList.class).taking(int.class).isPublic());

        String c = ""
                + "Library k = new Library(); \n"
                + "k.searchByTitle(\"2012\")";

        String v = "\nError was caused by the code " + c;

        klass.method(k, metodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2012);
    } 

    @Test
    @Points("101.3")
    public void team20() throws Throwable {
        String metodi = addBookMetodi;
        String klassName = bookstoLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        Reflex.ClassRef<_Book> _BookRef = Reflex.reflect(bookLuokka);

        Object k = newLibrary(klass);

        _Book book1 = newBook(_BookRef, "Core Java", "Addison Wesley", 2012);
        _Book book2 = newBook(_BookRef, "Test Driven Development", "Addison Wesley", 2001);
        _Book book3 = newBook(_BookRef, "Java Poweruser Guide", "Prentice Hall", 2001);
        
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book1);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book2);
        klass.method(k, metodi).returningVoid().taking(_BookRef.cls()).withNiceError().invoke(book3);
        
        String c = ""
                + "Library k = new Library(); \n"
                + "Book book1 = new Book(\"Core Java\", \"Addison Wesley\", 2012);\n"
                + "Book book2 = new Book(\"Test Driven Development\", \"Addison Wesley\", 2001);\n"
                + "Book book3 = new Book(\"Java Poweruser Guide\", \"Prentice Hall\", 2001);\n"
                + "k.addBook(book1);\n"               
                + "k.addBook(book2);\n"                
                + "k.addBook(book3);\n";

        String code = c+"k.teamByPublisher(2001)\n";
        
        String v = "\nError was caused by the code " + code;

        ArrayList lista = klass.method(k, haeBookJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2001);
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",2, lista.size());
        assertTrue("The code\n"+code+"returned a wrong list. It was "+lista.toString(), 
                lista.toString().contains("Java Poweruser Guide") && lista.toString().contains("Test Driven Development") && 
                lista.toString().contains("Addison Wesley") );
               
        code = c+"k.teamByPublisher(1952)\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, haeBookJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(1952);
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",0, lista.size());
        
        code = c+"k.teamByPublisher(2012)\n";
        
        v = "\nError was caused by the code " + code;

        lista = klass.method(k, haeBookJulkaisuvuodellaMetodi).returning(ArrayList.class).taking(int.class).withNiceError(v).invoke(2012);
        
        assertFalse("The code\n"+code+"returned null", lista==null);
        assertEquals("The code\n"+code+"returned list with wrong length",1, lista.size());   
        assertTrue("The code\n"+code+"returned a wrong list. It was "+lista.toString(), 
                lista.toString().contains("Core Java") && !lista.toString().contains("Java Poweruser Guide") && 
                !lista.toString().contains("Test Driven Development") );           
    }  
    
    /*
     * 
     */

    @Test
    @Points("101.4")
    public void team21() {
        String klassName = stringUtilsLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);
        assertTrue("The class " + klassName + " should be public, define it as"
                + "\npublic class " + klassName + " {...\n}", klass.isPublic());
    }    
    


    @Test
    @Points("101.4")
    public void team22() throws Throwable {
        String metodi = includedMetodi;
        String klassName = stringUtilsLuokka;
        Reflex.ClassRef<Object> klass = Reflex.reflect(klassName);    
        
        assertTrue("Add the class StringUtils public static boolean included(String word, String searched)",klass.staticMethod(metodi).returning(boolean.class).taking(String.class, String.class).isPublic());
        
        String v = "Problem caused by StringUtils.included(\"Vihavainen\", \"avain\");";
        klass.staticMethod(metodi).returning(boolean.class).taking(String.class, String.class).withNiceError(v).invoke("Vihavainen", "avain");
    }

    @Test
    @Points("101.4")
    public void test23() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, includedMetodi, String.class, String.class);

            if (!Modifier.isStatic(m.getModifiers())) {
                fail("no-static");
            }
        } catch (Throwable t) {
            fail("Does the class " + stringUtilsLuokka + " have the method public static boolean included(String sana, String haettava) varmasti staattinen?");
        }
    }

    @Test
    @Points("101.4")
    public void test24() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, includedMetodi, String.class, String.class);

            if (ReflectionUtils.invokeMethod(boolean.class, m, null, "abba", "ABBABBA")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("The method call " + stringUtilsLuokka + ".included(\"abba\", \"ABBABBA\") should return false. "
                    + "The string abba does not contain the string ABBABBA");
        }
    }

    @Test
    @Points("101.4")
    public void test25() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, includedMetodi, String.class, String.class);

            if (!ReflectionUtils.invokeMethod(boolean.class, m, null, "ABBABBA", "abba")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("The method call " + stringUtilsLuokka + ".included(\"ABBABBA\", \"abba\") should return true. "
                    + "The string ABBABBA contains the string abba when the character case is ignored.");
        }
    }

    @Test
    @Points("101.4")
    public void test26() {
        try {
            Method m = ReflectionUtils.requireMethod(stringUtilsClass, includedMetodi, String.class, String.class);

            if (!ReflectionUtils.invokeMethod(boolean.class, m, null, "ABBABBA", "  abba")) {
                fail("ei-ole");
            }

        } catch (Throwable t) {
            fail("The method call " + stringUtilsLuokka + ".included(\"ABBABBA\", \"  abba\") should return true. "
                    + "\nThe string ABBABBA contains the string abba if the extra whitespaces are "
                    + "removed from the beginning and the end away.");
        }
    }

    @Test
    @Points("101.4")
    public void test27() {
        stringUtilsKayttaaTrimMetodiaLukija();
    }

    @Test
    @Points("101.4")
    public void test28() {
        bookstoKayttaaStringUtilsiaLukija();
    }

    private Object luoBook(String title, String publisher, int julkaisuvuosi) throws Throwable {
        Constructor bookConstructor = ReflectionUtils.requireConstructor(bookClass, String.class, String.class, int.class);
        return ReflectionUtils.invokeConstructor(bookConstructor, title, publisher, julkaisuvuosi);
    }

    private Object luoLibrary() throws Throwable {
        return ReflectionUtils.invokeConstructor(ReflectionUtils.requireConstructor(bookstoClass));
    }

    private void addLibraryon(Object booksto, Object book) throws Throwable {
        Method addBook = ReflectionUtils.requireMethod(bookstoClass, addBookMetodi, bookClass);
        ReflectionUtils.invokeMethod(void.class, addBook, booksto, book);
    }

    private Object luoLibraryKirjoilla() throws Throwable {
        Object booksto = luoLibrary();
        addLibraryon(booksto, luoBook("How to Write a How to Write Book", "Brian Paddock", 1955));
        addLibraryon(booksto, luoBook("The Broken Window", "Eva Brick", 1974));
        addLibraryon(booksto, luoBook("TheUpperCaseAndLowerCaseMYSTERY", "El Barto", 2012));
        return booksto;
    }

    private void bookstoKayttaaStringUtilsiaLukija() {
        try {
            Scanner lukija = new Scanner(new File("src/Library.java"));
            boolean stringUtils = false;
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("StringUtils")) {
                    stringUtils = true;
                    break;
                }
            }

            if (!stringUtils) {
                fail("Change the class Library so that it uses the StringUtils class!");
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void stringUtilsKayttaaTrimMetodiaLukija() {
        try {
            Scanner lukija = new Scanner(new File("src/StringUtils.java"));
            boolean trim = false;
            while (lukija.hasNext()) {
                String rivi = lukija.nextLine();
                if (rivi.contains("trim")) {
                    trim = true;
                    break;
                }
            }

            if (!trim) {
                fail("Use the trim method of String to remove the leading and trailing whitespaces");
            }
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private void saniteettitarkastus(String luokanNimi, int muuttujia, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString(), luokanNimi), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString(), luokanNimi), field.toString().contains("private"));
        }


        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Class " + luokanNimi + " only needs " + m + ", remove the rest", var <= muuttujia);
        }

    }

    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "");
    }

    private Object newLibrary(ClassRef<Object> klass) throws Throwable {
        Object olio = klass.constructor().takingNoParams().invoke();
        return olio;
    }

    private _Book newBook(ClassRef<_Book> _BookRef, String n, String j, int v) throws Throwable {
        return _BookRef.constructor().taking(String.class, String.class, int.class).invoke(n, j, v);
    }
}
