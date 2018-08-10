package model;

public class FerryCard {

    private Passenger owner;
    private int balance;

    public FerryCard() {
        balance = 0;
    }

    // getters
    public int getBalance() { return balance; }
    public Passenger getOwner() { return owner; }

    // setters
    public void setOwner(Passenger owner) {   this.owner = owner; }
    public void setBalance(int balance) { this.balance = balance; }

    // REQUIRES: balanceAdded > 0
    // MODIFIES: this
    // EFFECTS: adds balanceAdded to balance available on this card
    public void topUp(int balanceAdded) {
        balance = balanceAdded;
    }

    // MODIFIES: this
    // EFFECTS: if this does not have at least ticketPrice available on balance,
    // returns false; otherwise creates a new ticket, adds the ticket to ticket
    // and subtracts ticketPrice from balance.
    public boolean buyTicket(Ferry ferry) {

        int ticketPrice = ferry.getTicketPrice();

        if(balance < ticketPrice) {
            return false;
        }

        Ticket ticket = new Ticket(ferry, owner);
        ferry.addTicket(ticket);
        return true;
    }


}