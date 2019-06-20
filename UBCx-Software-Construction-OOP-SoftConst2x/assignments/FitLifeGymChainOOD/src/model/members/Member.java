package model.members;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static model.members.MembershipLevel.BASIC;
import static model.members.MembershipLevel.CLASSES;
import static model.members.MembershipLevel.DELUXE;

public class Member {

    private String name;
    private List<Visit> visits;
    private MembershipLevel level;

    public Member(String name){
        this.name = name;
        level = BASIC;
        visits = new ArrayList<>();
    }

    //EFFECTS: returns name of member
    public String getName(){
        return name;
    }

    //EFFECTS: returns this member's membership level
    public MembershipLevel getLevel(){
        return level;
    }

    //EFFECTS: returns this member's visit history
    public List<Visit> getVisitHistory(){
        return visits;
    }

    //MODIFIES: this
    //EFFECTS: sets this member's name to given name
    public void setName(String name){
        this.name = name;
    }

    //MODIFIES: this
    //EFFECTS: returns this member's membership level
    public void setMembershipLevel(MembershipLevel level){
        this.level = level;
    }

    //MODIFIES: this
    //EFFECTS: adds current date to member's visit history
    public void checkIn(){
        Calendar current = Calendar.getInstance();
        int day = current.get(Calendar.DAY_OF_MONTH);
        int month = current.get(Calendar.MONTH);
        month++;
        int year = current.get(Calendar.YEAR);

        Visit v = new Visit(day, month, year);
        visits.add(v);
    }

    //MODIFIES: this
    //EFFECTS: upgrades this member's membership level, if possible
    public void upgrade(){
        if(level == BASIC){
            setMembershipLevel(CLASSES);
        } else if (level == CLASSES){
            setMembershipLevel(DELUXE);
        }
    }

    //EFFECTS: returns true if this member can reserve classes
    public boolean canReserveClasses(){
        if(level != BASIC){
            return true;
        } else {
            return false;
        }
    }

}
