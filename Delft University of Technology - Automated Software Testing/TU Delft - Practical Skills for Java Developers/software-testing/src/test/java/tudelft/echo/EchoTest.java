package tudelft.echo;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.mockito.Mock;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class EchoTest {

    @TestFactory
    Iterable<DynamicTest> echoTestCases() {
        return Arrays.asList(
                testEchoOutput("\n"),
                testEchoOutput("quick\n",  "quick"),
                testEchoOutput("quick brown fox\n",  "quick", "brown", "fox"),
                testEchoOutput("quick  brown fox\n",  "quick  brown", "fox"),

                testEchoOutput("", "-n" ),
                testEchoOutput("quick",  "-n", "quick" ),
                testEchoOutput("quick brown fox",  "-n", "quick", "brown", "fox"),
                testEchoOutput("quick -n brown\n",  "quick", "-n", "brown"),

                testEchoOutput("", "\\c" ),
                testEchoOutput("quick",  "quick\\c" ),
                testEchoOutput("quick brown",  "quick", "brown\\c" ),
                testEchoOutput("quick\\c brown\n",  "quick\\c", "brown" ),

                testEchoOutput("quick",  "-n", "quick\\c" ),
                testEchoOutput("quick\\d\n",  "quick\\d" ),
                testEchoOutput("-nquick\n",  "-nquick")

        );
    }

    private DynamicTest testEchoOutput(String expected, String... operands) {
        return DynamicTest.dynamicTest(
                "echo " + String.join(" ", operands),
                () -> {
                    Echo echo = new Echo();
                    echo.process(Arrays.asList(operands));
                    assertEquals(expected, echo.output());
                });
    }

    @Test
    void testOutput() {
        SystemWrapper mockedSystem = mock(SystemWrapper.class);
        Echo echo = new Echo().withSystem(mockedSystem);
        echo.echo(new String[] {"hello", "world"});
        verify(mockedSystem).exit(0);
        verify(mockedSystem).print("hello world\n");
    }
}
