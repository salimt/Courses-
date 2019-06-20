package model;
/*
 *@author salimt
 */

import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;

import java.util.LinkedList;
import java.util.List;

public class Transcript {

    private String name;
    private int year;
    private int id;
    private double gpa;
    private List<Course> currentCourses;
    private List<Course> pastCourses;

    public Transcript(String studentName, int academicYear, int studentID) {
        this.name = studentName;
        this.year = academicYear;
        this.id = studentID;
        this.gpa = 0;
        this.currentCourses = new LinkedList <>();
        this.pastCourses = new LinkedList <>();
    }

    // getters
    public String getName() { return this.name; }

    public int getAcademicYear() { return this.year; }

    public int getId() { return this.id; }

    public List<Course> getCurrentCourses() { return this.currentCourses; }

    public List<Course> getPastCourses() { return this.pastCourses; }

    // EFFECTS: computes cGPA
    //          if the list of past courses is empty, throws NoCoursesTakenException
    public double computeGPA() throws NoCoursesTakenException {
        if(this.pastCourses.isEmpty())
            throw new NoCoursesTakenException();

        double total = 0;
        for(Course c: this.pastCourses){
            total+=c.getGrade();
        }return (total / pastCourses.size())/20-1;
    }

    // EFFECTS: promotes the student represented by the transcript
    //          to the next academic year if GPA is over 2.6 on a 4.0 scale, and return true
    //          else return false with no change to the year field
    //          if GPA is not over 2.6 on a 4.0 scale, throws GPATooLowException
    //          if no courses have been taken, throws NoCoursesTakenException
    public boolean promoteStudent() throws GPATooLowException, NoCoursesTakenException {
        double GPA = computeGPA();
        if(GPA>=2.6){ this.year++; return true; }
        if(GPA<2.6){
            throw new GPATooLowException();
        }if(getPastCourses().isEmpty()){
            throw new NoCoursesTakenException();
        }return false;
    }


    // MODIFIES: this
    // EFFECTS: adds the given course to the list of past courses and returns true,
    //          unless pastCourses contains given course, then does not add and returns false
    public boolean addToPastCourses(Course c) {
        if(this.pastCourses.contains(c)){ return false; }
        return this.pastCourses.add(c);
    }

    // MODIFIES: this
    // EFFECTS: adds a course (c) into the record
    //          if the transcript is missing prerequisites, throws a MissingPrereqException
    //          if the course has no space for more students to register, throws a CourseFullException
    public boolean addCourse(Course course) throws MissingPrereqException, CourseFullException {
        if(!pastCourses.containsAll(course.getPrereq())){
            throw new MissingPrereqException();
        }if(course.isCourseFull()){
            throw new CourseFullException();
        }return this.currentCourses.add(course);
    }

}
