package model;

import java.util.ArrayList;
import java.util.List;

public class Band {

    private String name;
    private double totalMoney;
    private double currentMoney;
    private List<String> members;
    private int numShowsPlayed;

    public Band(String name) {
        this.name = name;
        totalMoney = 0;
        currentMoney = 0;
        members = new ArrayList<>();
        numShowsPlayed = 0;
    }

    // getters
    public String getName() { return name; }
    public double getTotalMoney() { return totalMoney; }
    public double getCurrentMoney() { return currentMoney; }
    public List<String> getMembers() { return members; }
    public int getNumShowsPlayed() { return numShowsPlayed; }

    // MODIFIES: this
    // EFFECTS: adds a member with the given name to the band
    public void addMember(String name) {
        members.add(name);
    }

    // MODIFIES: this
    // EFFECTS: given an amount the band will be played for the show, update
    //          the current money and total money by the amount, and add one to
    //          the number of shows played.
    public void playGig(double amt) {
        totalMoney += amt;
        currentMoney += amt;
        numShowsPlayed += 1;
    }

    // MODIFIES: this
    // EFFECTS: pays the given amount to each band member, the total paid
    //          is subtracted from the current money. If there is not enough
    //          money, nothing happens
    public void payMembers(double amt) {
        int numMembers;
        numMembers = members.size();
        if (currentMoney >= amt*numMembers) {
            currentMoney -= amt*numMembers;
        }
    }

    // REQUIRES: numShowsPlayed > 0
    // MODIFIES: nothing
    // EFFECTS: computes the average amount the band is payed per show
    public double averagePerShow() {
        return totalMoney/numShowsPlayed;
    }


}
