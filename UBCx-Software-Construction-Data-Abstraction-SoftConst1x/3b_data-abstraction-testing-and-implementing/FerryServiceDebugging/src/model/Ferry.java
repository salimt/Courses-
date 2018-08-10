package model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Ferry {

    private Set<Ticket> ticketSet;
    private String destination;
    private Date sailingDate;
    private int ticketPrice;

    public Ferry(String destination, Date sailingDate, int ticketPrice) {
        this.destination = destination;
        this.sailingDate = sailingDate;
        this.ticketPrice = 0;
        ticketSet = new HashSet<>();
    }

    // getters
    public Set<Ticket> getTicketSet() { return ticketSet; }
    public String getDestination() { return destination; }
    public Date getSailingDate() { return sailingDate; }
    public int getTicketPrice() { return ticketPrice; }

    // MODIFIES: this
    // EFFECTS: sets ticketSet to parameter
    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    // MODIFIES: this
    // EFFECTS: sets destination to parameter
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: sets sailingDate to parameter
    public void setSailingDate(Date sailingDate) {
        this.sailingDate = sailingDate;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // MODIFIES: this
    // EFFECTS: adds a ticket to the set of tickets
    public void addTicket(Ticket ticket) {
        ticketSet.add(ticket);
    }


}