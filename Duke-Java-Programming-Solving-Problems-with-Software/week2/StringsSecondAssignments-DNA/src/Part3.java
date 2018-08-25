/**
 * @author: salimt
 */

public class Part3 {
    public static void main(String[] args) {

        System.out.println(countGenes("ATGTAAGATGCCCTAGT"));        //2
        printAllGenes("ATGTAAGATGCCCTAGT");
    }

    public static int countGenes(String dna){

        int startIndex = 0, counter = 0;

        while(true){
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()) { break; }
            counter++;
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
        }
        return counter;
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

}
