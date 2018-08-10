package model;

public abstract class Bird implements Flyer {

    @Override
    public abstract void fly();

    @Override
    public void takeOff() {
        System.out.println("Flap flap flap jump");
    }

    @Override
    public void land() {
        System.out.println("Flap hop run run");
    }


}