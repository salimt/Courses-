package model;

import java.util.ArrayList;
import java.util.List;

public class CellPhone implements Traceable {

    private List<Call> calls;
    private Person owner;
    private String location;

    public CellPhone(Person owner, String location) {
        calls = new ArrayList<>();
        this.owner = owner;
        this.location = location;
    }

    // getters
    public List<Call> getCalls() { return calls; }
    public Person getOwner() { return owner; }
    @Override
    public String getLocation() { return location; }
    @Override
    public Object getTrace() { return this; }

    // EFFECTS: adds a given call to calls
    public void addCall(Call c) {
        calls.add(c);
    }

    @Override
    public void track() {
        System.out.println("This phone is located at: " + location);
    }


}
