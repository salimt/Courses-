
public class Main {

    public static void main(String[] args) {
//        Apartment studioManhattan = new Apartment(1, 16, 5500);
//        Apartment twoRoomsBrooklyn = new Apartment(2, 38, 4200);
//        Apartment fourAndKitchenBronx = new Apartment(3, 78, 2500);
//
//        System.out.println( studioManhattan.larger(twoRoomsBrooklyn) );       // false
//        System.out.println( fourAndKitchenBronx.larger(twoRoomsBrooklyn) );   // true

//        Apartment studioManhattan = new Apartment(1, 16, 5500);
//        Apartment twoRoomsBrooklyn = new Apartment(2, 38, 4200);
//        Apartment fourAndKitchenBronx = new Apartment(3, 78, 2500);
//
//        System.out.println( studioManhattan.priceDifference(twoRoomsBrooklyn) );        // 71600
//        System.out.println( fourAndKitchenBronx.priceDifference(twoRoomsBrooklyn) );    // 35400

        Apartment studioManhattan = new Apartment(1, 16, 5500);
        Apartment twoRoomsBrooklyn = new Apartment(2, 38, 4200);
        Apartment fourAndKitchenBronx = new Apartment(3, 78, 2500);

        System.out.println( studioManhattan.moreExpensiveThan(twoRoomsBrooklyn) );       // false
        System.out.println( fourAndKitchenBronx.moreExpensiveThan(twoRoomsBrooklyn) );   // true

    }
}
