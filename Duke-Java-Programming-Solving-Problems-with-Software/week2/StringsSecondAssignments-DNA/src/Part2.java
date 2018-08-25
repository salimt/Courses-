/**
 * @author: salimt
 */

public class Part2 {


    public static void main(String[] args) {

        System.out.println(howMany("GAA", "ATGAACGAATTGAATC"));     //3
        System.out.println(howMany("AA", "ATAAAA"));                //2
        System.out.println(howMany("SS", "SSASSBBCCSSXXSXXSS"));    //4

    }

    public static int howMany(String a, String b){

        int count = 0, found = 0;

        while(found != -1){
            found = b.indexOf(a, found);
            if(found == -1){ break; }
//            System.out.println(b.substring(found, found+a.length()) + found);
            found += a.length();
            count++;
        }
        return count;
    }

}
