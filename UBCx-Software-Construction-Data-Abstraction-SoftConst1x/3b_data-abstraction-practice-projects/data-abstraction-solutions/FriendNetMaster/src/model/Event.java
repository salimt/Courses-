package model;

public class Event {

    private String name;
    private String date;
    private String location;
    private EventType type;

    public Event(String name, String date, String locn, EventType type) {
        this.name = name;
        this.date = date;
        location = locn;
        this.type = type;
    }

    // getters
    public String getName() { return name; }
    public String getDate() { return date; }
    public EventType getEventType() { return type; }
    public String getLocation() { return location; }


}
