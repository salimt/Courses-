
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        for(int i=0; i< myWords.length; i++){
            ret+=myWords[i];
            ret+=" ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if(this.length() != other.length()){ return false; }
        for(int i=0; i<this.length(); i++){
            if(! other.wordAt(i).equals(this.wordAt(i))){
                return false;
            }
        }
        return true;
    }

    public int hashCode(){
        return this.toString().hashCode();
    }

    public WordGram shiftAdd(String word) {	
        WordGram out = new WordGram(myWords, 0, myWords.length);
        for(int i=0; i<myWords.length-1; i++){
            myWords[i] = myWords[i+1];
        }myWords[myWords.length-1] = word;
        return out;
    }

}