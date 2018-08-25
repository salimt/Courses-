/**
 * @author: salimt
 */

public class twoOccurrences {
    
    public static void main(String[] args) {

        System.out.println(twoOccurrences("by", "A story by Abby Long"));   //true
        System.out.println(twoOccurrences("a", "banana"));                  //true
        System.out.println(twoOccurrences("atg", "ctgtatgta"));             //false

        System.out.println(lastPart("an", "banana"));                       //ana
        System.out.println(lastPart("zoo", "forest"));                      //forest

    }

    public static boolean twoOccurrences(String a, String b){

        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){
            lastIndex = b.indexOf(a, lastIndex);
            if(lastIndex != -1){
                count++;
                lastIndex += a.length();
            }if(count >= 2){ return true; }
        }return false;
    }


    public static String lastPart(String a, String b){

        int lastIndex = 0;

        while(lastIndex != -1){
            lastIndex = b.indexOf(a, lastIndex);
            if(lastIndex != -1){
                return b.substring(lastIndex+a.length());
            }
        }return b;
    }

}
