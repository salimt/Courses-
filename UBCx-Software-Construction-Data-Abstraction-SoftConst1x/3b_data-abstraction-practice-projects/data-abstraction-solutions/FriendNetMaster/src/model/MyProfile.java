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
        name = nm;
        this.age = age;
        currentLocation = locn;
        workPlace = work;
        friendsList = new LinkedList<>();
        upcomingEvents = new LinkedList<>();
    }

    // getters
    public String getName() { return this.name; }
    public int getAge() { return this.age; }
    public String getCurrentLocation() { return this.currentLocation; }
    public String getWorkPlace() { return this.workPlace; }
    public int upcomingEventNum() { return upcomingEvents.size(); }
    public List<MyProfile> getFriendsList() { return this.friendsList; }
    public List<Event> getEventList() { return this.upcomingEvents; }

    // REQUIRES: f is not already in friendsList
    // MODIFIES: this
    // EFFECTS: consumes a friend f (represented as a MyProfile) object, and adds it to the friendList
    public void addFriend(MyProfile f) {
        this.friendsList.add(f);
    }

    // MODIFIES: this
    // EFFECTS: removes a friend with the given name from this. If removal is successful, return true - else
    //          return false
    public boolean unFriend(String nm) {
        for (MyProfile f : friendsList) {
            if (f.getName().equals(nm)) {
                friendsList.remove(f);
                return true;
            }
        }
        return false;
    }

    // REQUIRES: ev is not in upcomingEvents
    // MODIFIES: this
    // EFFECTS: adds the given event to the list of upcoming events
    public void addEvent(Event ev) {
        this.upcomingEvents.add(ev);
    }

    // MODIFIES: this
    // EFFECTS: removes an event with the given name. If removal is successful, return true - else return false
    public boolean removeEvent(String nm) {
        for (Event e : upcomingEvents) {
            if (e.getName().equals(nm)) {
                upcomingEvents.remove(e);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the number of events that are at the current location of this person
    public int eventNumNearMe() {
        int count = 0;
        for (Event e : upcomingEvents) {
            if (e.getLocation().equals(this.currentLocation)) {
                count++;
            }
        }
        return count;
    }

    // EFFECTS: returns the number of events of the given type (et)
    public int specificEventType(EventType et) {
        int count = 0;
        for (Event e : upcomingEvents) {
            if (e.getEventType().equals(et)) {
                count++;
            }
        }
        return count;
    }

    // EFFECTS: prints events of a given type (et) out on the console. Hint: is there a method you have already
    //          written that you can use in the implementation of this method?
    public void printEventSchedule(EventType et) {
        for (Event e : upcomingEvents) {
            if (e.getEventType().equals(et)) {
                System.out.println(e.getName() + " @ " + e.getLocation() );
            }
        }
    }

    // EFFECTS: prints out the list of friends that this MyProfile has
    public void printFriendNames() {
        System.out.println("Here are a list of my friends: ");
        for (MyProfile f : friendsList) {
            System.out.println(f.getName());
        }
    }

    // EFFECTS: prints out the names of friends who live at the same location of the person associated with
    //          this profile
    public void printFriendsNearMe() {
        System.out.println("These are the friends who live near me: ");
        for (MyProfile f : friendsList) {
            if (f.getCurrentLocation().equals(this.currentLocation)) {
                System.out.println(f.getName());
            }
        }
    }

    // EFFECTS: produces true if this profile has a friend with the given name OR if this profile's friends has
    //          the friend with the given name. Hint: use recursion!
    public boolean canFindPerson(String name) {
        if (this.friendsList.isEmpty()) return false;

        for (MyProfile p : friendsList) {
            if (p.getName().equals(name)) {
                return true;
            }
        }

        for (MyProfile p : friendsList) {
            if (p.canFindPerson(name)) {
                return true;
            }
        }
        return false;
    }


}
