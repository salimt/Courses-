package model;

import static model.Contact.ContactType.FAMILY;
import static model.Contact.ContactType.WORK;
import java.util.ArrayList;
import java.util.List;

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

    //getter
    public String getName() { return name; }
    public String getNumber() { return number; }
    public List<String> getCallLog() { return callLog; }
    public ContactType getContactType() { return type; }

    //setters
    public void setName(String name) { this.name = name; }
    public void setNumber(String number) { this.number = number; }
    public void setContactType(ContactType ContactType) { this.type = type; }

    // REQUIRES: a date as String in the format MM/DD/YYYY
    // MODIFIES: this
    // EFFECTS: adds the given date to the callLog
    public void call(String date){ callLog.add(date); }

    // EFFECTS: returns an automatic response to match ContactType
    public String generateResponse() {
        ContactType c = getContactType();
        if (c == WORK) {
            return "I'll get back to you at work.";
        } else if (c == FAMILY) {
            return "See you at home.";
        } else {
            return "Meet you at the cafe.";
        }
    }

}
