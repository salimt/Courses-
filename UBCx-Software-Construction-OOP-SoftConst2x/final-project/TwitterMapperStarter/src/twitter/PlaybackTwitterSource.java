package twitter;

import twitter4j.Status;
import util.ObjectSource;

/**
 * A Twitter source that plays back a recorded stream of tweets.
 *
 * It ignores the set of terms provided except it uses the first call to setFilterTerms
 * as a signal to begin playback of the recorded stream of tweets.
 *
 * Implements Observable - each tweet is signalled to all observers
 */
public class PlaybackTwitterSource extends TwitterSource {
    // The speedup to apply to the recorded stream of tweets; 2 means play at twice the rate
    // at which the tweets were recorded
    private final double speedup;
    private ObjectSource source = new ObjectSource("TwitterMapperStarter/data/TwitterCapture.jobj");
    private boolean threadStarted = false;

    public PlaybackTwitterSource(double speedup) {
        this.speedup = speedup;
    }

    private void startThread() {
        if (threadStarted) return;
        threadStarted = true;
        Thread t = new Thread() {
            long initialDelay = 1000;
            long playbackStartTime = System.currentTimeMillis() + initialDelay;
            long recordStartTime = 0;

            public void run() {
                long now;
                while (true) {
                    Object timeo = source.readObject();
                    if (timeo == null) break;
                    Object statuso = source.readObject();
                    if (statuso == null) break;
                    long statusTime = (Long)timeo;
                    if (recordStartTime == 0) recordStartTime = statusTime;
                    Status status = (Status) statuso;
                    long playbackTime = computePlaybackTime(statusTime);
                    while ((now = System.currentTimeMillis()) < playbackTime) {
                        pause(playbackTime - now);
                    }
                    if (status.getPlace() != null) {
                        handleTweet(status);
                    }
                }
            }

            private long computePlaybackTime(long statusTime) {
                long statusDelta = statusTime - recordStartTime;
                long targetDelta = Math.round(statusDelta / speedup);
                long targetTime = playbackStartTime + targetDelta;
                return targetTime;
            }

            private void pause(long millis) {
                try {
                    Thread.sleep(millis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    /**
     * The playback source merely starts the playback thread, it it hasn't been started already
     */
    protected void sync() {
        System.out.println("Starting playback thread with " + terms);

        startThread();
    }
}
