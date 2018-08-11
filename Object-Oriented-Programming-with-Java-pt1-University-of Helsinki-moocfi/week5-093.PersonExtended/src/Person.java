import java.util.*;

public class Person {
    private String name;
    private MyDate birthday;
    
    public Person(String name, int pp, int kk, int vv) {
        this.name = name;
        this.birthday = new MyDate(pp, kk, vv);
    }

    public Person(String name, MyDate birthday){
        this.name = name;
        this.birthday = birthday;
    }

    public Person(String name){
        this.name = name;
        this.birthday = new MyDate(Calendar.getInstance().get(Calendar.DATE),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.YEAR));
    }

    
    //calculates the age based on the birthday and the current day
    public int age() {

        MyDate now  = new MyDate(Calendar.getInstance().get(Calendar.DATE),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.YEAR));

        return birthday.differenceInYears(now);
    }

    //compares the ages of the object for which the method is called and the object given as parameter.
    public boolean olderThan(Person compared) {

        if(this.birthday.earlier(compared.birthday)){ return true; }
        return false;
    }
    
    public String getName() { return this.name; }
    
    public String toString() {
        return this.name + ", born " + this.birthday;
    }
}
