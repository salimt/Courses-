
public class MyDate {

    private int day;
    private int month;
    private int year;

    public MyDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString() {
        return this.day + "." + this.month + "." + this.year;
    }

    public boolean earlier(MyDate compared) {
        if (this.year < compared.year) {
            return true;
        }

        if (this.year == compared.year && this.month < compared.month) {
            return true;
        }

        if (this.year == compared.year && this.month == compared.month
                && this.day < compared.day) {
            return true;
        }

        return false;
    }

    //REQUIRES: days are no more than 30
    //MODIFIES: this
    //EFFECTS: advances the date by one
    public void advance() {

        if(this.day == 30) {
            this.day = 1;
            if(this.month == 12) {
                this.month = 1;
                this.year++;
            }else{ this.month++; }
        }else{ this.day++; }

    }

    //REQUIRES: days are no more than 30
    //MODIFIES: this
    //EFFECTS: advance the day by the number given as parameter
    public void advance(int numberOfDays){
        for(int i=0; i<numberOfDays; i++){
            advance();
        }
    }


    //returns a new MyDate-object that has the date which equals the
    //date of the object for which the method was called advance by the parameter of the method days
    public MyDate afterNumberOfDays(int days){

        MyDate newMyDate = new MyDate(day,month,year);
        newMyDate.advance(days);

        return newMyDate;
    }

}
