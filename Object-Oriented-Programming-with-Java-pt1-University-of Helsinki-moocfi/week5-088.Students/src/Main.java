import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<Student>();

        while(true){
            System.out.print("name: ");
            String name = reader.nextLine();
            if(name.isEmpty()){ break; }
            System.out.print("studentnumber: ");
            String studentNum = reader.nextLine();
            students.add(new Student(name, studentNum));
        }

        System.out.println("");
        for(Student student: students){ System.out.println(student); }

        System.out.print("\nGive search term: ");
        String forSearch = reader.nextLine();
        System.out.println("Result:");
        for(Student student: students){ if(student.getName().contains(forSearch)){ System.out.println(student); }}
    }
}
