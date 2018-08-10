package model;

public class Fingerprint implements Traceable {

    private Person owner;
    private String precinct;

    public Fingerprint(Person owner, String precinct) {
        this.owner = owner;
        this.precinct = precinct;
        owner.setFingerprint(this);
    }

    // getters
    public Person getOwner() { return owner; }
    @Override
    public String getLocation() { return precinct; }
    @Override
    public Object getTrace() { return this; }

    @Override
    public void track() {
        System.out.println("This fingerprint was collected at: " + precinct);
    }


}
