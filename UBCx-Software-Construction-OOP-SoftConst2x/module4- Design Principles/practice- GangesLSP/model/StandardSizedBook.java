/**
 * @author: salimt
 */

package model;

public class StandardSizedBook extends Book {

    private static final double FLAT_RATE_SHIPPING = 2.00;
    private static final String MIN_BOX_SIZE = "medium";

    public StandardSizedBook(String title, double price) {
        super(title, price);
    }

    // EFFECTS: Returns the shipping cost for this book, which is a base rate times a multiplier based on the book price.
    @Override
    public double calculateShipping() {
        return FLAT_RATE_SHIPPING;
    }

    @Override
    // EFFECTS: returns the minimum required size of box for packaging this book
    public String getMinBoxSize() {
        System.out.println("A standard size book can fit in a medium or large box.");
        return MIN_BOX_SIZE;
    }

    @Override
    // REQUIRES: model.Box must be medium or large
    // MODIFIES: this
    // EFFECTS:  If the book fits in the given box, then the two are associated (the book is packaged).
    public Box packageBook(Box b) {
        if (b.getSize().equals("medium")) {
            System.out.println("The book fits snugly into this box.");
        } else {
            System.out.println("This box is a little big for the book, but it fits.");
        }
        this.setBox(b);
        b.setContents(this);
        return b;
    }


}
