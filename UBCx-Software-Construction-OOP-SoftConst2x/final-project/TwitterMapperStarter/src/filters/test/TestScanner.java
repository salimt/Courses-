package filters.test;

import filters.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestScanner {
    @Test
    public void testBasic() {
        Scanner x = new Scanner("trump");
        assertTrue(x.peek().equals("trump"));
        assertTrue(x.advance() == null);
    }

    @Test
    public void testAnd() {
        Scanner x = new Scanner("trump and evil");
        assertTrue(x.peek().equals("trump"));
        assertTrue(x.advance().equals("and"));
        assertTrue(x.peek().equals("and"));
        assertTrue(x.advance().equals("evil"));
        assertTrue(x.peek().equals("evil"));
        assertTrue(x.advance() == null);
    }

    @Test
    public void testAll() {
        String expected[] = { "trump", "and", "(", "evil",
                "or", "not", "(", "good", ")", ")" };
        runTest("trump and (evil or not (good))", expected);
    }

    @Test
    public void testOr() {
        String expected[] = { "trump", "or", "evil" };
        runTest("trump or evil", expected);
    }

    private void runTest(String input, String[] expected) {
        Scanner x = new Scanner(input);
        boolean first = true;
        for (String token : expected) {
            if (first) {
                first = false;
            } else {
                assertTrue(x.advance().equals(token));
            }
            assertTrue(x.peek().equals(token));
        }
        assertTrue(x.advance() == null);
    }
}
