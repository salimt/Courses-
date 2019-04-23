/**
 * 
 */
package spelling;

import java.util.List;

/**
 * @author Christine
 *
 */
public interface AutoComplete {
	public List<String> predictCompletions(String prefix, int numCompletions);
}
