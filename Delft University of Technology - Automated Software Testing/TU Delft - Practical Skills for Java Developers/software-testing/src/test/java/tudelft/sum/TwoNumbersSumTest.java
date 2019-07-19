package tudelft.sum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TwoNumbersSumTest {

    private TwoNumbersSum sum;

    @BeforeEach
    public void initialize() {
        this.sum = new TwoNumbersSum();
    }

    @Test
    public void test01() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(1); list1.add(2);
        ArrayList list2 = new ArrayList();
        list2.add(5); list2.add(7);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(6); res.add(9);
        Assertions.assertEquals(res, result);
    }

    @Test
    public void test02() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(1); list1.add(0);
        ArrayList list2 = new ArrayList();
        list2.add(5); list2.add(1);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(6); res.add(1);
        Assertions.assertEquals(res, result);
    }

    @Test
    public void test03() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(3); list1.add(4); list1.add(2);
        ArrayList list2 = new ArrayList();
        list2.add(4); list2.add(6); list2.add(5);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(8); res.add(0); res.add(7);
        Assertions.assertEquals(res, result);
    }

    @Test
    public void test04() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(7); list1.add(4); list1.add(2);
        ArrayList list2 = new ArrayList();
        list2.add(3); list2.add(6); list2.add(8);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(1); res.add(1); res.add(1); res.add(0);
        Assertions.assertEquals(res, result);
    }

    @Test
    public void test05() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(9); list1.add(9); list1.add(3);
        ArrayList list2 = new ArrayList();
        list2.add(9); list2.add(9); list2.add(7);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(1); res.add(9); res.add(9); res.add(0);
        Assertions.assertEquals(res, result);
    }

    @Test
    public void test06() {
        ArrayList<Integer> list1 = new ArrayList();
        list1.add(1); list1.add(9); list1.add(9); list1.add(6);
        ArrayList list2 = new ArrayList();
        list2.add(9); list2.add(9); list2.add(7);
        ArrayList<Integer> result = sum.addTwoNumbers(list1, list2);
        ArrayList<Integer> res = new ArrayList <>();
        res.add(2); res.add(9); res.add(9); res.add(3);
        Assertions.assertEquals(res, result);
    }


}
