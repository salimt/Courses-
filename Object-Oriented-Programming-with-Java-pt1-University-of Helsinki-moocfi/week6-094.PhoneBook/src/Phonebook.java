/**
 * author: salimt
 */


import java.util.*;

public class Phonebook {

    private ArrayList<Person> persons;

    public Phonebook() {
        this.persons = new ArrayList <Person>();
    }

    public void add(String name, String number){
        Person A = new Person(name, number);
        persons.add(A);
    }

    public void printAll(){
        for(Person p: persons){
            System.out.println(p);
        }
    }

    public String searchNumber(String name){
        for(Person p: persons){
            if(p.getName().equals(name)){
                return p.getNumber();
            }
        }return "number not known";
    }
}
