
import fi.helsinki.cs.tmc.edutestutils.MockInOut;
import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import junit.framework.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TeamAndPlayersTest<_Team, _Player> {

    private String joukkueNimi = "Team";
    private String pelaajaNimi = "Player";
    public String goalsNimi = "goals";
    
    Reflex.ClassRef<Object> klassJ;
    String klassNameJ = "Team";
    Reflex.ClassRef<Object> klassP;
    String klassNameP = "Player";

    @Before
    public void haeLuokka() {
        klassJ = Reflex.reflect(klassNameJ);
    }

    @Points("90.1")
    @Test
    public void test1() {
        assertTrue("Class " + klassNameJ + " should be public, so define it as follows\npublic class " + klassNameJ + " {...\n}", klassJ.isPublic());
    }

    @Points("90.1")
    @Test
    public void test2() {
        saniteettitarkastus("Team", 3, "object variables for name, players and maximum size");
    }

    @Points("90.1")
    @Test
    public void test3() throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klassJ.constructor().taking(String.class).withNiceError();
        assertTrue("Add the class " + klassNameJ + " constructor: public " + klassNameJ + "(String name)", ctor.isPublic());
        ctor.invoke("HIFK");
    }

    public Object luo(String name) throws Throwable {
        Reflex.MethodRef1<Object, Object, String> ctor = klassJ.constructor().taking(String.class).withNiceError();
        return ctor.invoke(name);
    }

    @Test
    @Points("90.1")
    public void test4() throws Throwable {
        String method = "getName";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);

        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public String " + method + "() ", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());


        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\"); "
                + "j.getName();";

        assertEquals("Check code Team j = new Team(\"HIFK\"); "
                + "j.getName();", "HIFK", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luo("Flyers");

        v = "\nCode that caused the failure Team j = new Team(\"Flyers\"); "
                + "j.getName();";

        assertEquals("Check code Team j = new Team(\"Flyers\"); "
                + "j.getName();", "Flyers", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }


    /*
     * 
     */
    @Points("90.2")
    @Test
    public void test5() {
        klassP = Reflex.reflect(klassNameP);
        assertTrue("Class " + klassNameP + " should be public, so define it as follows\npublic class " + klassNameJ + " {...\n}", klassJ.isPublic());
    }

    @Points("90.2")
    @Test
    public void test6() {
        saniteettitarkastus("Player", 2, "object variables for name and number of goals");
    }

    @Points("90.2")
    @Test
    public void test7() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klassP.constructor().taking(String.class, int.class).withNiceError();
        assertTrue("Add the class " + klassNameP + " constructor: public " + klassNameP + "(String name, int goals)", ctor.isPublic());
        ctor.invoke("Arto", 39);
    }

    @Points("90.2")
    @Test
    public void tes8() throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        Reflex.MethodRef1<Object, Object, String> ctor = klassP.constructor().taking(String.class).withNiceError();
        assertTrue("Add the class " + klassNameP + " constructor: public " + klassNameP + "(String name)", ctor.isPublic());
        ctor.invoke("Arto");
    }

    public Object luoP(String name, int maaleja) throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        Reflex.MethodRef2<Object, Object, String, Integer> ctor = klassP.constructor().taking(String.class, int.class).withNiceError();
        return ctor.invoke(name, maaleja);
    }

    public Object luoP(String name) throws Throwable {
        klassP = Reflex.reflect(klassNameP);
        Reflex.MethodRef1<Object, Object, String> ctor = klassP.constructor().taking(String.class).withNiceError();
        return ctor.invoke(name);
    }

    @Points("90.2")
    @Test
    public void test9() throws Throwable {
        klassP = Reflex.reflect(klassNameP);

        String method = "getName";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameP);

        Object olio = luoP("Arto", 39);

        assertTrue("add the class " + klassNameP + " method public String " + method + "() ", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().isPublic());


        String v = "\nCode that caused the failure Player p = new Player(\"Arto\", 39); "
                + "p.getName();";

        assertEquals("Check code Player p = new Player(\"Arto\", 39); "
                + "p.getName();", "Arto", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());

        olio = luoP("Edsger");

        v = "\nCode that caused the failure Player p = new Player(\"Edsger\"); "
                + "p.getName();";

        assertEquals("Check code Player p = new Player(\"Edsger\"); "
                + "p.getName();", "Edsger", tuoteClass.method(olio, method)
                .returning(String.class).takingNoParams().withNiceError(v).invoke());
    }

    @Points("90.2")
    @Test
    public void test10() throws Throwable {
        klassP = Reflex.reflect(klassNameP);

        String method = "goals";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameP);

        Object olio = luoP("Arto", 39);

        assertTrue("add the class " + klassNameP + " method public int " + method + "() ", tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().isPublic());


        String v = "\nCode that caused the failure Player p = new Player(\"Arto\", 39); "
                + "p.goals();";

        int t = tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();

        assertEquals("Check code Player p = new Player(\"Arto\", 39); "
                + "p.goals();", 39, t);

        olio = luoP("Edsger");

        v = "\nCode that caused the failure Player p = new Player(\"Edsger\"); "
                + "p.goals();";

        t = tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();

        assertEquals("Check code Player p = new Player(\"Edsger\"); "
                + "p.goals();", 0, t);
    }

    /*
     * 
     */
    @Test
    @Points("90.2")
    public void test11() {
        Class joukkue = Utils.getClass(pelaajaNimi);
        Constructor constructor = null;
        try {
            constructor = ReflectionUtils.requireConstructor(joukkue, String.class, int.class);
        } catch (Throwable e) {
            fail("Does class " + pelaajaNimi + " have the required constructors?");
        }

        Method getName = null;
        try {
            getName = ReflectionUtils.requireMethod(joukkue, "getName");
        } catch (Throwable e) {
            fail("Does class " + pelaajaNimi + " have method getName?");
        }

        if (getName.getReturnType() != String.class) {
            fail("Does class " + pelaajaNimi + " method getName?");
        }

        Object pelaaja = null;
        String pelaajanNimi = "Antti L";
        int tehdytMaalit = 3;

        try {
            pelaaja = ReflectionUtils.invokeConstructor(constructor, pelaajanNimi, tehdytMaalit);
        } catch (Throwable ex) {
            fail("Does the class Players have the required constructors? ");
        }

        String pelaajanToString = pelaaja.toString();

        Assert.assertTrue("Check that the toString() of Player adds both the name and goals in the String representation",
                pelaajanToString.contains(pelaajanNimi) && pelaajanToString.contains("" + tehdytMaalit));
    }

    /*
     * 
     */
    @Test
    @Points("90.3")
    public void test12() throws Throwable {

        String method = "addPlayer";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);
        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public void " + method + "(Player player) ", tuoteClass.method(olio, method)
                .returningVoid().taking(ReflectionUtils.findClass("Player")).isPublic());

        Reflex.ClassRef<_Team> _TeamRef = Reflex.reflect("Team");

        _Team j = _TeamRef.constructor().taking(String.class).invoke("HIFK");

        Reflex.ClassRef<_Player> _PlayerRef = Reflex.reflect("Player");

        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\"); Player p = new Player(\"Arto\");"
                + " j.addPlayer(p);";

        _Player p = _PlayerRef.constructor().taking(String.class).invoke("Arto");

        Class<_Player> c = _PlayerRef.cls();

        _TeamRef.method(j, method).returningVoid().taking(c).withNiceError(v).invoke(p);

    }

    @Test
    @Points("90.3")
    public void test13() throws Throwable {

        String method = "printPlayers";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);
        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public void " + method + "() ", tuoteClass.method(olio, method)
                .returningVoid().takingNoParams().isPublic());

        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\");"
                + " j.printPlayers();";

        tuoteClass.method(olio, method)
                .returningVoid().takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("90.3")
    public void test14() {
        Class joukkueClass = Utils.getClass(joukkueNimi);
        Constructor constructor = null;
        try {
            constructor = ReflectionUtils.requireConstructor(joukkueClass, String.class);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the required constructors?");
        }

        Method getName = null;
        try {
            getName = ReflectionUtils.requireMethod(joukkueClass, "getName");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method getName");
        }

        if (getName.getReturnType() != String.class) {
            fail("Does class " + joukkueNimi + " method getName");
        }

        Object joukkue = null;
        String joukkueenNimi = "Jokivirran tsemppi";

        try {
            joukkue = ReflectionUtils.invokeConstructor(constructor, joukkueenNimi);
        } catch (Throwable ex) {
            fail("Does the Team have the required constructor?");
        }

        String saatuNimi = null;
        try {
            saatuNimi = ReflectionUtils.invokeMethod(String.class, getName, joukkue);
        } catch (Throwable ex) {
            fail("Check the method getName of the class Team");
        }

        Assert.assertEquals("The method getName of class Team does not return the name given in constructor!", joukkueenNimi, saatuNimi);

        Method addPlayer = null;
        Class pelaajaClass = Utils.getClass("Player");
        try {
            addPlayer = ReflectionUtils.requireMethod(joukkueClass, "addPlayer", pelaajaClass);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the method addPlayer?");
        }

        Constructor pelaajaConstructor = null;
        try {
            pelaajaConstructor = ReflectionUtils.requireConstructor(pelaajaClass, String.class);
        } catch (Throwable e) {
            fail("Does class Player have the required constructors? ");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Matti"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Teppo"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Aira Samulin"));
        } catch (Throwable t) {
            fail("Check the method addPlayer of class Team");
        }

        MockInOut mio = new MockInOut("");

        Method printPlayers = null;
        try {
            printPlayers = ReflectionUtils.requireMethod(joukkueClass, "printPlayers");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method printPlayers.");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, printPlayers, joukkue);
        } catch (Throwable t) {
            fail("Tulostaajan method printPlayers pelaajien tiedot.");
        }

        String output = mio.getOutput().toLowerCase();
        Assert.assertTrue("Method printPlayers should print info of the players added to the team. \n"
                + "Check the following code \n"
                + "Team j = new Team(\"RAGE\"); Player p = new Player(\"Jaakko\"); "
                + "j.addPlayer(p); j.printPlayers();",
                output.contains("matti") && output.contains("teppo") && output.contains("aira"));
    }

    /*
     * 
     */
    @Test
    @Points("90.4")
    public void test15() throws Throwable {

        String method = "setMaxSize";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);
        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public void " + method + "(int maxSize) ", tuoteClass.method(olio, method)
                .returningVoid().taking(int.class).isPublic());

        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\");"
                + " j.setMaxSize(22);";

        tuoteClass.method(olio, method)
                .returningVoid().taking(int.class).withNiceError(v).invoke(22);
    }

    @Test
    @Points("90.4")
    public void test16() throws Throwable {

        String method = "size";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);
        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public int " + method + "() ", tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\");"
                + " j.size();";

        tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("90.4")
    public void test17() {
        Class joukkueClass = Utils.getClass(joukkueNimi);
        Constructor constructor = null;
        try {
            constructor = ReflectionUtils.requireConstructor(joukkueClass, String.class);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the required constructor?");
        }

        Method sizeMethod = null;
        try {
            sizeMethod = ReflectionUtils.requireMethod(joukkueClass, "size");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have method size?");
        }

        if (sizeMethod.getReturnType() != int.class) {
            fail("Does class " + joukkueNimi + " have correctly implemented method size?");
        }

        Object joukkue = null;
        String joukkueenNimi = "Jokivirran tsemppi";

        try {
            joukkue = ReflectionUtils.invokeConstructor(constructor, joukkueenNimi);
        } catch (Throwable ex) {
            fail("Does the Team have the required constructor?");
        }

        int saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
            fail("Does class " + joukkueNimi + " have correctly implemented method size?");
        }

        Assert.assertEquals("A new team should have size 0", 0, saatuKoko);

        Method addPlayer = null;
        Class pelaajaClass = Utils.getClass("Player");
        try {
            addPlayer = ReflectionUtils.requireMethod(joukkueClass, "addPlayer", pelaajaClass);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have method addPlayer?");
        }

        Constructor pelaajaConstructor = null;
        try {
            pelaajaConstructor = ReflectionUtils.requireConstructor(pelaajaClass, String.class);
        } catch (Throwable e) {
            fail("Does class Player have the required constructors? ");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Matti"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Teppo"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Aira Samulin"));
        } catch (Throwable t) {
            fail("Check the method addPlayer of class Team");
        }

        saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
             fail("Does class " + joukkueNimi + " have correctly implemented method size?");
        }

        Assert.assertEquals("When team is created as follows:\n"
                + "Team j = new Team(\"SaPKo\");"
                + "after adding 3 players, the size of the team should be 3\n"
                + "\n", 3, saatuKoko);


    }

    /*
     * 
     */
    @Test
    @Points("90.4")
    public void test18() {
        Class joukkueClass = Utils.getClass(joukkueNimi);
        Constructor constructor = null;
        try {
            constructor = ReflectionUtils.requireConstructor(joukkueClass, String.class);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the required constructors?");
        }

        Method sizeMethod = null;
        try {
            sizeMethod = ReflectionUtils.requireMethod(joukkueClass, "size");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the method size?");
        }

        if (sizeMethod.getReturnType() != int.class) {
            fail("Does class " + joukkueNimi + " have correctly implemented method size?");
        }

        Object joukkue = null;
        String joukkueenNimi = "Jokivirran tsemppi";

        try {
            joukkue = ReflectionUtils.invokeConstructor(constructor, joukkueenNimi);
        } catch (Throwable ex) {
            fail("Does the Team have the required constructor?");
        }

        int saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
            fail("Does class " + joukkueNimi + " have correctly implemented method size?");
        }

        Assert.assertEquals("Size of an empty team should be", 0, saatuKoko);

        Method addPlayer = null;
        Class pelaajaClass = Utils.getClass("Player");
        try {
            addPlayer = ReflectionUtils.requireMethod(joukkueClass, "addPlayer", pelaajaClass);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method addPlayer");
        }

        Constructor pelaajaConstructor = null;
        try {
            pelaajaConstructor = ReflectionUtils.requireConstructor(pelaajaClass, String.class);
        } catch (Throwable e) {
            fail("Does class Player have the required constructors? ");
        }

        Method setMaxSize = null;
        try {
            setMaxSize = ReflectionUtils.requireMethod(joukkueClass, "setMaxSize", int.class);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have method setMaxSize?");
        }

        if (setMaxSize.getReturnType() != void.class) {
            fail("Does class " + joukkueNimi + " have method setMaxSize?");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, setMaxSize, joukkue, 1);
        } catch (Throwable ex) {
            fail("Does class " + joukkueNimi + " have method setMaxSize?");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Matti"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Teppo"));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Aira Samulin"));
        } catch (Throwable t) {
            fail("Check the method addPlayer of class Team");
        }

        saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
            fail("Calling method size failed.");
        }

        Assert.assertEquals("If team maximum size is 1, "
                + "the size of the team should be 1 even after adding 3 players.", 1, saatuKoko);
    }

    /*
     * 
     */
    @Test
    @Points("90.5")
    public void test19() throws Throwable {

        String method = "goals";

        Reflex.ClassRef<Object> tuoteClass = Reflex.reflect(klassNameJ);
        Object olio = luo("HIFK");

        assertTrue("add the class " + klassNameJ + " method public int " + method + "() ", tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().isPublic());

        String v = "\nCode that caused the failure Team j = new Team(\"HIFK\");"
                + " j.goals();";

        tuoteClass.method(olio, method)
                .returning(int.class).takingNoParams().withNiceError(v).invoke();
    }

    @Test
    @Points("90.5")
    public void test20() {
        Class joukkueClass = Utils.getClass(joukkueNimi);
        Constructor constructor = null;
        try {
            constructor = ReflectionUtils.requireConstructor(joukkueClass, String.class);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " have the required constructors?");
        }

        Method sizeMethod = null;
        try {
            sizeMethod = ReflectionUtils.requireMethod(joukkueClass, "size");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method size?");
        }

        if (sizeMethod.getReturnType() != int.class) {
            fail("Does class " + joukkueNimi + " method size?");
        }

        Object joukkue = null;
        String joukkueenNimi = "Jokivirran tsemppi";

        try {
            joukkue = ReflectionUtils.invokeConstructor(constructor, joukkueenNimi);
        } catch (Throwable ex) {
            fail("Does the Team have the required constructor?");
        }

        int saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
            fail("Does class " + joukkueNimi + " method size?");
        }

        Assert.assertEquals("Size of an empty team should be", 0, saatuKoko);

        Method addPlayer = null;
        Class pelaajaClass = Utils.getClass("Player");
        try {
            addPlayer = ReflectionUtils.requireMethod(joukkueClass, "addPlayer", pelaajaClass);
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method addPlayer?");
        }

        Constructor pelaajaConstructor = null;
        try {
            pelaajaConstructor = ReflectionUtils.requireConstructor(pelaajaClass, String.class, int.class);
        } catch (Throwable e) {
            fail("Does class Player have the required constructors? ");
        }

        try {
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Matti", 3));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Teppo", 4));
            ReflectionUtils.invokeMethod(void.class, addPlayer, joukkue, ReflectionUtils.invokeConstructor(pelaajaConstructor, "Aira Samulin", 3));
        } catch (Throwable t) {
            fail("Check the method addPlayer of class Team");
        }

        saatuKoko = -1;
        try {
            saatuKoko = ReflectionUtils.invokeMethod(int.class, sizeMethod, joukkue);
        } catch (Throwable ex) {
            fail("Calling method size failed");
        }


        Method maalienMaara = null;
        try {
            maalienMaara = ReflectionUtils.requireMethod(joukkueClass, "goals");
        } catch (Throwable e) {
            fail("Does class " + joukkueNimi + " method goals?");
        }

        int maaleja = -1;

        try {
            maaleja = ReflectionUtils.invokeMethod(int.class, maalienMaara, joukkue);
        } catch (Throwable t) {
            fail("Does class " + joukkueNimi + " method goals?");
        }


        Assert.assertEquals("Check that method goals of the class Team works as expected", 10, maaleja);
    }


    private void saniteettitarkastus(String luokanNimi, int muuttujia, String m) throws SecurityException {
        Field[] kentat = ReflectionUtils.findClass(luokanNimi).getDeclaredFields();

        for (Field field : kentat) {        
            assertFalse("does not need \"static variables\", remove " + kentta(field.toString(), luokanNimi), field.toString().contains("static") && !field.toString().contains("final"));
            assertTrue("all the object variables should be private, please change " + kentta(field.toString(), luokanNimi), field.toString().contains("private"));
        }


        if (kentat.length > 1) {
            int var = 0;
            for (Field field : kentat) {
                if (!field.toString().contains("final")) {
                    var++;
                }
            }
            assertTrue("Class " + luokanNimi + " only needs " + m + ", remove the rest", var <= muuttujia);
        }

    }     
    
    private String kentta(String toString, String klassName) {
        return toString.replace(klassName + ".", "").replace("java.lang.", "");
    }
}
