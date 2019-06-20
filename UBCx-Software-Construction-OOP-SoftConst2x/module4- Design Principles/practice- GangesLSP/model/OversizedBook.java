package model;

/**
 * A book that is much larger than standard sized books, e.g., an encyclopedia or an atlas
 * @author salimt
 */
public class OversizedBook extends Book {

    private double shippingMultiplier;

    private static final double BASE_SHIPPING = 5.00;
    private static final String MIN_SIZE = "large";

    public OversizedBook(String title, double price) {
        super(title, price);

        // More expensive books should have a higher shipping multiplier
        if (price > 9.50) {
            shippingMultiplier = 2;
        } else {
            shippingMultiplier = 1;
        }
    }


    // REQUIRES: model.Box must be large
    // MODIFIES: this
    // EFFECTS:  If the book fits in the given box, then the two are associated
    //          (the book is packaged); otherwise return null.
    @Override
    public Box packageBook(Box b) {
        if (b.getSize().equals("large")) {
            System.out.println("The large box is big enough for this oversized book.");
        } else {
            System.out.println("WARNING: This box is way too small for an oversized book!");
        }
        this.setBox(b);
        b.setContents(this);
        return b;
    }

    // EFFECTS: Returns the shipping cost for this book, which is a base rate times a multiplier based on the book price.
    @Override
    public double calculateShipping() {
        return BASE_SHIPPING * shippingMultiplier;
    }

    // EFFECTS: returns the minimum size box required to package this book
    @Override
    public String getMinBoxSize() {
        System.out.println("An oversized book can only fit in a large box.");
        return MIN_SIZE;
    }

}
