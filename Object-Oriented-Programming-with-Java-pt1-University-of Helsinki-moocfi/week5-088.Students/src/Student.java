/**
 * author: salimt
 */

public class Student {

    private String name;
    private String studentNumber;

    public Student(String name, String studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
    }

    //getters
    public String getName() { return name; }
    public String getStudentNumber() { return studentNumber; }


    public String toString() { return this.name + " (" + this.studentNumber + ")"; }
}
