/**
 * @author: salimt
 */

public class Part1 {
    public static void main(String[] args) {

        testFindStopCodon();
        testprintAllGenes();

    }

    public static int findStopCodon(String dna, int startIndex, String stopCodon){

        int currIndex = dna.indexOf(stopCodon, startIndex+1);
        while(currIndex != -1 ){
            if((currIndex - startIndex) % 3 == 0){ return currIndex; }
            currIndex = dna.indexOf(stopCodon, currIndex+stopCodon.length());
        }

        return dna.length();
    }


    public static String findGene(String dna, int where) {

        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1) { return ""; }

        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");

        int minIndex = 0;

        if (taaIndex == -1 ||
                (tgaIndex != -1 && tgaIndex < taaIndex)) {
            minIndex = tgaIndex;
        }
        else { minIndex = taaIndex; }

        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }

        if (minIndex == -1){ return ""; }

        return dna.substring(startIndex,minIndex + 3);
    }

    public static void printAllGenes(String dna) {

        int startIndex = 0;

        while ( true ) {
            String gene = findGene(dna, startIndex);
            if (gene.isEmpty()) { break; }
            System.out.println(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
    }

    public static void testprintAllGenes(){
//                    012345678901234567890123456789012
        String dna = "ATGxxxyyyTAAxxxTAAyyyxxxyyyTAAxxx"; //3
        printAllGenes(dna);

    }

    public static void testFindStopCodon(){
//                    012345678901234567890123456
        String dna = "xxxyyyxxxATGxxxTAAATGTAATAG";

        int stopCodon = findStopCodon(dna, 9, "TAA");
        if(stopCodon != 15){ System.out.println("error on 15"); }

        stopCodon = findStopCodon(dna,18,"TAA");
        if(stopCodon != 21){ System.out.println("error on 21" + stopCodon); }

        stopCodon = findStopCodon(dna,19,"TAA");
        if(stopCodon != dna.length()){ System.out.println("error on dna.length 21"); }

        stopCodon = findStopCodon(dna,9,"TAG");
        if(stopCodon != 24){ System.out.println("error on TAG 24" + stopCodon); }

        stopCodon = findStopCodon(dna, 1, "TAA");
        if(stopCodon != dna.length()){ System.out.println("error on dna.length 1"); }

        System.out.println("tests finished (findStopCodon)");
    }

}
