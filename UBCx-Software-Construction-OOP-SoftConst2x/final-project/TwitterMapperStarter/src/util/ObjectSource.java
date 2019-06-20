package util;

import java.io.*;

/**
 * Read objects from a file
 */
public class ObjectSource {
    private File file;
    private ObjectInputStream instream;

    public ObjectSource(String filename)  {
        file = new File(filename);
        try {
            instream = new ObjectInputStream(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObject() {
        Object o = null;
        try {
            o = instream.readObject();
        } catch (EOFException e) {
            // Do nothing, EOF is expected to happen eventually
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return o;
    }

    public void close() {
        try {
            instream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
