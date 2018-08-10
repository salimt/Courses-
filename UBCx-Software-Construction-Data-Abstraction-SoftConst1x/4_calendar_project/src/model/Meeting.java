/**
 * author: salimt
 */

package model;
import java.util.*;

public class Meeting extends Entry {

    private ArrayList<String> attendees;

    public Meeting(Date date, Time time, String label) {
        super(date, time, label);
        this.attendees = new ArrayList <>();
    }

    public void getAttendees() {
        for (String a : attendees) { System.out.println(a); }
    }

    public void addAttendees(String name){ this.attendees.add(name); }

    public void removeAttendees(String a){
        for(String attendee: attendees){
            if(attendee.equals(a)){ this.attendees.remove(a); }
        }
    }

    public void sendInvites(){
        for(String attendee: attendees){
            System.out.println("Inviting: " + attendee);
        }
    }
}
