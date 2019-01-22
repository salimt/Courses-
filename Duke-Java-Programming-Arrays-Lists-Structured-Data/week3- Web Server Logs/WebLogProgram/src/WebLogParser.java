/**
 * @author: salimt
 */

import java.text.*;
import java.util.*;

public class WebLogParser {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:kk:mm:ss Z", Locale.US);
    private static String munchTo(StringBuilder sb, String delim) {
        int x = sb.indexOf(delim);
        if (x == -1) {
            x = sb.length();
        }
        String ans = sb.substring(0,x);
        sb.delete(0, x + delim.length());
        return ans;
    }
    public static LogEntry parseEntry(String line) {
        //Assumes line is vald and in this format:
        //110.76.104.12 - - [30/Sep/2015:07:47:11 -0400] "GET //favicon.ico HTTP/1.1" 200 3426
        StringBuilder sb = new StringBuilder(line);
        String ip = munchTo(sb, " ");
        munchTo(sb, " "); //ignore -
        munchTo(sb, " ["); //ignore -, and eat the leading [
        String dateStr = munchTo(sb, "] \""); //]-space is intentional: eat both
        Date date = parseDate(dateStr);
        String request = munchTo(sb, "\" "); // quote-space is intentional: eat both
        String statusStr = munchTo(sb, " ");
        int status = Integer.parseInt(statusStr);
        String byteStr = munchTo(sb, " ");
        int bytes = Integer.parseInt(byteStr);
        return new LogEntry(ip, date, request, status, bytes);
    }
    public static Date parseDate(String dateStr) {
        ParsePosition pp = new ParsePosition(0);
        return  dateFormat.parse(dateStr, pp);
    }

}