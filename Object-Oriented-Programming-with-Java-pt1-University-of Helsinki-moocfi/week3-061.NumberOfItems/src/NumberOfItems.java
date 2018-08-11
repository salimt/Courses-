import java.util.ArrayList;

public class NumberOfItems {

    public static int countItems(ArrayList<String> list) {
        return list.size();  //returns the size of list
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Moi");
        list.add("Ciao");
        list.add("Hello");
        System.out.println("There are this many items on the list:");
        System.out.println(countItems(list));
    }

}
