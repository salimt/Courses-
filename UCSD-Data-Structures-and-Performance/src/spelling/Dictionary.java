/**
 * Dictionary interface, representing and old school word-lookup dictionary
 */
package spelling;

/**
 * @author Christine
 *
 */
public interface Dictionary {
	/** Add this word to the dictionary.
	 * @param word The word to add
	 * @return true if the word was added to the dictionary 
	 * (it wasn't already there). */
	public abstract boolean addWord(String word);

	/** Is this a word according to this dictionary? */
	public abstract boolean isWord(String s);
	
	/** Return the number of words in the dictionary */
	public abstract int size();
	
}
