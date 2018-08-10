package model;

public class FryCook extends Employee {

    public static final double FRYCOOK_WAGE = 5.50;

    private BurgerByte workBranch;
    private boolean isGrillReady;

    public FryCook(String name, int age, BurgerByte workBranch) {
        super(name, age);
        this.workBranch = workBranch;
        workBranch.addStaff(this);
        isGrillReady = false;
    }

    // getters
    public BurgerByte getWorkPlace() { return workBranch; }
    public boolean isGrillReady() { return isGrillReady; }

    // EFFECTS: the grill should be ready to go, and hours should be logged
    @Override
    public void startWork(double hours) {
        isGrillReady = true;
        atWork = true;
        logHoursWorked(hours);
        System.out.println("Grill is ready to cook with!");
    }

    // EFFECTS: close the grill for the day
    @Override
    public void leaveWork() {
        isGrillReady = false;
        atWork = false;
        System.out.println("Grill is closed for the day.");
    }

    // EFFECTS: computes wages for the day
    @Override
    public double computeWage() {
        return (hoursWorked * (FRYCOOK_WAGE + BASE_WAGE));
    }


}

