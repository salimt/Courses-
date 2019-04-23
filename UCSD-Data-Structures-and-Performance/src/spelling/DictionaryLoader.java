package spelling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DictionaryLoader {

    /** Load the words from the dictionary file into the dictionary
     * 
     * @param d  The dictionary to load
     * @param filename The file containing the words to load.  Each word must be on a separate line.
     */    
	public static void loadDictionary(Dictionary d, String filename)
    {
        // Dictionary files have 1 word per line
        BufferedReader reader = null;
        try {
            String nextWord;
            reader = new BufferedReader(new FileReader(filename));
            while ((nextWord = reader.readLine()) != null) {
                d.addWord(nextWord);
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: " + filename);
            e.printStackTrace();
        }
        
    }
    
    /** Load the first N words from the dictionary file into the dictionary
     * 
     * @param d  The dictionary to load
     * @param filename The file containing the words to load.  Each word must be on a separate line.
     * @param nWords  The number of words to load.  It will load the first nWords words
     */
    public static void loadDictionary(Dictionary d, String filename, int nWords)
    {
        // Dictionary files have 1 word per line
        BufferedReader reader = null;
        try {
            String nextWord;
            reader = new BufferedReader(new FileReader(filename));
            int numLoaded = 0;
            while ((nextWord = reader.readLine()) != null && numLoaded < nWords) {
                d.addWord(nextWord);
                numLoaded++;
            }
            if (numLoaded < nWords) {
            	System.out.print("loadDicitonary Warning: End of dictionary file reached.  ");
            	System.out.println(nWords + " requested, but only " + numLoaded + " words loaded.");
            }
        } catch (IOException e) {
            System.err.println("Problem loading dictionary file: " + filename);
            e.printStackTrace();
        }    	
    	
    }
}
