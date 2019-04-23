package spelling;

import java.util.List;

/**
 * @author UC San Diego Intermediate MOOC team
 *
 */

public interface WordPath {

	/** Return a path from word1 to word2 through dictionary words with
	 *  the restriction that each step in the path can involve only a
	 *  single character mutation  
	 * @param word1 The first word
	 * @param word2 The second word
	 * @return list of Strings which are the path from word1 to word2
	 *         including word1 and word2
	 */
	public List<String> findPath(String word1, String word2);
}
