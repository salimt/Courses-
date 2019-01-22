/**
 * @author: salimt
 */

import edu.duke.FileResource;
import java.util.*;

public class CharactersInPlay {
    private HashMap <String, Integer> myChars;

    public CharactersInPlay(){
        myChars = new HashMap <String, Integer>();
    }

    public static void main(String[] args) {
        CharactersInPlay chr = new CharactersInPlay();
        chr.tester();
    }

    public void update(String person){
        if(!myChars.containsKey(person)){
            myChars.put(person, 1);
        }else{
            myChars.put(person, myChars.get(person)+1);
        }
    }

    public void findAllCharacters(){
        FileResource resource = new FileResource();
        String charName = "";

        for(String line: resource.lines()){
            if(line.contains(".")){
                charName = line.substring(0,line.indexOf("."));
                update(charName);
            }
        }
    }

    public void tester(){
        findAllCharacters();
        List sortedVals=new ArrayList(myChars.values());
        Collections.sort(sortedVals);
        Collections.reverse(sortedVals);

        for(Object i: sortedVals){
            if((Integer)i > 13){
                for (Map.Entry<String, Integer> entry : myChars.entrySet()) {
                    if((Integer)i == entry.getValue()){
                        System.out.println(entry.getKey() + " = " + i);
                    }
                }
            }
        }charactersWithNumParts(10,15);
    }


    public void charactersWithNumParts(int num1, int num2){
        List sortedVals=new ArrayList(myChars.values());
        Collections.sort(sortedVals);
        Collections.reverse(sortedVals);

        int between = 0;
        for(Object i: sortedVals){
            if((Integer)i >= num1 && (Integer)i <= num2)
                between+=1;
        }
        System.out.println("There are " + between + " number that are between " + num1 + " and " + num2);
    }
}