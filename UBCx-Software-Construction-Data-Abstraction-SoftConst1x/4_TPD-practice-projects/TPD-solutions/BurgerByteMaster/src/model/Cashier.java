package model;

public class Cashier extends Employee {

    public static final double CASHIER_WAGE = 6.50;

    private BurgerByte workBranch;
    private boolean isRegisterOpen;

    public Cashier(String name, int age, BurgerByte workBranch) {
        super(name, age);
        this.workBranch = workBranch;
        workBranch.addStaff(this);
        isRegisterOpen = false;
    }

    // getters
    public BurgerByte getWorkPlace() { return workBranch; }
    public boolean isRegisterOpen() { return isRegisterOpen; }

    @Override
    public void startWork(double hours) {
        isRegisterOpen = true;
        atWork = true;
        logHoursWorked(hours);
        System.out.println("Register open!");
    }

    // EFFECTS: closes the register, and resets the day's revenue for the next day
    @Override
    public void leaveWork() {
        isRegisterOpen = false;
        atWork = false;
        System.out.println("Register closed, can't take any more orders.");
    }

    // EFFECTS: computes wages for the day
    @Override
    public double computeWage() {
        return (hoursWorked * (CASHIER_WAGE + BASE_WAGE));
    }


}