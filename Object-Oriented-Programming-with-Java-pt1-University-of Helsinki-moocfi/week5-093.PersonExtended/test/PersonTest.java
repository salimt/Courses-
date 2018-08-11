
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    @Points("93.1")
    public void ageTest1() {
        Calendar nyt = Calendar.getInstance();
        int vuosi = nyt.get(Calendar.YEAR);
        int kuukausi = nyt.get(Calendar.MONTH) + 1;
        int paiva = nyt.get(Calendar.DATE);
        Person henkilo = new Person("Sälli", paiva, kuukausi, vuosi);
        int ika = henkilo.age();
        assertTrue("The age of person born today should be 0."
                + ""
                + "returned age was: " + ika, (ika == 0));
    }

    @Test
    @Points("93.1")
    public void ageTest2() {
        Calendar nyt = Calendar.getInstance();

        nyt.setLenient(true);
        nyt.add(Calendar.YEAR, -1);

        int vuosi = nyt.get(Calendar.YEAR);
        int kuukausi = nyt.get(Calendar.MONTH) + 1;
        int paiva = nyt.get(Calendar.DATE);
        Person henkilo = new Person("Sälli", paiva, kuukausi, vuosi);
        int ika = henkilo.age();
        assertTrue("The age of person born year ago should be 1, "
                + "returned age was: " + ika, (ika == 1));
    }

    @Test
    @Points("93.1")
    public void ageTest3() {
        Calendar nyt = Calendar.getInstance();

        nyt.setLenient(true);
        nyt.add(Calendar.YEAR, -1);
        nyt.add(Calendar.DATE, 1);

        int vuosi = nyt.get(Calendar.YEAR);
        int kuukausi = nyt.get(Calendar.MONTH) + 1;
        int paiva = nyt.get(Calendar.DATE);
        Person henkilo = new Person("Sälli", paiva, kuukausi, vuosi);
        int ika = henkilo.age();
        assertTrue("The age of person born 364 days ago should be 0 "
                + "returned age was: " + ika, (ika == 0));
    }

    @Test
    @Points("93.1")
    public void ageTest4() {
        Calendar nyt = Calendar.getInstance();

        nyt.setLenient(true);
        nyt.add(Calendar.YEAR, -27);
        nyt.add(Calendar.MONTH, 5);
        nyt.add(Calendar.DATE, 27);

        int vuosi = nyt.get(Calendar.YEAR);
        int kuukausi = nyt.get(Calendar.MONTH) + 1;
        int paiva = nyt.get(Calendar.DATE);
        Person henkilo = new Person("Sälli", paiva, kuukausi, vuosi);
        int ika = henkilo.age();
        assertTrue("The age of person born more than 26 years ago but less than 27 years ago should be 26"
                + ". Returned age was: " + ika, (ika == 26));
    }

    @Test
    @Points("93.2")
    public void olderThanTest1() {
        Person h1 = new Person("Jarmo", 15, 9, 1954);
        Person h2 = new Person("Petteri", 4, 4, 1978);
        assertTrue("Person (" + h1 + ") should be older than (" + h2 + ")", h1.olderThan(h2));
        assertFalse("Person (" + h2 + ") should NOT be older than (" + h1 + ")", h2.olderThan(h1));
    }

    @Test
    @Points("93.2")
    public void olderThanTest2() {
        Person h1 = new Person("Helga", 31, 12, 2009);
        Person h2 = new Person("Janika", 1, 1, 2010);
        assertTrue("Person " + h1 + " should be older than " + h2, h1.olderThan(h2));
        assertFalse("Person " + h2 + " should NOT be older than " + h1, h2.olderThan(h1));
    }

    @Points("93.3")
    @Test
    public void olderThanTest3() {
        try {
            Person.class.getConstructor(String.class, MyDate.class);
        } catch (Throwable e) {
            fail("class Person should have constructor: public Person(String name, MyDate birthday) {...}");
        }
        try {
            Person.class.getConstructor(String.class);
        } catch (Throwable e) {
            fail("class Person should have constructor: public Person(String name) {...}");
        }
    }

    @Points("93.3")
    @Test
    public void personTest() {
        Constructor<Person> c1;
        try {
            c1 = Person.class.getConstructor(String.class, MyDate.class);
        } catch (Throwable e) {
            fail("class Person should have constructor: public Person(String name, MyDate birthday) {...}");
            return;
        }

        Person h1;
        try {
            h1 = c1.newInstance("Sepe", new MyDate(29, 2, 2012));
        } catch (Throwable e) {
            fail("call new Person(\"Sepe\", new MyDate(29, 2, 2012)); caused exception " + e.toString());
            return;
        }

        String string = h1.toString();
        assertTrue("With call new Person(\"Sepe\", new MyDate(29, 2, 2012));"
                + "the given date should be used as birthday",
                string.contains("29.2.2012"));
        assertTrue("With call new Person(\"Sepe\", new MyDate(29, 2, 2012));"
                + "the given name should be used as name of the person",
                string.contains("Sepe"));

        Constructor<Person> c2;
        try {
            c2 = Person.class.getConstructor(String.class);
        } catch (Throwable e) {
            fail("class Person should have constructor: public Person(String name) {...}");
            return;
        }

        Person h2;
        try {
            h2 = c2.newInstance("Sade");
        } catch (Throwable e) {
            fail("call new Person(\"Sepe\"); caused exception " + e.toString());
            return;
        }

        Calendar nyt = Calendar.getInstance();
        int vuosi = nyt.get(Calendar.YEAR);
        int kuukausi = nyt.get(Calendar.MONTH) + 1;
        int paiva = nyt.get(Calendar.DATE);

        string = h2.toString();
        assertTrue("With call new Person(\"Sepe\");"
                + "this day should be used as birthday",
                string.contains(" " + paiva + "." + kuukausi + "." + vuosi));
        assertTrue("With call new Person(\"Sepe\");"
                + "the name of the person should be Sepe",
                string.contains("Sade"));
    }
}
