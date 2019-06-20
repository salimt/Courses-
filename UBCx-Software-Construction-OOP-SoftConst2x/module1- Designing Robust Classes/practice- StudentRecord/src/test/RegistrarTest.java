package test;

import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;
import model.Course;
import model.Registrar;
import model.Transcript;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RegistrarTest {

    private Registrar testReg;
    private Transcript testTct1;
    private Transcript testTct2;
    private Course CPSC110;
    private Course CPSC121;

    @Before
    public void setUp() {
        testReg = new Registrar("Samwise Gamgee");
        testTct1 = new Transcript("Paul Carter", 1, 10639152);
        testTct2 = new Transcript("John Appleseed", 3, 10932043);
        CPSC110 = new Course("Science", "Computation, Programs, and Programming", 22, 89.9);
        CPSC121 = new Course("Science", "Models of Computation", 11, 86.3);
    }

    @Test
    public void testGetters() {
        assertEquals(testReg.getName(),"Samwise Gamgee");
        assertTrue(testReg.getStudents().isEmpty());
    }

    @Test
    public void testaddStudent() {
        assertTrue(testReg.getStudents().isEmpty());
        assertTrue(testReg.addStudent(testTct1));
        assertTrue(testReg.getStudents().contains(testTct1));
        assertFalse(testReg.addStudent(testTct1));
        assertEquals(testReg.getStudents().size(),1);
        assertTrue(testReg.addStudent(testTct2));
        assertTrue(testReg.getStudents().contains(testTct2));
    }

    @Test
    public void testpromoteAllStudents() throws GPATooLowException, NoCoursesTakenException {
        testTct1.addToPastCourses(CPSC110);
        testTct2.addToPastCourses(CPSC121);

        assertEquals(testTct1.getAcademicYear(),1);
        assertEquals(testTct2.getAcademicYear(),3);

        testReg.addStudent(testTct1);
        testReg.addStudent(testTct2);
        testReg.promoteAllStudents();

        assertEquals(testTct1.getAcademicYear(),2);
        assertEquals(testTct2.getAcademicYear(),4);
    }

    @Test
    public void testregisterStudent() throws MissingPrereqException, CourseFullException {
        assertFalse(CPSC110.isCourseFull());
        assertEquals(CPSC110.getEnrollment(),22);
        testReg.registerStudent(CPSC110, testTct1);
        assertEquals(CPSC110.getEnrollment(),23);
        assertTrue(testTct1.getCurrentCourses().contains(CPSC110));
    }


}