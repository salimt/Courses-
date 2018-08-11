import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.*;
import static org.junit.Assert.*;

public class ReformatoryTest {
    Reformatory ref;
    Person pekka;
    Random arpa = new Random();

    Reflex.ClassRef<Object> klass;
    String klassName = "Reformatory";

    @Before
    public void haeLuokka() {
        klass = Reflex.reflect(klassName);
    }    
    
    @Before
    public void setUp() {
        ref = new Reformatory();
        pekka = new Person("Pekka", 33, 175, 78);
    }

    @Points("85.1")
    @Test
    public void test1() {
        saniteettitarkastus();
    }

    @Test
    @Points("85.1")
    public void test2() {
        assertEquals("check code: ref = Reformatory(); "
                + "h = new Person(\"Pekka\", 33, 175, 78); "
                + "System.out.println( ref.weight(h) )", pekka.getWeight(), ref.weight(pekka));

        for (int i = 0; i < 5; i++) {
            int paino = 60 + arpa.nextInt(60);
            Person mikko = new Person("Mikko", 45, 181, paino);

            assertEquals("check code: ref = Reformatory(); "
                    + "h = new Person(\"Mikko\", 45, 181, " + paino + "); "
                    + "System.out.println( ref.weight(h) )", mikko.getWeight(), ref.weight(mikko));
        }
    }

    @Points("85.2")
    @Test
    public void test3() {
        saniteettitarkastus();
    }

    @Points("85.2")
    @Test
    public void test4() throws Throwable {
        String method = "feed";

        Person h = new Person("Pekka", 20, 175, 85);
        Reformatory k = new Reformatory();
        
        assertTrue("add class " + klassName + " method public void " + method + "(Person h) ", 
                klass.method(k, method).returningVoid().taking(Person.class).isPublic());

        String v = "\nCode that caused the problem "
                + "k = new Reformatory(); h = new Person(\"Pekka\", 20, 175, 85); k.weight(h);";

        klass.method(k, method).returningVoid().taking(Person.class).withNiceError(v).invoke(h);
    }    

    @Test
    @Points("85.2")
    public void test5() {
        int odotettu = pekka.getWeight() + 1;
        feed(ref, pekka);

        assertEquals("Feeding should increase the weight by one kilo. Check code: \n"
                + "ref = Reformatory(); "
                + "h = new Person(\"Pekka\", 33, 175, 78); "
                + "ref.feed(h); "
                + "System.out.println( h.getWeight() )",
                odotettu, pekka.getWeight());

        assertEquals("Feeding should increase the weight by one kilo. Check code: \n"
                + "ref = Reformatory(); "
                + "h = new Person(\"Pekka\", 33, 175, 78); "
                + "ref.feed(h); "
                + "System.out.println( ref.getWeight(h) )",
                odotettu, ref.weight(pekka));

        feed(ref, pekka);
        feed(ref, pekka);
        feed(ref, pekka);
        feed(ref, pekka);

        assertEquals("Feeding should increase the weight by one kilo. Check code: \n"
                + "ref = Reformatory(); "
                + "pekka = new Person(\"Pekka\", 33, 175, 78); "
                + "ref.feed(pekka); "
                + "ref.feed(pekka); "
                + "ref.feed(pekka); "
                + "ref.feed(pekka); "
                + "ref.feed(pekka); "
                + "System.out.println( pekka.getWeight() )",
                odotettu + 4, pekka.getWeight());
    }

    @Points("85.3")
    @Test
    public void test6() {
        saniteettitarkastus();
    }

    @Test
    @Points("85.3")
    public void test7()  throws Throwable {
        String method = "totalWeightsMeasured";

        Reformatory k = new Reformatory();
        
        assertTrue("add class " + klassName + " method public int " + method + "() ", 
                klass.method(k, method).returning(int.class).takingNoParams().isPublic());

        String v = "\nCode that caused the problem "
                + "k = new Reformatory; k.totalWeightsMeasured();";

        klass.method(k, method).returning(int.class).takingNoParams().withNiceError(v).invoke();
    }         
    
    @Test
    @Points("85.3")
    public void tehtyjenPunnitustenMaaraMuistissa() {
        assertEquals("Does the method totalWeightsMeasured() work? "
                + "At the beginning no weights measured! Check code\n"
                +"ref = Reformatory(); "
                + "System.out.println( ref.totalWeightsMeasured() )", 
                0, totalWeightsMeasured(ref));
        
        ref.weight(pekka);
        assertEquals("Does the method totalWeightsMeasured() work? "
                + "It should return how many times the method weight() has been called "
                        + "Check code\n"
                + "ref = Reformatory(); "
                + "h1 = new Person(\"Pekka\", 33, 175, 78); "
                + "ref.weight(h1);"
                + "System.out.println( ref.totalWeightsMeasured() )", 
                1, totalWeightsMeasured(ref));
        
        Person mikko = new Person("Mikko", 0, 52, 4);
        ref.weight(mikko);
        assertEquals("Does the method totalWeightsMeasured() work? "
                + "It should return how many times the method weight() has been called "
                        + "Check code\n"
                + "ref = Reformatory(); "
                + "h1 = new Person(\"Pekka\", 33, 175, 78); "
                + "h2 = new Person(\"Mikko\", 0, 52, 4); "
                + "ref.weight(h1);"
                + "ref.weight(h2);"
                + "System.out.println( ref.totalWeightsMeasured() )", 
                2, totalWeightsMeasured(ref));   
        
        ref.weight(mikko);
        ref.weight(pekka);
        ref.weight(pekka);
               assertEquals("Does the method totalWeightsMeasured() work? "
                + "It should return how many times the method weight() has been called "
                        + "Check code\n"
                + "ref = Reformatory(); "
                + "h1 = new Person(\"Pekka\", 33, 175, 78); "
                + "h2 = new Person(\"Mikko\", 0, 52, 4); "
                + "ref.weight(h1);"
                + "ref.weight(h2);"
                + "ref.weight(h2);"
                + "ref.weight(h1);"
                + "ref.weight(h1);"
                + "System.out.println( ref.totalWeightsMeasured() )", 
                5, totalWeightsMeasured(ref));           
    }
    
    String luokanNimi = "Reformatory";

    private void feed(Object ref, Person hlo) {
        try {
            Method feed = ReflectionUtils.requireMethod(Reformatory.class, "feed", Person.class);
            ReflectionUtils.invokeMethod(void.class, feed, ref, hlo);
        } catch (Throwable t) {
        }
    }
    
    private int totalWeightsMeasured(Object ref) {
        try {
            Method totalWeightsMeasured = ReflectionUtils.requireMethod(Reformatory.class, "totalWeightsMeasured");
            return ReflectionUtils.invokeMethod(int.class, totalWeightsMeasured, ref);
        } catch (Throwable t) {
        }
        return -1;
    }    

    private void saniteettitarkastus() throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString()), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString()), field.toString().contains("private"));
        }


        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("The class " + luokanNimi + " needs only instance variable for the amount of weightings, remove the extra "
                    + "", var < 2);
        }
    }
        

    private String kentta(String toString) {
        return toString.replace(luokanNimi + ".", "");
    }
}
