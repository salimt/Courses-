package tests;

import model.Transcript;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TranscriptTest {

    private Transcript testTranscript;

    @Before
    public void setUp(){
        testTranscript = new Transcript("Salim", 1903);
    }

    @Test
    public void testTemplate(){
//        assertTrue(testTranscript.Transcript("Salim", 1903));
        testTranscript.addGrade("ECON-102", 4);
        testTranscript.addGrade("MATH-101", 3);

        assertEquals(testTranscript.getStudentName(), "Salim");
        assertEquals(testTranscript.getCourseAndGrade("ECON-102"), "ECON-102: 4.0");
        assertEquals(testTranscript.getGPA(), 3.5, 0);
        assertEquals(testTranscript.getStudentId(), 1903);
        assertFalse(testTranscript.getGPA()!=3.5);
        assertTrue(testTranscript.getStudentName()=="Salim");


    }
}
