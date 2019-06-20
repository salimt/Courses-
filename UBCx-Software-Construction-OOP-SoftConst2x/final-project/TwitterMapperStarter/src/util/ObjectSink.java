package util;

import java.io.*;


public class ObjectSink {
    private File file;
    private ObjectOutputStream outstream;

    public ObjectSink(String filename) {
        try {
            file = new File(filename);
            outstream = new ObjectOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void storeObject(Object o) {
        try {
            outstream.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
