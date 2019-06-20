package test;

import model.Course;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourseTest {

    private Course testCourse1;
    private Course testCourse2;

    @Before
    public void setUp() {
        testCourse1 = new Course("Science", "CPSC110", 44, 80.4);
        testCourse2 = new Course("Science", "CPSC210", 32, 77.9);
    }

    @Test
    public void testGetters() {
        assertEquals(testCourse1.getFaculty(),"Science");
        assertEquals(testCourse1.getName(),"CPSC110");
        assertTrue(testCourse1.getPrereq().isEmpty());
        assertEquals(testCourse1.getGrade(),80.4, 0.05);
    }

    @Test
    public void testisCourseFull() {
        assertFalse(testCourse1.isCourseFull());
        for (int i = 0; i < 5; i++) { testCourse1.addStudent(); }
        assertFalse(testCourse1.isCourseFull());
        testCourse1.addStudent();
        assertTrue(testCourse1.isCourseFull());
    }

    @Test
    public void testaddPrereq() {
        assertTrue(testCourse2.getPrereq().isEmpty());
        testCourse2.addPrereq(testCourse1);
        assertFalse(testCourse2.getPrereq().isEmpty());
        assertTrue(testCourse2.getPrereq().contains(testCourse1));
    }


}