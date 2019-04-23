package spelling;

import java.io.PrintWriter;
import java.lang.StringBuilder;
import java.util.List;

public class TrieGrader {
    StringBuilder feedback;


    public TrieGrader() {
        feedback = new StringBuilder();
    }


    public static void main(String[] args) {
        TrieGrader g = new TrieGrader();

        PrintWriter out;
        try {
            out = new PrintWriter("output.out");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            AutoCompleteDictionaryTrie ac = new AutoCompleteDictionaryTrie();

            g.testAddWords(ac);

            g.testWordsInOut(ac);

            g.testPredictions(ac);

        } catch (Exception e) {
            out.println(g.getFeedback() + "Error during runtime: " + e);
            out.close();
            return;
        }

        StringBuilder feedback = g.getFeedback();


        out.println(feedback.toString());
        out.close();
    }


    private void testAddWords(AutoCompleteDictionaryTrie ac) {
        feedback.append( "//TESTING ADDING WORDS (addWord, insert)//");
        appendTestString(1, "Adding first word to dictionary...");
        feedback.append("addWord returned " + ac.addWord("dog") + ".");

        appendTestString(2,"Adding two more words and testing size...");
        ac.addWord("downhill");
        ac.addWord("downhiller");

        feedback.append("Size is " + ac.size() + ".");

        appendTestString(3, "Adding more words to dictionary trie (testing size after insertions)...");

        ac.addWord("doge");
        ac.addWord("dogg");
        ac.addWord("dawg");
        ac.addWord("dage");
        ac.addWord("doggo");
        ac.addWord("doggie");
        ac.addWord("doggos");
        ac.addWord("doggoes");
        ac.addWord("doggies");
        ac.addWord("test");
        ac.addWord("tester");
        ac.addWord("testing");
        ac.addWord("tested");
        ac.addWord("testin");
        ac.addWord("teston");
        ac.addWord("testone");
        ac.addWord("testine");
        ac.addWord("testell");
        ac.addWord("testcase");
        ac.addWord("testbase");
        ac.addWord("testcases");


        feedback.append("Dict size is " + ac.size() + ".");

        // get current size before trying to add duplicate word

        appendTestString(4,"Adding duplicate word...");
        feedback.append("Adding duplicate word returned " + ac.addWord("dog") + ".");

        appendTestString(5, "Checking size after try to add duplicate word...");
        feedback.append("Dict size is " + ac.size()+ ".");
    }

    private void testWordsInOut(AutoCompleteDictionaryTrie ac) {

        feedback.append("\n\n\n//TESTING FOR WORDS IN/OUT OF DICTIONARY (isWord)//");
        appendTestString(6,"Checking empty string...");
        // test empty string
        feedback.append("Empty string in dictionary: " + ac.isWord("") + ".");

        appendTestString(7, "Checking for word in dictionary...");
        feedback.append("'doggoes' in dictionary: " + ac.isWord("doggoes") + ".");

        // test word only missing last letter
        appendTestString(8, "Testing word only missing last letter...");
        feedback.append("'downhil' in dictionary: " + ac.isWord("downhil") + ".");

        //test word with added letter
        appendTestString(9, "Testing word with one extra letter...");
        feedback.append("'downhille' in dictionary: " + ac.isWord("downhille") + ".");

        appendTestString(10, "Testing for more words in dictionary...");
        feedback.append("'test' in dictionary: " + ac.isWord("test") + ". 'testcases' in dictionary: " + ac.isWord("testcases") + ". 'testone' in dictionary: " + ac.isWord("testone") + ".");


        appendTestString(11, "Testing word with capital letters...");
        feedback.append("'TeSt' in dictionary: " + ac.isWord("TeSt") + ".");



    }

    private void testPredictions(AutoCompleteDictionaryTrie ac) {

        feedback.append("\n\n\n//TESTING AUTO COMPLETE FUNCTIONALITY (predictCompletions)//");
        List<String> auto = ac.predictCompletions("dog", 3);

        appendTestString(12, "3 completions requested...");
        feedback.append("Autocomplete returned the following: ");
        for (String s : auto) {
            feedback.append(s + ", ");
        }

        appendTestString(13,"Testing size of list...");
        feedback.append("predictCompletions returned " + auto.size() + " elements.");

        auto = ac.predictCompletions("soup", 6);
        appendTestString(14, "6 completions requested, 0 expected...");
        feedback.append("predictCompletions found " + auto.size() + " words.");

        auto = ac.predictCompletions("dogg", 10);
        appendTestString(15, "10 completions requested, 6 expected...");
        feedback.append("predictCompletions found " + auto.size() + " elements.");

        appendTestString(16, "Testing for correctness of 6 words...");
        feedback.append("Words returned by predictCompletions: ");
        for (String s : auto) {
            feedback.append(s + ", ");
        }

        auto = ac.predictCompletions("test", 7);

        appendTestString(17, "7 completions requested (test for size)...");
        feedback.append("predictCompletions returned " + auto.size() + " elements.");

        appendTestString(18, "Testing if list is sorted from shortest to longest...");
        feedback.append("Check above output.");

        List<String> partialList = auto.subList(0, 5);

        appendTestString(19, "Testing if list contains correct shorter words...");
        feedback.append("Check above output.");


        appendTestString(20, "Testing for remaining words...");
        partialList = auto.subList(5, auto.size());

        int count = 0;

        count = partialList.contains("testone") ? ++count:count ;
        count = partialList.contains("testine") ? ++count:count;
        count = partialList.contains("testell") ? ++count:count;
        count = partialList.contains("testing") ? ++count:count;

        feedback.append("Out of 'testone', 'testine', 'testell', and 'testing', " + count + " words were found.");

    }

    private void appendTestString(int num, String description) {
        feedback.append("\n\n** Test #" + num + ": " + description + "\n");
    }

    private StringBuilder getFeedback() {
        return this.feedback;
    }


}

