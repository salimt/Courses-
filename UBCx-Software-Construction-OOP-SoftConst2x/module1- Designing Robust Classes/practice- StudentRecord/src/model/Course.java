package model;

import java.util.ArrayList;
import java.util.List;

/*
 *@author salimt
 */

public class Course {

    private int DEFAULT_MAX_ENROLLMENT = 50;
    private String faculty;
    private String name;
    private List<Course> prereq;
    private int enrollment;
    private double percentage;

    public Course(String faculty, String name,  int enrollment, double pcnt) {
        this.faculty = faculty;
        this.name = name;
        this.prereq = new ArrayList<>();
        this.enrollment = enrollment;
        percentage = pcnt;
    }

    //getters
    public String getFaculty() { return this.faculty; }
    public String getName() { return this.name; }
    public List<Course> getPrereq() { return this.prereq; }
    public double getGrade() { return this.percentage; }
    public int getEnrollment() { return this.enrollment; }

    public boolean isCourseFull() { return this.enrollment == DEFAULT_MAX_ENROLLMENT; }
    public void addStudent() { this.enrollment++; }
    public void addPrereq(Course c) { this.prereq.add(c); }



}