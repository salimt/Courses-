package tudelft.sum;

import java.util.ArrayList;
import java.util.Collections;

// Source: https://leetcode.com/problems/add-two-numbers/description/
class TwoNumbersSum {

    public ArrayList<Integer> addTwoNumbers(ArrayList<Integer> first, ArrayList<Integer> second) {
        Collections.reverse(first);
        Collections.reverse(second);

        int complement = 0;
        ArrayList<Integer> result = new ArrayList<>();

        int maxVal = Math.max(first.size(), second.size());
        for(int i = 0; i < maxVal; i++){
            int firstVal = i < first.size() ? first.get(i) : 0;
            int secondVal = i < second.size() ? second.get(i) : 0;
            int total = firstVal + secondVal + complement;
            if (i==maxVal-1 && total >= 10) {
                result.add(i, total%10);
                result.add(i+1, total/10);
                break;
            }
            complement = 0;
            if (total >= 10){
                complement = 1;
                total -= 10;
            }
            result.add(i, total);
        }

        Collections.reverse(result);
        return result;
    }
}
