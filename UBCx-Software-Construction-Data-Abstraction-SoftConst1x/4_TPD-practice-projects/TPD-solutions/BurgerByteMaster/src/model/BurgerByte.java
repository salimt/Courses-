package model;

import java.util.LinkedList;
import java.util.List;

public class BurgerByte {

    private String location;
    private Manager manager;
    private List<Employee> staff;
    private boolean isOpen;

    public BurgerByte(String locn, Manager manager) {
        location = locn;
        this.manager = manager;
        staff = new LinkedList<>();
        isOpen = false;
    }

    // getters
    public String getLocation() { return location; }
    public Employee getManager() { return manager; }
    public List<Employee> getStaff() { return staff; }
    public boolean isOpen() { return isOpen; }

    // EFFECTS: "opens" this restaurant, i.e. set isOpen to true
    public void openRestaurant() {
        isOpen = true;
    }

    // EFFECTS: "closes" this restaurant, i.e. set isOpen to false, and sends all employees home
    public void closeRestaurant() {
        isOpen = false;
        for (Employee emp : staff) {
            emp.leaveWork();
        }
    }

    // REQUIRES: emp must not be in this staff or this BurgerByte's Manager's team
    // MODIFIES: this
    // EFFECTS: adds the given Employee to this staff and to this Manager's team
    public void addStaff(Employee emp) {
        staff.add(emp);
        manager.getTeam().add(emp);
    }

    // REQUIRES: emp must be present in staff and this BurgerByte's Manager's team
    // MODIFIES: this
    // EFFECTS: removes the given Employee from this staff and this Manager's team
    public void removeStaff(Employee emp) {
        staff.remove(emp);
        manager.getTeam().remove(emp);
    }

    // EFFECTS: computes wages for all employees, prints them out in this format
    //          Name: ______, Salary: _______ for each employee
    public void computeStaffWages() {
        for (Employee emp : staff) {
            System.out.println("Name: " + emp.getName() + ", Salary: " + emp.computeWage());
        }
    }


}
