package Manager;

import java.time.LocalDateTime;

public class Time {
    public int year, month, day, hour, minute, second;
    
    public Time() {
        LocalDateTime localtime = LocalDateTime.now();
        year = (localtime.getYear() - (Math.round((localtime.getYear() / 100 ) * 100)));
        month = localtime.getMonthValue();
        day = localtime.getDayOfMonth();
        hour = localtime.getHour();
        minute = localtime.getMinute();
        second = localtime.getSecond();
    } 
}
