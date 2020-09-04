//@author: salimt

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AdditionAndSubtraction {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int x, y, z;
        x = in.nextInt();
        y = in.nextInt();
        z = in.nextInt();

        int result = -1;
        ArrayList<Integer> arlist = new ArrayList<Integer>();
        arlist.add(0,0);
        if(z==0){ result = 0; }
        else {
            for(int i=1; i<1001; i++){
                int firstVal = arlist.get(2*i-2) + x;
                arlist.add(2*i-1, firstVal);
                int secVal = firstVal - y;
                arlist.add(2*i, secVal);
                if (firstVal == z || secVal == z){
                    result = firstVal == z? 2*i-1 : 2*i;
                    break;
                }
            }
        }
        out.println(result);

        in.close();
        out.close();
    }
}