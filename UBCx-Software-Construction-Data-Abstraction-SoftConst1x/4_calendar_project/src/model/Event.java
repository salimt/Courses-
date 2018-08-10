/**
 * author: salimt
 */

package model;

public class Event extends Entry {

    private String reminder;

    public Event(Date date, Time time, String event) {
        super(date, time, event);
        this.reminder = event;
    }

    public void getReminder(){ System.out.println(this.reminder); }
    public void setReminder(String r){ this.reminder = r; }

}
