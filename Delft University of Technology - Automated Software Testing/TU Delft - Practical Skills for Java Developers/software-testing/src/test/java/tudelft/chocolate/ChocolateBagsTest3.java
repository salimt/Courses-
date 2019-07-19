package tudelft.chocolate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ChocolateBagsTest3 {
    @ParameterizedTest(name = "{0}: ({1}, {2}, {3}) = {4}")
    @CsvSource({
            // The total is higher than the amount of small and big bars.
            "'total-higher',1,1,5,0", "'total-higher',1,1,6,1", "'total-higher',1,1,7,-1", "'total-higher',1,1,8,-1",
            // No need for small bars.
            "'big-bars-only',4,0,10,-1", "'big-bars-only',4,1,10,-1", "'big-bars-only',5,2,10,0", "'big-bars-only',5,3,10,0",
            // Need for big and small bars.
            "'big-and-small',0,3,17,-1", "'big-and-small',1,3,17,-1", "'big-and-small',2,3,17,2", "'big-and-small',3,3,17,2",
            "'big-and-small',0,3,12,-1", "'big-and-small',1,3,12,-1", "'big-and-small',2,3,12,2", "'big-and-small',3,3,12,2",
            // Only small bars.
            "'small-bars-only',4,2,3,3", "'small-bars-only',3,2,3,3", "'small-bars-only',2,2,3,-1", "'small-bars-only',1,2,3,-1"
    })
    public void testAlgorithm(String partition, int small, int big, int total, int expectedResult) {
        int result = new ChocolateBags().calculate(small, big, total);
        Assertions.assertEquals(expectedResult, result);
    }
}
