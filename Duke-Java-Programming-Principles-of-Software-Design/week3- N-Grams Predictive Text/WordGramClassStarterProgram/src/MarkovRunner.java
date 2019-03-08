
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {

    public static void main(String[] args) {
        MarkovRunner mr = new MarkovRunner();
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        mr.runModel(new EfficientMarkovWord(3), st, 50, 371);
    }

    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text);
        markov.setRandom(42);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    }

    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        // MARKOV WORD 1

        long start1 = System.nanoTime();
        runModel(new MarkovWord(2),st,100,42);
        long end1 = System.nanoTime()-start1;

        System.out.println(end1);
        // EFFICIENT MARKOV
        long start2 = System.nanoTime();
        runModel(new EfficientMarkovWord(2),st,100,42);
        long end2 = System.nanoTime()-start2;

        System.out.println("Time for Markov One: " + (end1-start1));
        System.out.println("Time for Efficient One: " + (end2-start2));
    }

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(3);
        runModel(markovWord, st, 200);
    } 

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
