package filters;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author salimt
 *
 * A simple scanner for a language consisting of words and parentheses.
 */
public class Scanner {
    private List<String> tokens;

    public Scanner(String input) {
        // This pattern matches words ([a-zA-Z]+), left and right parens, and whitespace
        Pattern tokenPattern = Pattern.compile("\\(|\\)|[a-zA-Z]+|\\s+");

        // This simple scanner scans the entire input in its constructor, building a list of tokens
        // which it then returns as necessary in response to calls to its peek and advance methods.
        tokens = new LinkedList<>();
        Matcher m = tokenPattern.matcher(input);
        while (m.find()) {
            String token = m.group();
            // Throw away any white space
            if (token.matches("\\s+"))  continue;
            tokens.add(token);
        }
    }

    /**
     * Return the first token remaining, without changing anything.
     * A second call to peek without an intervening call to advance, will return this same token again.
     * @return      The first remaining token in the input, or null if no tokens remain
     */
    public String peek() {
        return tokens.size() > 0 ? tokens.get(0) : null;
    }

    /**
     * Advance the input, consuming the current token, and return the first remaining token in the input.
     * @return      The first remaining token in the input after advancing, or null if no tokens remain
     */
    public String advance() {
        tokens.remove(0);
        return tokens.size() > 0 ? tokens.get(0) : null;
    }
}
