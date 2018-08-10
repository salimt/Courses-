package model;

public class Call implements Traceable {

    private CellPhone outgoingCaller;
    private String callLocation;

    public Call(CellPhone outgoingCaller, String location) {
        this.outgoingCaller = outgoingCaller;
        callLocation = location;
    }

    // getters
    public CellPhone getOutgoingCaller() { return outgoingCaller; }
    @Override
    public String getLocation() { return callLocation; }
    @Override
    public Call getTrace() { return this; }

    @Override
    public void track() {
        System.out.println("This call is coming from: " + callLocation);
    }


}
