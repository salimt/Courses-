package model;

import model.gym.Gym;
import model.members.Member;
import model.members.MembershipLevel;

import java.util.ArrayList;
import java.util.List;

public class GymChain {

    private List<Gym> gyms;
    private List<Member> members;

    public GymChain(){
        gyms = new ArrayList<>();
        members = new ArrayList<>();
    }

    // getters
    public List<Gym> getGyms() { return gyms; }
    public List<Member> getMembers() { return members; }

    // REQUIRES: gym != null
    // MODIFIES: this
    // EFFECTS: adds gym to this chain
    public void addGym(Gym gym) {
        gyms.add(gym);
    }

    // REQUIRES: gym != null
    // MODIFIES: this
    // EFFECTS: removes gym from this chain
    public void removeGym(Gym gym) {
        gyms.remove(gym);
    }

    // REQUIRES: m != null
    // MODIFIES: this
    // EFFECTS: adds member m to this gym
    public void signUpMember(Member m){
        members.add(m);
    }

    // REQUIRES: m != null
    // MODIFIES: this
    // EFFECTS: removes member m from this gym
    public void removeMember(Member m){
        members.remove(m);
    }

    // REQUIRES: gym != null
    // EFFECTS: returns the type of this gym as "Deluxe" or "Standard"
    public String getGymType(Gym gym){
        if(gym.getClass().getSimpleName().equals("DeluxeGym")){
            return "Deluxe";
        } else {
            return "Standard";
        }
    }

    // REQUIRES: memberName, locnName already exist in this chain
    // EFFECTS: returns true if member's membership level qualifies for this gym
    public boolean canMemberEnterGym(String memberName, String locnName){
        Member m = lookupMemberByName(memberName);
        Gym gym = lookupGymByName(locnName);

        boolean canEnter;

        if (m.getLevel() == MembershipLevel.DELUXE){
            canEnter = true;
        } else if (getGymType(gym).equals("Deluxe")){
            canEnter = false;
        } else {
            canEnter = true;
        }
        return canEnter;
    }

    // REQUIRES: memberName is the name of an existing member
    // EFFECTS: returns member object that has name memberName
    public Member lookupMemberByName(String memberName){
        Member mem = null;
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(memberName))
                mem = m;
        }
        return mem;
    }

    // REQUIRES: locnName is the name of an existing gym
    // EFFECTS: returns gym that has name locnName
    public Gym lookupGymByName(String locnName){
        Gym gym = null;
        for (Gym g : gyms) {
            if (g.getLocationName().toLowerCase().equals(locnName.toLowerCase()))
                gym = g;
        }
        return gym;
    }


}