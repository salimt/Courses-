
public class Accounts {

    public static void main(String[] args) {
        Account matsAccount = new Account("Matt's account", 1000);
        Account myAccount = new Account("My account", 0);
        matsAccount.withdrawal(100.0);
        myAccount.deposit(100.0);
        System.out.println(matsAccount);
        System.out.println(myAccount);
    }

}
