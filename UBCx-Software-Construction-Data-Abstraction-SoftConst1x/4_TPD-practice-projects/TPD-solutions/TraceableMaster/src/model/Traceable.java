package model;

public interface Traceable {

    String getLocation();

    Object getTrace();      // Note: I've never had a method which returns an object

    void track();

}
