package model.gym;

import model.members.Member;

import java.util.ArrayList;
import java.util.List;

public class FitnessClass {

    private static final int maxParticipants = 20;

    private String className;
    private String dayAndTime;
    private List<Member> registered;

    public FitnessClass(String className, String dayAndTime){
        this.className = className;
        this.dayAndTime = dayAndTime;
        registered = new ArrayList<>();
    }

    //getters
    public String getClassName() {
        return className;
    }

    public String getDayAndTime() {
        return dayAndTime;
    }

    public List<Member> getMembersRegistered() {
        return registered;
    }

    public int getMaxParticipants() { return maxParticipants; }

    //REQUIRES: className != null
    //MODIFIES: this
    //EFFECTS: sets className to given name
    public void setClassName(String className) {
        this.className = className;
    }

    //REQUIRES: dayAndTime != null
    //MODIFIES: this
    //EFFECTS: sets dayAndTime to given day and time
    public void setDayAndTime(String dayAndTime) {
        this.dayAndTime = dayAndTime;
    }

    //REQUIRES: m != null
    //MODIFIES: this
    //EFFECTS: registers given member for this class
    public void registerMember(Member m){
        if (registered.size() < maxParticipants) {
            registered.add(m);
        } else {
            System.out.println("Sorry, this class is already full.");
        }
    }

    //REQUIRES: m != null
    //MODIFIES: this
    //EFFECTS: removes this member from the list of registered members
    public void removeMember(Member m){
        registered.remove(m);
    }
}
