package model;

public abstract class Vegetable implements Growable {

    private String name;
    private String instructions;
    private VegType type;

    public Vegetable(String name, VegType type) {
        setName(name);
        setType(type);
        setInstructions("");
    }

    // getters
    public String getName() { return name; }
    @Override
    public String getInstructions() { return instructions; }
    public VegType getType() { return type; }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(VegType type) {
        this.type = type;
    }

    protected void setInstructions(String instructions) {
        this.instructions = instructions;
    }


}