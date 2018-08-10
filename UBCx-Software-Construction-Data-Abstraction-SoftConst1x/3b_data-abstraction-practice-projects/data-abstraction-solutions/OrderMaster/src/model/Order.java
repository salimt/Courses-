package model;

/**
 * Created by mickey on 2017-04-27.
 */

public class Order {

    private int ticketNum;
    private char combo;
    private double price;
    private String custName;
    private boolean completed;
    private String instructions;

    // MODIFIES: this
    // EFFECTS: creates an order object with number, combo (order), and price.
    //          When an order is placed, it is not yet completed.
    public Order(int ticketNum, char combo, String custName){
        this.ticketNum = ticketNum;
        this.combo = combo;
        this.custName = custName;
        calculatePrice();
        completed = false;
    }

    // getters
    public char getCombo() { return combo; }
    public int getTicketNum() { return ticketNum; }
    public double getPrice() { return price; }
    public String getCustName() { return custName; }
    public boolean isCompleted() { return completed; }
    public String getInstructions() { return instructions; }

    // MODIFIES: this
    // EFFECTS: sets completed to true
    public void completed(){
        completed = true;
    }

    // MODIFIES: this
    // EFFECTS: updates instructions to the new String parameter
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    // EFFECTS: returns a string with customer name and ticket number, combo ordered and price
    public String customerReceipt(){
        return getCustName() + " - " + getTicketNum() + " - Combo " + getCombo() + ": " + comboFoodType()
                + " - $" + getPrice();
    }

    // EFFECTS: returns a string with ticket number, combo and special instructions
    public String cookingInstructions(){
        return getTicketNum() + " COMBO " + getCombo() + " INSTRUCTIONS: " + getInstructions();
    }

    // EFFECTS: returns this Order's combo letter
    public String comboFoodType(){
        switch(getCombo()){
            case 'A': return "Salad";
            case 'B': return "Pasta";
            case 'C': return "Pizza";
            case 'D': return "Tacos";
            case 'E': return "Sandwich";
            default: return "Soup";
        }
    }

    // MODIFIES: this
    // EFFECTS: sets price of order based on combo letter
    private void calculatePrice() {
        switch (getCombo()) {
            case 'A':
                price = 10.0;
                break;
            case 'B':
                price = 9.5;
                break;
            case 'C':
                price = 11.0;
                break;
            case 'D':
                price = 7.5;
                break;
            case 'E':
                price = 9.0;
                break;
            default :
                price = 10.0;
        }
    }


}
