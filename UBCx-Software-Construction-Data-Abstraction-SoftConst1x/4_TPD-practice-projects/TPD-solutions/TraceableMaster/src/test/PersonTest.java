package test;

import model.Fingerprint;
import model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PersonTest {

    private Person person;
    private Fingerprint fp;

    @Before
    public void setUp() {
        person = new Person("Yvonne Chan", 45, "Vancouver");
        fp = new Fingerprint(person, "Metro Vancouver");
    }

    @Test
    public void testGetters() {
        assertEquals(person.getName(), "Yvonne Chan");
        assertEquals(person.getAge(), 45);
        assertEquals(person.getFingerprint(), fp);
        assertEquals(person.getLocation(), "Vancouver");
        assertEquals(person.getTrace(), person);
    }

    @Test
    public void testTrack() {
        person.track();
    }


}