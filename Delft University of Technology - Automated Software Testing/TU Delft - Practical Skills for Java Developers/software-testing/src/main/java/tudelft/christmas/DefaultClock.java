package tudelft.christmas;

import java.util.Calendar;

public class DefaultClock implements Clock {

    @Override
    public Calendar now () {
        return Calendar.getInstance();
    }
}
