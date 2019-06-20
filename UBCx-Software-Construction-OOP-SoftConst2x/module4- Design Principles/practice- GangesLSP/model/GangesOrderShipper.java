package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The system that packages and ships book orders for Ganges.com, Inc.
 * @author salimt
 */
public class GangesOrderShipper {

    private static final double MAX_EXPECTED_SHIPPING = 26.50;

    public static void main(String[] args) {
        // Some books that need to be shipped
        Book b1 = new StandardSizedBook("The Unbearable Lightness of Being John Malkovich", 19.95);
        Book b2 = new OversizedBook("The Princess Brideshead Revisited", 29.15);

        // Add them to a list so it's easy to iterate through them
        List<Book> booksToShip = new ArrayList<>();
        booksToShip.add(b1);
        booksToShip.add(b2);

        for (Book b : booksToShip) {
            String thisTitle = b.getTitle();
            System.out.printf("Packaging and shipping %s.\n", thisTitle);

            // Package the book in a medium sized box
            String boxSize = b.getMinBoxSize();
            Box box = b.packageBook(new Box(boxSize));
            double shipping = box.calculateShipping();
            System.out.printf("The book %s is being shipped for %.2f in a %s-sized box.\n",
                    thisTitle, shipping, boxSize);

            // model.Book is successfully boxed up, so calculate the shipping cost and make sure it's not too expensive
            if (shipping > MAX_EXPECTED_SHIPPING) {
                System.out.printf("WARNING: The shipping for %s is too expensive!\n", thisTitle);
            } else {
                System.out.printf("SUCCESS: %s shipped successfully!\n", thisTitle);
            }

            System.out.println();
        }
    }
}
