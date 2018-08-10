package test;

import model.Fingerprint;
import model.Person;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FingerprintTest {

    private Person person;
    private Fingerprint fp;

    @Before
    public void setUp() {
        person = new Person("Yvonne Chan", 45, "Vancouver");
        fp = new Fingerprint(person, "Metro Vancouver");
    }

    @Test
    public void testGetters() {
        assertEquals(fp.getOwner(), person);
        assertEquals(fp.getLocation(), "Metro Vancouver");
        assertEquals(fp.getTrace(), fp);
    }

    @Test
    public void testTrack() {
        fp.track();
    }


}