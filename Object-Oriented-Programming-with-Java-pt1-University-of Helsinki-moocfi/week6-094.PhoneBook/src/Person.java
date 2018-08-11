/**
 * author: salimt
 */


public class Person {

    private String name;
    private String phoneNum;

    public Person(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    //getters
    public String getName() { return name; }
    public String getNumber() { return phoneNum; }

    public void changeNumber(String newNumber){ this.phoneNum = newNumber; }

    @Override
    public String toString() {
        return name + " number: " + phoneNum;
    }
}
