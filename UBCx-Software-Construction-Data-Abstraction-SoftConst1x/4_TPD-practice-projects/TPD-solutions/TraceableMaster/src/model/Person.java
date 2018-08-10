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

    // getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public Fingerprint getFingerprint() { return fingerprint; }
    @Override
    public String getLocation() { return location; }
    @Override
    public Object getTrace() { return this; }

    // EFFECTS: sets fingerprint to f
    public void setFingerprint(Fingerprint f) { fingerprint = f; }

    @Override
    public void track() {
        System.out.println("This person is located at: " + location);
    }


}
