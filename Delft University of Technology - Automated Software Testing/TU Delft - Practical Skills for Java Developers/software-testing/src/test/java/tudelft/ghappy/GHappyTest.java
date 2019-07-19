package tudelft.ghappy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GHappyTest {

    private GHappy gHappy;

    @BeforeEach
    public void initialize() {
        this.gHappy = new GHappy();
    }

    @Test
    public void test01() {
        boolean result = gHappy.gHappy("xxggxx");
        Assertions.assertTrue(result);
    }

    @Test
    public void test02() {
        boolean result = gHappy.gHappy("xxgxx");
        Assertions.assertFalse(result);
    }

    @Test
    public void test03() {
        boolean result = gHappy.gHappy("xxggyygxx");
        Assertions.assertFalse(result);
    }

    @Test
    public void test04() {
        boolean result = gHappy.gHappy("ggx");
        Assertions.assertTrue(result);
    }

}
