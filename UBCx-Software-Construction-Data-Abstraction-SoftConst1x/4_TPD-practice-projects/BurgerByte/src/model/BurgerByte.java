package model;

import java.util.LinkedList;
import java.util.List;

public class BurgerByte {

    private String location;
    private Manager manager;
    private List<Cashier> cashiers;
    private List<FryCook> fryCooks;
    private boolean isOpen;

    public BurgerByte(String locn, Manager manager) {
        location = locn;
        this.manager = manager;
        cashiers = new LinkedList<>();
        fryCooks = new LinkedList<>();
    }

    // getters
    public String getLocation() { return location; }
    public Manager getManager() { return manager; }
    public List<Cashier> getCashiers() { return cashiers; }
    public List<FryCook> getFryCooks() { return fryCooks; }
    public boolean isOpen() { return isOpen; }

    // EFFECTS: "opens" this restaurant, i.e. set isOpen to true
    public void openRestaurant() {
        isOpen = true;
    }

    // EFFECTS: sets the isOpen field to false, and sends all employees home (off work)
    public void closeRestaurant() {
        isOpen = false;
        for (Cashier c : cashiers) {
            c.leaveWork();
        }
        for (FryCook fc : fryCooks) {
            fc.leaveWork();
        }
        manager.leaveWork();
    }

    // REQUIRES: c must not be in this cashiers or this BurgerByte's Manager's cashiers
    // MODIFIES: this
    // EFFECTS: adds the given cashier to this cashiers
    public void addCashier(Cashier c) {
        cashiers.add(c);
    }

    // REQUIRES: c must be in this cashiers and this BurgerByte's Manager's cashiers
    // MODIFIES: this
    // EFFECTS: removes the given cashier from this cashiers
    public void removeCashier(Cashier c) {
        cashiers.remove(c);
    }

    // REQUIRES: fc must not be in this frycooks or this BurgerByte's Manager's frycooks
    // MODIFIES: this
    // EFFECTS: adds the given cashier to this cashiers
    public void addFryCook(FryCook fc) {
        fryCooks.add(fc);
    }

    // REQUIRES: fc must be in this frycooks and this BurgerByte's Manager's frycooks
    // MODIFIES: this
    // EFFECTS: removes the given FryCook from this frycooks
    public void removeFrycook(FryCook fc) {
        fryCooks.remove(fc);
    }

    public List<Employee> getStaff(){
        List<Employee> staff = new LinkedList<>();
        staff.addAll(cashiers);
        staff.addAll(fryCooks);
        return staff;
    }

    public void removeStaff(FryCook f){ fryCooks.remove(f); }

    public void removeStaff(Cashier c){ cashiers.remove(c); }

    public void addStaff(FryCook f){ addFryCook(f); }

    public void addStaff(Cashier c){ addCashier(c); }

    // EFFECTS: computes wages for all employees, prints them out in this format
    //          Name: ______, Salary: _______ for each employee
    public void computeStaffWages() {
        for (Cashier c : cashiers) {
            System.out.println("Name: " + c.getName() +", Salary: " + c.computeWage());
        }
        for (FryCook fc : fryCooks) {
            System.out.println("Name: " + fc.getName() +", Salary: " + fc.computeWage());
        }
        System.out.println("Name: " + manager.getName() +", Salary: " + manager.computeWage());
    }


}