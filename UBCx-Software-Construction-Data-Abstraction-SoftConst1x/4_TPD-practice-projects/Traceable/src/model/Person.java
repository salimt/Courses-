/**
 * author: salimt
 */

package model;

public class Person implements Traceable {

    private String name;
    private int age;
    private String location;
    private Fingerprint fingerprint;

    public Person(String name, int age, String location) {
        this.name = name;
        this.age = age;
        this.location = location;
        fingerprint = null;
    }

    //getters
    public String getName() { return name; }
    public int getAge() { return age; }
    @Override
    public String getLocation() { return location; }
    public Fingerprint getFingerprint() { return fingerprint; }
    @Override
    public Object getTrace() { return this; }

    //setters
    public void setFingerprint(Fingerprint fingerprint) {
        this.fingerprint = fingerprint;
    }

    @Override
    public void track() {
        System.out.println("This person is located at: " + location);
    }

}
