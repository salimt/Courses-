package model;

public class Ticket {

    private Ferry ferry;
    private Passenger passenger;

    public Ticket(Ferry ferry, Passenger passenger) {
        this.ferry = ferry;
        this.passenger = passenger;
    }

    // getters
    public Ferry getFerry() { return ferry; }
    public Passenger getPassenger() { return passenger; }

    //MODIFIES: this
    //EFFECTS: sets ferry to parameter
    public void setFerry(Ferry ferry) {
        this.ferry = ferry;
    }

    //MODIFIES: this
    //EFFECTS: sets passenger to parameter
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }


}
