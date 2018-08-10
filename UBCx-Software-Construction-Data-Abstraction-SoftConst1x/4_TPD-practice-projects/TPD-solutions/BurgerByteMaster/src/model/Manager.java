package model;

import java.util.LinkedList;
import java.util.List;

public class Manager extends Employee {

    public static final double MANAGER_WAGE = 9.50;

    private BurgerByte managingBranch;
    private List<Employee> team;

    public Manager(String name, int age) {
        super(name, age);
        team = new LinkedList<>();
    }

    // getters
    public BurgerByte getManagingBranch() { return managingBranch; }
    public List<Employee> getTeam() { return team; }

    // EFFECTS: set this managingBranch to managingBranch
    public void setManagingBranch(BurgerByte managingBranch) {
        this.managingBranch = managingBranch;
    }

    // REQUIRES: emp must not be in already in team and this Manager's restaurant
    // MODIFIES: this
    // EFFECTS: adds given employee to team and this Manager's restaurant
    public void hire(Employee emp) {
        team.add(emp);
        managingBranch.getStaff().add(emp);
        System.out.println("Welcome aboard, " + emp.getName() + "!");
    }


    // REQUIRES: emp must be in this team and and this Manager's restaurant
    // MODIFIES: this
    // EFFECTS: removes given employee from team and this Manager's restaurant
    public void fire(Employee emp) {
        team.remove(emp);
        managingBranch.removeStaff(emp);
        System.out.println("Sorry to let you go, " + emp.getName() + ".");
    }

    // EFFECTS: opens this Manager's restaurant and logs hours worked
    @Override
    public void startWork(double hours) {
        managingBranch.openRestaurant();
        atWork = true;
        logHoursWorked(hours);
        System.out.println("We are open for the day!");
    }

    // EFFECTS: closes this Manager's restaurant,
    @Override
    public void leaveWork() {
        managingBranch.closeRestaurant();
        atWork = false;
        System.out.println("We are closed for the day!");
    }

    // EFFECTS: compute this Manager's wage for the day
    @Override
    public double computeWage() {
       return (hoursWorked * (MANAGER_WAGE + BASE_WAGE));
    }


}
