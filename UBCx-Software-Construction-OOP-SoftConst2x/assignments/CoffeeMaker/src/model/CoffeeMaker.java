package model;

import model.exceptions.*;

/**
 * A coffee maker used to train baristas.
 *
 * Class invariant: cups remaining >= 0, time since last brew >= 0
 *
 * @author salimt
 */

public class CoffeeMaker {

    private int timeSinceLastBrew;
    private int cupsRemaining;

    public CoffeeMaker(){
        timeSinceLastBrew = 0;
        cupsRemaining = 0;
    }

    // getters
    public int getTimeSinceLastBrew(){
        return timeSinceLastBrew;
    }
    public int getCupsRemaining(){
        return cupsRemaining;
    }

    // EFFECTS: return true if there are coffee cups remaining
    public boolean areCupsRemaining(){
        return cupsRemaining>0;
    }

    //REQUIRES: a non-negative integer
    //EFFECTS: sets time since last brew, if a long time has passed throw StaleCoffeeException
    public void setTimeSinceLastBrew(int time) {
        this.timeSinceLastBrew = time;
    }

    //REQUIRES: beans between 2.40 and 2.60 cups, water > 14.75 cups
    //EFFECTS: sets cups remaining to full (20 cups) and time since last brew to 0
    //         if there are more or less than adequate beans throws NotEnough or TooMany BeansException
    //         if water is not enough for cups throws WaterException
    public void brew(double beans, double water) throws BeansAmountException, WaterException {
        if(water <= 14.75)
            throw new WaterException(water);

        if(beans >= 2.40){
            if(beans <= 2.60){
                this.timeSinceLastBrew = 0;
                this.cupsRemaining = 20;
            }else{
                throw new TooManyBeansException(beans);
            }
        }else{
            throw new NotEnoughBeansException(beans);
        }
    }

    ///REQUIRES: cups remaining > 0, time since last brew < 60
    //MODIFIES: this
    //EFFECTS: subtracts one cup from cups remaining
    //         if time since last brew passed 60 then throws StaleCoffeeException
    //         if there are no remaining cups to pour then throws NoCupsRemainingException
    public void pourCoffee() throws NoCupsRemainingException, StaleCoffeeException {
        if(cupsRemaining > 0){
            if(timeSinceLastBrew < 60){
                this.cupsRemaining--;
            }else{
                throw new StaleCoffeeException(timeSinceLastBrew);
            }
        }else{
            throw new NoCupsRemainingException();
        }
    }


}
