public class Clock {
    private BoundedCounter hours;
    private BoundedCounter minutes;
    private BoundedCounter seconds;
   
    public Clock(int hoursAtBeginning, int minutesAtBeginning, int secondsAtBeginning) {
        // the counters that represent hours, minutes and seconds are created and set to have the correct initial values
        this.hours = new BoundedCounter(24, hoursAtBeginning);
        this.minutes = new BoundedCounter(60, minutesAtBeginning);
        this.seconds = new BoundedCounter(60, secondsAtBeginning);

    }

    //second ticks one sec
    public void tick() {
        this.seconds.next();
        if(this.seconds.getValue()==0){
            this.minutes.next();
            if(this.minutes.getValue()==0){
                this.hours.next();
            }
        }
    }
    
    public String toString() { return this.hours + ":" + this.minutes + ":" + this.seconds; }
}
