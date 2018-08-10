/**
 * author: salimt
 */

package model;

public class Reminder extends Entry {

    private String note;

    public Reminder(Date date, Time time, String label) {
        super(date, time, label);
        this.note = label;
    }

    public void getNote(){ System.out.println(this.note); }
    public void setNote(String note){ this.note  = note; }

}
