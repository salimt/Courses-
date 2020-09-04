//@author: salimt

import java.io.PrintWriter;
import java.util.*;

public class StraightFlush {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        Map<String, Integer> cardRanking = new HashMap<String, Integer>() {
            {
                put("2", 0);
                put("3", 1);
                put("4", 2);
                put("5", 3);
                put("6", 4);
                put("7", 5);
                put("8", 6);
                put("9", 7);
                put("T", 8);
                put("J", 9);
                put("Q", 10);
                put("K", 11);
                put("A", 12);

            }
        };

        String checkHand = "YES";
        ArrayList<Integer> ranks = new ArrayList<>();
        HashSet<String> suits = new HashSet<>();

        for(int i=0; i<5; i++){
            String card = in.next().toUpperCase();
            ranks.add(i, cardRanking.get(card.substring(0,1)));
            suits.add(card.substring(1,2));
        } Collections.sort(ranks);

        if(suits.size() == 1) {
            if (ranks.contains(12) && ranks.contains(11) && ranks.contains(2)) {
                checkHand = "NO";
            } else {
                for (int i = 1; i < ranks.size(); i++) {
                    if ((ranks.get(i) != 12) && ranks.get(i - 1) + 1 != ranks.get(i)) {
                        checkHand = "NO";
                        break;
                    }
                }
            }
        } else {
            checkHand = "NO";
        }

        out.println(checkHand);

        in.close();
        out.close();
    }
}