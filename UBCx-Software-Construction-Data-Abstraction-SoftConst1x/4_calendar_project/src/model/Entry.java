/**
 * author: salimt
 */

package model;
public abstract class Entry {

    private Date date;
    private Time time;
    private String label;

    public Entry(Date date, Time time, String label) {
        this.date = date;
        this.time = time;
        this.label = label;
    }

    public Date getDate(){ return this.date; }
    public Time getTime(){ return this.time; }
    public void getLabel(){ System.out.println(this.label); }

    public void isRepeating(){}
    public void getIntervalOfRepetition(){}
    public void setInterval(){}

}
