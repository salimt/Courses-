
public class Main {
    public static void main(String[] args) {
        Menu exactum = new Menu();

        exactum.addMeal("Fish fingers with sour cream sauce");
        exactum.addMeal("Vegetable casserole with salad cheese");
        exactum.addMeal("Chicken and nacho salad");

        exactum.printMeals();

        exactum.clearMenu();
        exactum.printMeals();
    }
}
