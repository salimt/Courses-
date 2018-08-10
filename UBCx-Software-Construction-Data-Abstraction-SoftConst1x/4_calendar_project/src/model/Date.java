/**
 * author: salimt
 */

package model;

import java.time.*;

public class Date {

    LocalDateTime now = LocalDateTime.now();

    public Integer getDay() { return now.getDayOfMonth(); }
    public Integer getMonth() { return now.getMonthValue(); }
    public Integer getYear() { return now.getYear(); }

    @Override
    public String toString() {
        return getDay() + "-" + getMonth() + "-" + getYear();
    }
}
