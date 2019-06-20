/**
 * @author: salimt
 */

package model;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String name;
    private List <String> ingredients;
    private String recipe;
    private String desc;

    public Dish(String name) {
        this.name = name;
        this.ingredients = new ArrayList <>();
        this.recipe = null;
        this.desc = null;
    }

    public Dish(String name, String desc, List ingredients, String recipe) {
        this.name = name;
        this.ingredients = ingredients;
        this.recipe = recipe;
        this.desc = desc;
    }

    //getters
    public String getName() { return name; }
    public List <String> getIngredients() { return ingredients; }
    public String getRecipe() { return recipe; }
    public String getDesc() { return desc; }

    //setters
    public void setName(String name) { this.name = name; }
    public void setIngredients(ArrayList <String> ingredients) { this.ingredients = ingredients; }
    public void setRecipe(String recipe) { this.recipe = recipe; }
    public void setDesc(String desc) { this.desc = desc; }
}
