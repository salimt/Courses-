
import fi.helsinki.cs.tmc.edutestutils.MockStdio;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import static org.junit.Assert.*;

public class MenuTest {

    @Rule
    public MockStdio stdio = new MockStdio();

    @Test
    @Points("76.1")
    public void hasMethodAddMeal() throws Throwable {
        String klassName = "Menu";

        String metodi = "addMeal";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("Add class " + klassName + " the method: public void " + metodi + "(String meal) ", tuoteClass.method(olio, metodi)
                .returningVoid().taking(String.class).isPublic());

        String v = "\nThe code that caused the fault m = new Menu(); m.addMeal(\"Bratwurst\");";

        tuoteClass.method(olio, metodi)
                .returningVoid().taking(String.class).withNiceError(v).invoke("Bratwurst");
    }

    @Test
    @Points("76.1")
    public void addMealAddsTheMealToAttribute() throws Throwable {
        Field ateriatField = null;
        try {
            ateriatField = Menu.class.getDeclaredField("meals");
        } catch (NoSuchFieldException ex) {
            fail("Ensure that class Menu has the attribute private ArrayList<String> meals;");
        }

        Menu lista = new Menu();
        ateriatField.setAccessible(true);

        Method m = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);


        ReflectionUtils.invokeMethod(void.class, m, lista, "eka");

        try {
            ArrayList<String> ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat.size() != 1) {
                fail("calling addMeal should add the meal to the ArrayList meals");
            }

            ReflectionUtils.invokeMethod(void.class, m, lista, "toka");

            if (ateriat.size() != 2) {
                fail("After adding two differently named meals, the ArrayList "
                        + "meals should have size 2.");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Points("76.1")
    public void addMealAddsSameMealOnlyOnce() throws NoSuchFieldException {
        Field ateriatField = Menu.class.getDeclaredField("meals");

        ateriatField.setAccessible(true);

        Menu lista = new Menu();
        Method m = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista, "eka");
            ReflectionUtils.invokeMethod(void.class, m, lista, "eka");
        } catch (Throwable ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<String> ateriat;
        try {
            ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat.size() != 1) {
                fail("Same meal should go to list only once!");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Points("76.2")
    public void hasMethodPrintMeals() throws Throwable {
        String klassName = "Menu";

        String metodi = "printMeals";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("Add the class " + klassName + " the method: public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nThe code that caused the fault m = new Menu(); m.printMeals();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("76.2")
    public void printMealsWorks() {
        String porkkanaSoppa = "Le porkkanaSuppa";
        String kinkkukiusaus = "Kinkkukiusaus";

        Menu lista = new Menu();

        Method addMeal = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);

        try {
            ReflectionUtils.invokeMethod(void.class, addMeal, lista, porkkanaSoppa);
            ReflectionUtils.invokeMethod(void.class, addMeal, lista, kinkkukiusaus);
        } catch (Throwable ex) {
            fail("Ensure that printing of meals works");
        }

        Method m = ReflectionUtils.requireMethod(Menu.class, "printMeals");
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista);
        } catch (Throwable ex) {
            fail("Ensure that printing of meals works when there are more than one meal added");
        }

        String out = stdio.getSysOut();
        assertTrue("Ensure that printMeals prints all the added meals", out.contains(porkkanaSoppa) && out.contains(kinkkukiusaus));
        assertTrue("Ensure that printMeals prints each meal to separate line. Now output is "+out, out.split("\n").length>1);
    }

    @Test
    @Points("76.2")
    public void hasMethodClearMenu() throws Throwable {
        String klassName = "Menu";

        String metodi = "clearMenu";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassName);
        Object olio = tuoteClass.constructor().takingNoParams().invoke();

        assertTrue("Add the class " + klassName + " has method public void " + metodi + "() ", tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nThe code that caused the fault m = new Menu(); m.clearMenu();";

        tuoteClass.method(olio, metodi)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("76.3")
    public void clearingMenuWorks() throws Throwable {
        Field ateriatField = null;

        ateriatField = Menu.class.getDeclaredField("meals");

        ateriatField.setAccessible(true);

        Menu lista = new Menu();
        Method addMeal = ReflectionUtils.requireMethod(Menu.class, "addMeal", String.class);


        ReflectionUtils.invokeMethod(void.class, addMeal, lista, "eka");
        ReflectionUtils.invokeMethod(void.class, addMeal, lista, "toka");

        Method tyhjenna = ReflectionUtils.requireMethod(Menu.class, "clearMenu");

        ReflectionUtils.invokeMethod(void.class, tyhjenna, lista);

        try {
            ArrayList<String> ateriat = (ArrayList<String>) ateriatField.get(lista);
            if (ateriat == null) {
                fail("Do not set ArrayList meals to null when clearing the menu");
            }

            if (!ateriat.isEmpty()) {
                fail("ArrayList meals should have size of zero after a call to clearMenu");
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MenuTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        Method m = ReflectionUtils.requireMethod(Menu.class, "printMeals");
        try {
            ReflectionUtils.invokeMethod(void.class, m, lista);
        } catch (Throwable ex) {
            fail("Ensure that printing meals works");
        }

        String out = stdio.getSysOut();
        out = out.trim();
        if (!out.isEmpty()) {
            fail("After menu has been cleared, call to printMeals should not print anything");
        }
    }
}
