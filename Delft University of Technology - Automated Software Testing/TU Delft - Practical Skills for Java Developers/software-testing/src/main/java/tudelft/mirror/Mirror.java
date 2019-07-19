package tudelft.mirror;

/**
 * @author salimt
 */

public class Mirror {

    public String mirrorEnds(String string) {

        if (string.length() <= 1) { return string; }

        String mirror = "";

        int begin = 0;
        int end = string.length() - 1;
        for (; begin < end; begin++, end--) {
            if (string.charAt(begin) == string.charAt(end)) {
                mirror += String.valueOf(string.charAt(end));
            }
            else {
                break;
            }
        }

        return (string.length()%2==0 && string.charAt(begin)==string.charAt(end)) || begin == end ? string : mirror;
    }
}