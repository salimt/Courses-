
public class Apartment {

    private int rooms;
    private int squareMeters;
    private int pricePerSquareMeter;

    public Apartment(int rooms, int squareMeters, int pricePerSquareMeter) {
        this.rooms = rooms;
        this.squareMeters = squareMeters;
        this.pricePerSquareMeter = pricePerSquareMeter;
    }

    //returns true if the Apartment object for which
    //the method is called (this) is larger than the apartment object given as parameter (otherApartment)
    public boolean larger(Apartment otherApartment){
        if(this.squareMeters > otherApartment.squareMeters){
            return true;
        }return false;
    }

    //returns the absolute value of the price difference of the Apartment object for which
    //the method is called (this) and the apartment object given as parameter (otherApartment)
    public int priceDifference(Apartment otherApartment){
        return Math.abs(this.pricePerSquareMeter*this.squareMeters -
                otherApartment.pricePerSquareMeter*otherApartment.squareMeters);
    }

    //returns true if the Apartment-object for which
    //the method is called (this) has a higher price than the apartment object given as parameter (otherApartment)
    public boolean moreExpensiveThan(Apartment otherApartment){

        int thisPrice = this.pricePerSquareMeter*this.squareMeters;
        int otherPrice = otherApartment.pricePerSquareMeter*otherApartment.squareMeters;
        if(thisPrice > otherPrice){ return true; }
        return false;
    }
}
