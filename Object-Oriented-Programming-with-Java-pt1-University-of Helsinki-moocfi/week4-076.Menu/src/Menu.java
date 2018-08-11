
import java.util.ArrayList;

public class Menu {

    private ArrayList<String> meals;

    public Menu() {
        this.meals = new ArrayList<String>();
    }

    //adds the meal into the list if not already in the list
    public void addMeal(String meal){if(!(meals.contains(meal))){ meals.add(meal); }}

    //prints the meals in the list
    public void printMeals(){ for(String m: meals){System.out.println(m);} }

    //clears the whole menu
    public void clearMenu(){meals.clear();}
}
