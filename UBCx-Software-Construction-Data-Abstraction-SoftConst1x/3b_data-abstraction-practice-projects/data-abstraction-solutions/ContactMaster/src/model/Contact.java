package model;

import java.util.ArrayList;
import java.util.List;

import static model.Contact.ContactType.FAMILY;
import static model.Contact.ContactType.WORK;

public class Contact {

    private String name;
    private String number;
    private List<String> callLog;
    private ContactType type;

    public enum ContactType {
        WORK, FRIEND, FAMILY
    }

    public Contact(String name){
        this.name = name;
        callLog = new ArrayList<>();
    }

    // getters
    public String getName() { return name; }
    public String getNumber() { return number; }
    public List<String> getCallLog() { return callLog; }
    public ContactType getContactType() { return type; }

    // MODIFIES: this
    // EFFECTS: sets contact's name to parameter
    public void setName(String name){
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets contact's name to parameter
    public void setNumber(String number){
        this.number = number;
    }

    // MODIFIES: this
    // EFFECTS: sets contact type to one of: WORK, FRIEND, FAMILY
    public void setContactType(ContactType contactType) {
        type = contactType;
    }

    // REQUIRES: a date as String in the format MM/DD/YYYY
    // MODIFIES: this
    // EFFECTS: adds the given date to the callLog
    public void call(String date) {
        callLog.add(date);
    }

    // EFFECTS: returns an automatic response to match ContactType
    public String generateResponse() {
        ContactType ct = getContactType();
        if (ct == WORK) {
            return "I'll get back to you at work.";
        } else if (ct == FAMILY) {
            return "See you at home.";
        } else {
            return "Meet you at the cafe.";
        }
    }


}
