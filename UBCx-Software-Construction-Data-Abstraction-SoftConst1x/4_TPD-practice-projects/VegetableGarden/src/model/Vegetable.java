/**
 * author: salimt
 */

package model;
import java.util.*;

public abstract class Vegetable implements Growable {

    private String name;
    private VegType type;
    private List<Vegetable> vegetables;
    private String instructions;

    public Vegetable(String name, VegType type) {
        this.name = name;
        this.type = type;
        vegetables = new LinkedList<>();
    }

    //getters
    public String getName() { return name; }
    public VegType getType() { return type; }

    @Override
    public String getInstructions() { return instructions; }

    //setters
    public void setName(String name){ this.name = name; }
    protected void setInstructions(String instructions){ this.instructions = instructions; }
    public void setType(VegType type) { this.type = type; }
}
