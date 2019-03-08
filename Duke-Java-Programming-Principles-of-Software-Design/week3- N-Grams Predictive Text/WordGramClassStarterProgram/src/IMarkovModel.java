
/**
 * Write a description of interface IMarkovModel here.
 * 
 * @author salimt
 */

public interface IMarkovModel {
    public void setTraining(String text);
    
    public void setRandom(int seed);
    
    public String getRandomText(int numChars);

}
