
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author salimt
 */

import edu.duke.FileResource;

public class MarkovRunner {

    public static void main(String[] args) {
        MarkovRunner mr = new MarkovRunner();
        mr.runMarkovZero();
    }

    public void runMarkovZero() {
		FileResource fr = new FileResource();
		String st = fr.asString();
		st = st.replace('\n', ' ');
		MarkovZero markov = new MarkovZero();
		markov.setTraining(st);
		markov.setRandom(1024);
		for(int k=0; k < 3; k++){
			String text = markov.getRandomText(500);
			printOut(text);
		}
	}

    public void runMarkovOne() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        markov.setRandom(365);
        for(int k=0; k < 3; k++){
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }

    public void runMarkovFour() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovFour markov = new MarkovFour();
        markov.setTraining(st);
        markov.setRandom(715);
        for(int k=0; k < 3; k++){
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }

    public void runMarkovModel() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovModel markov = new MarkovModel();
        markov.setTraining(st);
        markov.setRandom(953);
        markov.setN(7);
        for(int k=0; k < 3; k++){
            String text = markov.getRandomText(500);
            printOut(text);
        }
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
