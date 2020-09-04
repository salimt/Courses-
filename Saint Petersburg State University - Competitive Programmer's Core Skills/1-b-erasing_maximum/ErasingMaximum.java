//@author: salimt

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ErasingMaximum {
    public static void main(String[] arg) {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n;
        n = in.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i)
            a[i] = in.nextInt();

        int[] result = new int[n - 1];
        int[] b = a.clone();
        Arrays.sort(b);

        int maxVal = b[n-1];
        ArrayList<Integer> idx = new ArrayList<>();
        for(int i=0; i<n; i++){
            if(a[i]==maxVal){
                idx.add(i);
            } if(idx.size()==3){
                break;
            }
        }

        int index = idx.size()==3? idx.get(2) : idx.get(0);
        for(int i=0, k=0; i<n; i++){
            if(i==index){
                continue;
            } result[k++] = a[i];
        }

        for (int i = 0; i < result.length; ++i) {
            if (i != 0) out.print(' ');
            out.print(result[i]);
        }
        out.println();

        in.close();
        out.close();
    }
}