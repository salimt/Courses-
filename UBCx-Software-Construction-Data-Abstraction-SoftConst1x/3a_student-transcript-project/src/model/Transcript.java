/**
 * author: salimt
 */

package model;

import java.util.*;

/**
 * INVARIANT: course list and grade list are the same size
 * each course has a grade associated, and vice versa, at matching indices
 */

public class Transcript {
    String name;
    int id;
    List<String> courseNames = new ArrayList<String>();
    List<Double> grades = new ArrayList<Double>();

    //REQUIRES student name and student id number
    public Transcript(String studentName, int studentID){
        name = studentName;
        id = studentID;
    }

    //REQUIRES the grade should be between 0.0 and 4.0, and/or the course should not be null
    //MODIFIES this
    public void addGrade(String course, double grade){
        if(grade >= 0.0 && grade <= 4.0 && course != null){
            courseNames.add(course);
            grades.add(grade);
        }
    }

    //REQUIRES: a course the student has already taken.
    //EFFECTS: returns course name and grade in format CourseName: Grade
    public String getCourseAndGrade(String course){
        int courseIndex = courseNames.indexOf(course);
        return course + ": " + grades.get(courseIndex);
    }

    //EFFECTS: displays student academic record
    public void printTranscript(){
        for(int i=0; i<courseNames.size(); i++){System.out.print(courseNames.get(i) + ": " + grades.get(i) + ", ");}
    }

    //EFFECTS: displays student grade point average(GPA)
    public double getGPA(){ System.out.print("\nGPA: "); return sum(grades)/(double)grades.size(); }

    //EFFECTS: displays student name
    public String getStudentName(){ return name; }

    //EFFECTS: displays student id
    public int getStudentId(){ return id; }

    public double sum(List<Double> grades) {double sum = 0; for(Double i: grades){sum+=i;} return sum;}
}
