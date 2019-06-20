package test;

import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;
import model.Course;
import model.Transcript;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TranscriptTest {

    private Transcript testTct;
    private Course CPSC110;
    private Course CPSC121;
    private Course CPSC210;
    private Course CPSC221;
    private Course CPSC213;
    private Course CPSC320;

    @Before
    public void setUp() {
        CPSC110 = new Course("Science", "Computation, Programs, and Programming", 22, 89.9);
        CPSC121 = new Course("Science", "Models of Computation", 11, 86.3);
        CPSC210 = new Course("Science", "Introduction to Software Construction", 30, 88.4);
        CPSC221 = new Course("Science", "Basic Data Structures and Algorithms", 23, 88.3);
        CPSC213 = new Course("Science", "Introduction to Computer Systems", 13, 78.7);
        CPSC320 = new Course("Science", "Intermediate Algorithm Design and Analysis", 44, 90.4);

        testTct = new Transcript("John Appleseed", 2, 10639152);
    }

    @Test
    public void testGetters() {
        assertEquals(testTct.getName(),"John Appleseed");
        assertEquals(testTct.getAcademicYear(),2);
        assertEquals(testTct.getId(),10639152);
        assertTrue(testTct.getCurrentCourses().isEmpty());
        assertTrue(testTct.getPastCourses().isEmpty());
    }

    @Test
    public void computeGPAWithException() {
        assertTrue(testTct.getPastCourses().isEmpty());
        try {
            testTct.computeGPA();
        } catch (NoCoursesTakenException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void computeGPANoException() {
        assertTrue(testTct.getPastCourses().isEmpty());
        testTct.addToPastCourses(CPSC110);
        try {
            double testResult = testTct.computeGPA();
            assertEquals(testResult,3.495, 0.05);
        } catch (NoCoursesTakenException e) {
            fail("Exception should not have been thrown.");
        }

        testTct.addToPastCourses(CPSC121);

        try {
            double testResult = testTct.computeGPA();
            assertEquals(testResult,3.405, 0.05);
        } catch (NoCoursesTakenException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void testpromoteStudentNoException() {
        testTct.addToPastCourses(CPSC110);
        testTct.addToPastCourses(CPSC210);
        assertEquals(testTct.getAcademicYear(),2);

        try {
            assertTrue(testTct.promoteStudent());
        } catch (GPATooLowException | NoCoursesTakenException e) {
            fail("Exception should not have been thrown.");
        }
        assertEquals(testTct.getAcademicYear(),3);
    }

    @Test
    public void testpromoteStudentNoCoursesException() {

        assertEquals(2, testTct.getAcademicYear());

        try {
            assertFalse(testTct.promoteStudent());
        } catch (GPATooLowException e) {
            fail("This is the incorrect exception.");
        } catch (NoCoursesTakenException ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(2, testTct.getAcademicYear());
    }

    @Test
    public void testpromoteStudentGPATooLowException() {
        Course CPEN261 = new Course("Applied Science", "Computer Systems", 33, 22.5);
        testTct.addToPastCourses(CPEN261);
        assertEquals(testTct.getAcademicYear(),2);
        try {
            testTct.promoteStudent();
        } catch (GPATooLowException | NoCoursesTakenException e) {
            System.out.println(e.getMessage());
        }
        assertEquals(testTct.getAcademicYear(),2);
    }

    @Test
    public void testaddCourseNoException() {
        assertTrue(testTct.getCurrentCourses().isEmpty());
        try {
            testTct.addCourse(CPSC110);
            assertTrue(testTct.getCurrentCourses().contains(CPSC110));
        } catch (MissingPrereqException | CourseFullException e) {
            fail("Exception should not have been thrown.");
        }
        assertFalse(testTct.getCurrentCourses().isEmpty());
        assertTrue(testTct.getCurrentCourses().contains(CPSC110));
    }

    @Test
    public void testaddCourseWithMissingPrereqException() {
        CPSC320.addPrereq(CPSC221);
        assertTrue(testTct.getPastCourses().isEmpty());
        try {
            testTct.addCourse(CPSC320);
        } catch (MissingPrereqException e1) {
            System.out.println(e1.getMessage());
        } catch (CourseFullException e2) {
            fail("This exception was incorrectly thrown.");
        }
    }

    @Test
    public void testaddCourseWithCourseFullException() {
        for (int i = 0; i < 28; i++) { CPSC110.addStudent(); }

        try {
            testTct.addCourse(CPSC110);
        } catch (MissingPrereqException e1) {
            fail("This exception was incorrectly thrown");
        } catch (CourseFullException e2) {
            System.out.println(e2.getMessage());
        }
    }


}