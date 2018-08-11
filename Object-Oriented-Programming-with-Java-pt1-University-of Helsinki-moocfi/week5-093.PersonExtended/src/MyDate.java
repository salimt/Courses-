
public class MyDate {

    private int day;
    private int month;
    private int year;

    public MyDate(int pv, int kk, int vv) {
        this.day = pv;
        this.month = kk;
        this.year = vv;
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

    //REQUIRES: month as 30 days, year as 364 days
    public int differenceInYears(MyDate comparedDate){

        int totalDaysNow = this.day+(this.month*30)+(this.year*364);
        int totalDaysCompared = comparedDate.day+(comparedDate.month*30)+(comparedDate.year*364);

        if(Math.abs(totalDaysCompared - totalDaysNow) >= Math.abs(this.year - comparedDate.year)*364){
            return Math.abs(this.year - comparedDate.year);
        }
        return Math.abs(this.year - comparedDate.year)-1;

    }

}
