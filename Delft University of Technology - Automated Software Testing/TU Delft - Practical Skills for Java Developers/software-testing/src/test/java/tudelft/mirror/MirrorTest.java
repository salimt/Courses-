package tudelft.mirror;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MirrorTest {

    private Mirror mirror;

    @BeforeEach
    public void initialize() {
        this.mirror = new Mirror();
    }

    @Test
    public void test01() {
        String result = mirror.mirrorEnds("abXYZba");
        Assertions.assertEquals("ab", result);
    }

    @Test
    public void test02() {
        String result = mirror.mirrorEnds("abca") ;
        Assertions.assertEquals("a", result);
    }

    @Test
    public void test03() {
        String result = mirror.mirrorEnds("aba") ;
        Assertions.assertEquals("aba", result);
    }

    @Test
    public void test04() {
        String result = mirror.mirrorEnds("aaa") ;
        Assertions.assertEquals("aaa", result);
    }

    @Test
    public void test05() {
        String result = mirror.mirrorEnds("aa") ;
        Assertions.assertEquals("aa", result);
    }

    @Test
    public void test06() {
        String result = mirror.mirrorEnds("11 1") ;
        Assertions.assertEquals("1", result);
    }

    @Test
    public void test07() {
        String result = mirror.mirrorEnds("aaaa") ;
        Assertions.assertEquals("aaaa", result);
    }


}
