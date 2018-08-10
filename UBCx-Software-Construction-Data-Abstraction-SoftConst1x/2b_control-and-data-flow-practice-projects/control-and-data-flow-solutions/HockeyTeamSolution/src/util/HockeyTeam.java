package util;

import model.Player;
import java.util.ArrayList;

public class HockeyTeam {

    private ArrayList<Player> players;

    public HockeyTeam() {
        players = new ArrayList<>();
    }


    // REQUIRES: players contains the player with the given number
    // EFFECTS: returns the player with the given number from the team
    public Player retrieve(int num) {
        for (Player p : players) {
            if (p.getNumber() == num) {
                return p;
            }
        } return null;
    }

    // MODIFIES: this
    // EFFECTS: adds the given player into the team, given that the player is not already in the set.
    //          return true if the insertion is successful, else return false.
    public boolean insert(Player player) {
       for (Player p : players) {
           if (p.getNumber() == player.getNumber()) {
               return false;
           }
       }
       players.add(player);
       return true;
    }

    // REQUIRES: players contains the player with the given number
    // EFFECTS: removes the player with the given number from players, return true
    //          if successful, else false
    public boolean remove(int i) {
        for (Player p : players) {
            if (p.getNumber() == i) {
                players.remove(p);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the size of the HockeyTeam
    public int size() {
        return players.size();
    }

    // EFFECTS: returns true if the given player is contained within this set
    public boolean contains(Player p) {
        return players.contains(p);
    }

    // EFFECTS: consumes a set and returns the intersection of this set and the other
    public HockeyTeam intersection(HockeyTeam otherTeam) {
        HockeyTeam intersection = new HockeyTeam();
        for (Player p : players) {
            if (otherTeam.contains(p)) {
                intersection.insert(p);
            }
        }
        return intersection;
    }

    // EFFECTS: Prints the names of all players in the following format:
    //          Name: _______, Number: _______
    public void printRoster() {
        if (players.isEmpty()) {
            System.out.println("There is no one in the roster.");
        } else {
            int i = 0;
            while (i < players.size()) {
                Player temp = players.get(i);
                System.out.println("Name: " + temp.getName() + ", Number: " + temp.getNumber());
                i++;
            }
            System.out.println("That's everyone on the team!");
        }
    }


}