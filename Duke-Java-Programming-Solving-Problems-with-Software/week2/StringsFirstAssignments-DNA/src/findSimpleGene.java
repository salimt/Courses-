/**
 * @author: salimt
 */

public class findSimpleGene {

    public static void main (String[] args){
        System.out.println(findSimpleGene("ATGGGTTAAGTC"));     //ATGGGTTAA
        System.out.println(findSimpleGene("gatgctataat"));     //atgctataa
    }


    public static String findSimpleGene(String dna) {
        int startIndex =  dna.toUpperCase().indexOf("ATG");
        int stopIndex =  dna.toUpperCase().indexOf("TAA", startIndex + 3);
        if (startIndex == -1 || stopIndex == -1) {   //return empty string if ATG or TAA is missing
            return "no gene found!1";
        }

        String gene = dna.substring(startIndex, stopIndex+3);
        if (gene.length() % 3 == 0) {
            return gene;
        } else {
            return "no gene found!2";
        }
    }



    public void testing() {
        String a = "cccatggggtttaaataataataggagagagagagagagttt";
        String ap = "atggggtttaaataataatag";
        //String a = "atgcctag";
        //String ap = "";
        //String a = "ATGCCCTAG";
        //String ap = "ATGCCCTAG";
        String result = findSimpleGene(a);
        if (ap.equals(result)) {
            System.out.println("success for " + ap + " length " + ap.length());
        }
        else {
            System.out.println("mistake for input: " + a);
            System.out.println("got: " + result);
            System.out.println("not: " + ap);
        }
    }



}
