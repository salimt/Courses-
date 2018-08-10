package model;

public class Airplane implements Flyer, Cafe {

    // NOTE: this project accompanies the Abstract Classes video

    @Override
    public void serveDrinks() {
        System.out.println("Serving airplane drinks");
    }

    @Override
    public void serveSnacks() {
        System.out.println("Serving airplane snacks");
    }

    @Override
    public void takeOff() {
        System.out.println("Taking off!!");
    }

    @Override
    public void fly() {
        System.out.println("Flying along -- no turbulance!");
    }

    @Override
    public void land() {
        System.out.println("Fasten your seatbelts - landing!");
    }
}
