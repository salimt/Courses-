package spelling;

import java.util.List;

public interface SpellingSuggest {

	public List<String> suggestions(String word, int numSuggestions);
	
}
