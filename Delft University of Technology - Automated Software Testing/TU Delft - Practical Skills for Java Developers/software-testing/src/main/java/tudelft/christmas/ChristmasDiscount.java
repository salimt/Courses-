package tudelft.christmas;

import java.util.Calendar;

public class ChristmasDiscount {

    public double applyDiscount(double rawAmount) {
        Calendar today = Calendar.getInstance();

        double discountPercentage = 0;
        boolean isChristmas = today.get(Calendar.DAY_OF_MONTH) == 25 &&
                today.get(Calendar.MONTH) == Calendar.DECEMBER;

        if(isChristmas)
            discountPercentage = 0.15;

        return rawAmount - (rawAmount * discountPercentage);
    }

}
