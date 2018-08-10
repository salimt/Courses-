package model;

import java.util.LinkedList;
import java.util.List;

public class MyProfile {

    private String name;
    private int age;
    private String currentLocation;
    private String workPlace;
    private List<MyProfile> friendsList;
    private List<Event> upcomingEvents;

    public MyProfile(String nm, int age, String locn, String work) {
        this.name = nm;
        this.age = age;
        this.currentLocation = locn;
        this.workPlace = work;
        upcomingEvents = new LinkedList<>();
        friendsList = new LinkedList<>();
    }

    // getters
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCurrentLocation() { return currentLocation; }
    public String getWorkPlace() { return workPlace; }
    public int upcomingEventNum() { return upcomingEvents.size(); }
    public List<MyProfile> getFriendsList() { return friendsList; }
    public List<Event> getEventList() { return upcomingEvents; }

    // REQUIRES: f is not already in friendsList
    // MODIFIES: this
    // EFFECTS: consumes a MyProfile object, a friend f, and adds it to the friendsList
    public void addFriend(MyProfile f) { if(!(friendsList.contains(f))){ friendsList.add(f); } }

    // MODIFIES: this
    // EFFECTS: removes a friend with the given name from this. If removal is successful, return true, else
    //          return false
    public boolean unFriend(String nm) {
        for(MyProfile f: friendsList){
            if(f.getName().equals(nm)){
                friendsList.remove(f);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: ev is not in upcomingEvents
    // MODIFIES: this
    // EFFECTS: adds the given event to the list of upcoming events
    public void addEvent(Event ev) { this.upcomingEvents.add(ev); }

    // MODIFIES: this
    // EFFECTS: removes an event with the given name. If removal is successful, return true, else return false
    public boolean removeEvent(String nm) {
        for(Event e: upcomingEvents){
            if(e.getName().equals(nm)){
                upcomingEvents.remove(e);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the number of events that are at the current location of this person
    public int eventNumNearMe() {
        int eventNum = 0;
        for(Event e: upcomingEvents){
            if(e.getLocation().equals(this.currentLocation))
                eventNum++;
        }
        return eventNum;
    }

    // EFFECTS: returns the number of events of the given type et
    public int specificEventType(EventType et) {
        int typeNum = 0;
        for(Event e: upcomingEvents){
            if(e.getEventType().equals(et)){
                typeNum++;
            }
        }
        return typeNum;
    }

    // EFFECTS: prints events of  type et to the console
    //          Hint: is there a method you have already written that you can use?
    public void printEventSchedule(EventType et) {
        // TODO: complete the implementation of this method
    }

    // EFFECTS: prints out the list of friends that this MyProfile has
    public void printFriendNames() { System.out.println(getFriendsList()); }

    // EFFECTS: prints out the names of friends who live at the same location associated with this profile
    public void printFriendsNearMe() {
        // TODO: complete the implementation of this method
    }

    // EFFECTS: produces true if this profile has a friend with the given name,
    //          OR if any of this profile's friends has a friend with the given name
    //          Hint: use recursion!
    public boolean canFindPerson(String name) {
        for (MyProfile f : friendsList) {
            if (f.getName().equals(name)) {
                return true;
            } else {
                if (f.canFindPerson(name)) {
                    return true;
                }
            }
        }
        return false;
    }
}
