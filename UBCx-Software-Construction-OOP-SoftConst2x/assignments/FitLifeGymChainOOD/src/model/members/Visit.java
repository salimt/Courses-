package model.members;


public class Visit {
    private int year;
    private int month;
    private int day;

    public Visit(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getDate(){
        String dayString = prettyFormat(day);
        String monthString = prettyFormat(month);
        return dayString+"-"+monthString+"-"+year;
    }

    private String prettyFormat(int i){
        if(i < 10){
            return "" + "0" + i;
        } else {
            return "" + i;
        }
    }
}
