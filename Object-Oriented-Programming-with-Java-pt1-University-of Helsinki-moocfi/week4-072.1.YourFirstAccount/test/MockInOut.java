import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;

public class MockInOut {

    private PrintStream orig;
    private InputStream irig;
    private ByteArrayOutputStream os;
    private ByteArrayInputStream is;

    public MockInOut(String input) {
        orig = System.out;
        irig = System.in;

        os = new ByteArrayOutputStream();
        System.setOut(new PrintStream(os));

        is = new ByteArrayInputStream(input.getBytes());
        System.setIn(is);
    }

    public InputStream getInputStream() {
        return is;
    }        

    public String getOutput() {
        if (os != null)
            return os.toString();
        else
            throw new Error("getOutput on closed MockInOut!");
    }

    public void close() {
        os = null;
        is = null;
        System.setOut(orig);
        System.setIn(irig);
    }
}

