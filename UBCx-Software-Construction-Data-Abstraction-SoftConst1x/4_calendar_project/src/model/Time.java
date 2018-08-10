/**
 * author: salimt
 */

package model;
import java.time.*;

public class Time {

    LocalDateTime now = LocalDateTime.now();

    public Integer getSeconds() { return now.getSecond(); }
    public Integer getMinutes() { return now.getMinute(); }
    public Integer getHours() { return now.getHour(); }

    @Override
    public String toString() {
        return getHours() + ":" + getMinutes() + ":" + getSeconds();
    }
}
