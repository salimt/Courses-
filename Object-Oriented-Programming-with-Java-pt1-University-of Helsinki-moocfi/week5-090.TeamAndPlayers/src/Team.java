/**
 * author: salimt
 */

import java.util.*;

public class Team {

    private String name;
    private ArrayList<Player> members;
    private int maxPlayer;

    public Team(String name) {
        this.name = name;
        this.members = new ArrayList<Player>();
        this.maxPlayer = 16;
    }

    //getters
    public String getName() { return name; }

    //adds the player into the team
    public void addPlayer(Player p){
        if(members.size() < maxPlayer){
            members.add(p);
        }
    }

    //print the players' name
    public void printPlayers(){
        for(Player p: members){
            System.out.println(p);
        }
    }

    //sets the maximum number of players that the team can have
    public void setMaxSize(int maxSize){ this.maxPlayer = maxSize; }

    //returns the number of players in the team
    public int size(){ return members.size(); }

    //returns the total number of goals for all the players in the team
    public int goals(){
        int totalGoals = 0;
        for(Player p: members){
            totalGoals += p.getGoals();
        }return totalGoals;
    }

}
