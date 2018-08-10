package ui;

import model.Transcript;

public class Main {
    public static void main(String[] args){
        Transcript t1 = new Transcript("Jane Doe", 7830);
        Transcript t2 = new Transcript("Ada Lovelace", 8853);
        Transcript t3 = new Transcript("Salim T", 1903);


        t1.addGrade("CPSC-210", 3.5);
        t1.addGrade("ENGL-201", 4.0);
        t1.addGrade("CPSC-110", 3.1);
        t3.addGrade("ECON-101", 3.8);
        t3.addGrade("MATH-105", 3.2);
        t2.addGrade("ENG-101", 2.3);

        System.out.print(t1.getStudentName() + ": ");
        t1.printTranscript();

        System.out.println(t1.getGPA());

//        System.out.println(t1.getCourseAndGrade("CPSC-210"));
//        System.out.print(t3.getStudentName() + ": ");
//        t3.printTranscript();

    }
}
