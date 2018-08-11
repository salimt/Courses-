/**
 * author: salimt
 */



public class StringUtils {
    public static boolean included(String word, String searched) {
        if (word != null && searched != null) {
            word = word.trim().toUpperCase();
            searched = searched.trim().toUpperCase();

            return word.contains(searched);
        }
        return false;
    }
}
