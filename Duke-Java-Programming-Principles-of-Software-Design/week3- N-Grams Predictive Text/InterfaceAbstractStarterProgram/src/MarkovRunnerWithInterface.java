
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author salimt
 */

import edu.duke.*; 

public class MarkovRunnerWithInterface {

    public static void main(String[] args) {
        MarkovRunnerWithInterface mvi = new MarkovRunnerWithInterface();
        //mvi.runMarkov();
        mvi.runMarkov();
    }

    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
			String st= markov.getRandomText(size);
			printOut(st);
		}
    }
    
    public void runMarkov() {
        FileResource fr = new FileResource();
		String st = fr.asString();
		st = st.replace('\n', ' ');
		int size = 50;
		/*
        MarkovZero mz = new MarkovZero();
        runModel(mz, st, size, 3);
    
        MarkovOne mOne = new MarkovOne();
        runModel(mOne, st, size, 3);
        
        MarkovModel mThree = new MarkovModel(3);
        runModel(mThree, st, size, mThree.getSeed());
        
        MarkovFour mFour = new MarkovFour();
        runModel(mFour, st, size, 3);*/

        EfficientMarkovModel mEff = new EfficientMarkovModel(6);
        runModel(mEff, st, size, 792);
    }

    private void testHashMap(){
        /*EfficientMarkovModel markov = new EfficientMarkovModel(5);
        markov.setTraining("yes-this-is-a-thin-pretty-pink-thistle");*/
        EfficientMarkovModel markov = new EfficientMarkovModel(6);
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        markov.setRandom(792);
        markov.setTraining(st);
        System.out.println(markov.getRandomText(50));
        //markov.printHashMapInfo();
        System.out.println("running with " + markov);
    }

    private void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 1000;
        int seed = 42;
        int order = 2;

        long start = System.nanoTime();
        MarkovModel mModel = new MarkovModel(order);
        runModel(mModel, st, size, seed);
        long elapsedTime = System.nanoTime() - start;
        System.out.println(elapsedTime);

        long start2 = System.nanoTime();
        EfficientMarkovModel mEff = new EfficientMarkovModel(order);
        runModel(mEff, st, size, seed);
        long elapsedTime2 = System.nanoTime() - start2;

        System.out.println("Elapsed Time for MarkovModel: " + elapsedTime +
                           "\nElapsed Time for EfficientMarkovModel: " + elapsedTime2);
        if(elapsedTime<elapsedTime2){ System.out.println("Markov Model is faster."); }
        else{System.out.println("EfficientMarkovModel is faster.");}
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
