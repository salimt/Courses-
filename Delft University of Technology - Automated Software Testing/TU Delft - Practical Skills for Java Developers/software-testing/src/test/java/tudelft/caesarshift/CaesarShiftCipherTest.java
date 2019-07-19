package tudelft.caesarshift;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CaesarShiftCipherTest {

    private CaesarShiftCipher ceaser;

    @BeforeEach
    public void initialize() {
        this.ceaser = new CaesarShiftCipher();
    }

    @Test
    public void test01() {
        String result = ceaser.CaesarShiftCipher("abc", 1);
        Assertions.assertEquals("bcd", result);
    }

    @Test
    public void test02() {
        String result = ceaser.CaesarShiftCipher("zz", 1);
        Assertions.assertEquals("aa", result);
    }

}
