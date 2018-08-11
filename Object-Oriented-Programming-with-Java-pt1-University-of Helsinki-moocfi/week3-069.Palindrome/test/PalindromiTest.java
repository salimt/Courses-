import org.junit.Test;
import static org.junit.Assert.*;
import fi.helsinki.cs.tmc.edutestutils.Points;

@Points("69")
public class PalindromiTest {
    public void t0(String s) {
        assertFalse("String \""+s+"\" is not a palindrome! Your program claimed it is.",
                    Palindromi.palindrome(s));
    }

    public void t1(String s) {
        assertTrue("String \""+s+"\" is a palindrome! Your program claimed it's not.",
                   Palindromi.palindrome(s));
    }

    @Test
    public void test1() {
        t1("");
    }

    @Test
    public void test2() {
        t1("a");
    }

    @Test
    public void test3() {
        t0("ax");
    }

    @Test
    public void test4() {
        t0("axda");
    }

    @Test
    public void test5() {
        t0("abc");
    }

    @Test
    public void test6() {
        t1("aba");
    }

    @Test
    public void test7() {
        t1("abccba");
    }

    @Test
    public void test8() {
        t0("abcc");
    }

    @Test
    public void test9() {
        t0("abca");
    }

    @Test
    public void test10() {
        t1("saippuakauppias");
    }
}
