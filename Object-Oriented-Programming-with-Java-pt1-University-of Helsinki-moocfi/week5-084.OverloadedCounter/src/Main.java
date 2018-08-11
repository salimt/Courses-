public class Main {
    public static void main(String[] args) {
        Counter SC = new Counter(5, true);
        Counter S = new Counter(5);
        Counter C = new Counter(true);
        Counter C2 = new Counter(false);

        System.out.println(SC);
        SC.decrease();
        System.out.println(SC);
        SC.increase(5);
        System.out.println(SC);
        SC.decrease(50);
        System.out.println(SC);


//        System.out.println(S);
//        System.out.println(C);
//        System.out.println(C2);
    }
}
