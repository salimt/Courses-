package model;

/**
 * The curtain across the front of the stage
 */
public class Curtain {

    private boolean closed;

    public Curtain() {
        // Nothing to do
    }

    public void close() {
        setClosed(true);
    }

    public void open() {
        setClosed(false);
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean isClosed() {
        return closed;
    }
}
