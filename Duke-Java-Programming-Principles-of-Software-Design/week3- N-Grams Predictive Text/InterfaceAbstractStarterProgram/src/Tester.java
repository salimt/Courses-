import edu.duke.FileResource;

import java.util.ArrayList;

/**
 * @author: salimt
 */

public class Tester {

    public static void main(String[] args) {
        Tester t = new Tester();
        t.testGetFollowsWithFile();
    }

    public void testGetFollows(){
        MarkovOne markov = new MarkovOne();
        markov.setTraining("this is a test yes this is a test.");
        ArrayList<String> lettr = markov.getFollows("t");
        System.out.println(markov.getRandomText(500));
        System.out.println("\narraylist: " + lettr + " \nlength: " + lettr.size());
    }

    public void testGetFollowsWithFile(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setRandom(42);
        markov.setTraining(st);

        ArrayList<String> lettr = markov.getFollows("t");
        System.out.println(markov.getRandomText(500));
        System.out.println("\narraylist: " + lettr + " \nlength: " + lettr.size());
    }
}
