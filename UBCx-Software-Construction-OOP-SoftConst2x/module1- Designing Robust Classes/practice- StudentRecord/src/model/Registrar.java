package model;
import exceptions.CourseFullException;
import exceptions.GPATooLowException;
import exceptions.MissingPrereqException;
import exceptions.NoCoursesTakenException;

import java.util.LinkedList;
import java.util.List;

/*
 *@author salimt
 */

public class Registrar {

    private String name;
    private List<Transcript> students;

    public Registrar(String name) {
        // TODO: complete the implementation of this method
        this.name = name;
        this.students = new LinkedList <>();
    }

    // getters
    public String getName() {
        return this.name;
    }
    public List<Transcript> getStudents() {
        return this.students;
    }

    // MODIFIES: this
    // EFFECTS: returns true if the student (represented as a transcript) was successfully added to the
    //          Registrar's list. Remember to check if the student already exists in the list
    public boolean addStudent(Transcript stu) {
        if(!this.students.contains(stu)){ return this.students.add(stu); }
        return false;

    }

    // EFFECTS: registers a given student represented by tct to a course c.
    //          if the student is missing the necessary prerequisites throws MissingPrereqException
    //          if the course is full throws CourseFullException
    public boolean registerStudent(Course c, Transcript tct) throws CourseFullException, MissingPrereqException {
        // TODO: complete the implementation of this method
        if(tct.addCourse(c)){
            c.addStudent();
            return true;
        }return false;


    }

    // EFFECTS: promotes all students to their next year level.
    //          if the GPA is too low (2.6 out of 4.0), throws a GPATooLowException
    //          if no courses have been taken, throws a NoCoursesTaken exception
    public void promoteAllStudents() throws GPATooLowException, NoCoursesTakenException {
        // TODO: complete the implementation of this method
        for(Transcript t: students){
            try{
                t.promoteStudent();
            }catch (GPATooLowException e) {
                System.out.println("GPA is too low to promote this student: " + t.getName());
            }catch (NoCoursesTakenException e){
                System.out.println("No courses has been taken by this student: " + t.getName());
            }
        }
    }


}
