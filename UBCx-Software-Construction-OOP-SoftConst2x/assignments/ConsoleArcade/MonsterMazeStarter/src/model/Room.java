/**
 * @author: salimt
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Room extends Choice  {

    private int id;
    private List<Choice> nextChoices;

    public Room(int id) {
        super("Enter Room " + id + ".");
        this.id = id;
        nextChoices = new ArrayList <>();

    }

    //EFFECTS: add a choice into list
    public void addChoice(Choice c){
        nextChoices.add(c);
    }

    //EFFECTS: return choices from the list
    public Choice getChoice(int idx){
        return nextChoices.get(idx-1);
    }

    //EFFECTS: prints all possible next choices
    public void printOutcome() {
        System.out.println("You are now in Room " + id + ".\n");
        System.out.println("You have the following options: ");

        for (int i=1; i<nextChoices.size()+1; i++) {
            System.out.print("\tOption " + i + ": ");
            nextChoices.get(i-1).printOptionMessage();
        }

    }


}
