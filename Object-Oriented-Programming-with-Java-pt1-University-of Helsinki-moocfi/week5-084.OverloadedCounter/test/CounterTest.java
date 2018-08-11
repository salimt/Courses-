import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CounterTest {

    String name = "Counter";

    Class l;

    MethodSignature value = new MethodSignature(Integer.TYPE, "value");
    MethodSignature increase = new MethodSignature(Void.TYPE, "increase");
    MethodSignature decrease = new MethodSignature(Void.TYPE, "decrease");

    MethodSignature increase2 = new MethodSignature(Void.TYPE, "increase", Integer.TYPE);
    MethodSignature decrease2 = new MethodSignature(Void.TYPE, "decrease", Integer.TYPE);    
    
    ConstructorSignature cib = new ConstructorSignature(Integer.TYPE, Boolean.TYPE);
    ConstructorSignature ci = new ConstructorSignature(Integer.TYPE);
    ConstructorSignature cb = new ConstructorSignature(Boolean.TYPE);
    ConstructorSignature c = new ConstructorSignature();

    @Before
    public void haeLuokka() {
        l = Utils.getClass(name);
    }

    @Points("84.1")
    @Test
    public void testaaKonstruointi() {
        cib.invokeIn(l, 10, true);
        cib.invokeIn(l, 2, false);
    }

    @Points("84.1")
    @Test
    public void test1() {

        Object o = cib.invokeIn(l,10,false);
        Integer i = -9000;
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(10, false);\nSystem.out.println(c.value());\n",
                     10,(int)i);

        o = cib.invokeIn(l,2,true);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, true);\nSystem.out.println(c.value());\n",
                     2,(int)i);

    }

    @Points("84.1")
    @Test public void test2() {

        Object o = cib.invokeIn(l,5,false);
        increase.invokeIn(l,o);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(5, false);\nc.increase();\nSystem.out.println(c.value());\n",
                     6,(int)i);

        
        increase.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(5, false);\nc.increase();\nc.increase();\nSystem.out.println(c.value());\n",
                     7,(int)i);


    }

    @Points("84.1")
    @Test public void test3() {

        Object o = cib.invokeIn(l,900,false);
        decrease.invokeIn(l,o);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(900, false);\nc.decrease();\nSystem.out.println(c.value());\n",
                     899,(int)i);

        
        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(900, false);\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                     898,(int)i);
    }

    @Points("84.1")
    @Test public void test4() {
        Object o = cib.invokeIn(l,2,false);
        decrease.invokeIn(l,o);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, false);\nc.decrease();\nSystem.out.println(c.value());\n",
                     1,(int)i);

        
        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, false);\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                     0,(int)i);

        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, false);\nc.decrease();\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                     -1,(int)i);

    }

    @Points("84.1")
    @Test public  void test5() {
        Object o = cib.invokeIn(l,2,true);
        decrease.invokeIn(l,o);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, true);\nc.decrease();\nSystem.out.println(c.value());\n",
                     1,(int)i);

        
        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, true);\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                     0,(int)i);

        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, true);\nc.decrease();\nc.decrease();\nc.decrease();\nSystem.out.println(c.value());\n",
                     0,(int)i);
    }

    @Points("84.1")
    @Test
    public void test6() {

        Object o = ci.invokeIn(l, 11);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(11);\nSystem.out.println(c.value());\n",
                     11,i);

        o = c.invokeIn(l);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter();\nSystem.out.println(c.value());\n",
                     0,i);

        o = cb.invokeIn(l,true);
        decrease.invokeIn(l,o);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(true);\nc.decrease();\nSystem.out.println(c.value());\n",
                     0,i);

    }

    @Points("84.2")
    @Test public void test7() {

        Object o = cib.invokeIn(l,5,false);
        increase2.invokeIn(l,o, 2);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. "
                + "Try code:\nCounter c = new Counter(5, false);\nc.increase(2);\nSystem.out.println(c.value());\n",
                     7,(int)i);

        
        increase2.invokeIn(l,o,4);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\n"
                + "Counter c = new Counter(5, false);\nc.increase(2);\nc.increase(4);\nSystem.out.println(c.value());\n",
                     11,(int)i);
    }
    
    @Points("84.2")
    @Test public void test8() {

        Object o = cib.invokeIn(l,5,false);
        increase2.invokeIn(l,o, -2);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("Negative increase shouldn't change counter's value "
                + "Try code:\nCounter c = new Counter(5, false);\nc.increase(-2);\nSystem.out.println(c.value());\n",
                     5,(int)i);
    }
    
    @Points("84.2")
    @Test public void test9() {

        Object o = cib.invokeIn(l,900,false);
        decrease2.invokeIn(l,o,7);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(900, false);\nc.decrease(7);\nSystem.out.println(c.value());\n",
                     893,(int)i);

        
        decrease2.invokeIn(l,o,3);
        i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(900, false);\nc.decrease(7);\nc.decrease(3);\nSystem.out.println(c.value());\n",
                     890,(int)i);
    }
    
    @Points("84.2")
    @Test public void test10() {

        Object o = cib.invokeIn(l,900,false);
        decrease2.invokeIn(l,o,-55);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("Negative decrease shouldn't change counter's value "
                + " Try code:\nCounter c = new Counter(900, false);\nc.decrease(-55);\nSystem.out.println(c.value());\n",
                     900,(int)i);

    }    
    
    @Points("84.2")
    @Test public  void test11() {
        Object o = cib.invokeIn(l,2,true);
        decrease2.invokeIn(l,o,4);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, true);\nc.decrease(4);\nSystem.out.println(c.value());\n",
                     0,(int)i);

    }
    
    @Points("84.2")
    @Test public void test12() {
        Object o = cib.invokeIn(l,2,false);
        decrease2.invokeIn(l,o,5);
        int i = (Integer) value.invokeIn(l,o);
        assertEquals("You returned wrong value. Try code:\nCounter c = new Counter(2, false);\nc.decrease(5);\nSystem.out.println(c.value());\n",
                     -3,(int)i);
    }    
}
