package model;

public class Cashier extends Employee {

    public static final double CASHIER_WAGE = 6.50;

    private BurgerByte workBranch;
    private boolean isRegisterOpen;

    public Cashier(String name, int age, BurgerByte workBranch) {
        super(name, age);
        this.workBranch = workBranch;
        isRegisterOpen = false;
        workBranch.addCashier(this);
    }

    // getters
    public BurgerByte getWorkPlace() { return workBranch; }
    public boolean isRegisterOpen() { return isRegisterOpen; }

    // EFFECTS: returns the total amount of wages this Cashier made
    public double computeWage() {
        return (hoursWorked * (CASHIER_WAGE + BASE_WAGE));
    }

    // EFFECTS: sets isRegisterOpen to true, and updates atWork to reflect that this Cashier is
    //          now at work
    public void startWork(double hours) {
        isRegisterOpen = true;
        atWork = true;
        logHoursWorked(hours);
        System.out.println("Register open!");
    }

    // EFFECTS: sets isRegisterOpen to false and updates atWork to reflect that this Cashier is
    //          no longer at work
    public void leaveWork() {
        isRegisterOpen = false;
        atWork = false;
        System.out.println("Register closed, can't take any more orders.");

    }


}