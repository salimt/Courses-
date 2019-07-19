package tudelft.chocolate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ChocolateBagsTest2 {
    @ParameterizedTest(name = "small={0}, big={1}, total={2}, result={3}")
    @CsvSource({
            // The total is higher than the amount of small and big bars.
            "1,1,5,0", "1,1,6,1", "1,1,7,-1", "1,1,8,-1",
            // No need for small bars.
            "4,0,10,-1", "4,1,10,-1", "5,2,10,0", "5,3,10,0",
            // Need for big and small bars.
            "0,3,17,-1", "1,3,17,-1", "2,3,17,2", "3,3,17,2",
            "0,3,12,-1", "1,3,12,-1", "2,3,12,2", "3,3,12,2",
            // Only small bars.
            "4,2,3,3", "3,2,3,3", "2,2,3,-1", "1,2,3,-1"
    })
    public void testAlgorithm(int small, int big, int total, int expectedResult) {
        int result = new ChocolateBags().calculate(small, big, total);
        Assertions.assertEquals(expectedResult, result);
    }

}
