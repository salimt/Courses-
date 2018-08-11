/**
 * author: salimt
 */

public class Player {

    private String name;
    private int goals;

    public Player(String name, int goals) {
        this.name = name;
        this.goals = goals;
    }

    public Player(String name){
        this.name = name;
        this.goals = 0;
    }

    //getters
    public String getName() { return name; }
    public Integer getGoals() { return goals; }

    @Override
    public String toString() {
        return this.name +","+ " goals " + this.goals;
    }

    //returns the  number of goals for the player
    public int goals() {
        return this.goals;
    }

}
