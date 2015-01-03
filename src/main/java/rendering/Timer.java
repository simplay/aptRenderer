package rendering;

import java.util.Date;

/**
 * Used to measure rendering timings or other methods run-times.
 * Created by simplaY on 03.01.2015.
 */
public class Timer {
    private long start;

    public Timer() {
        this.reset();
    }

    /**
     * Get Elapsed time in milliseconds since last timer reset.
     * @return elapsed time in milliseconds.
     */
    public long timeElapsed() {
        return this.now()-start;
    }

    /**
     * Set timer to current system time in milliseconds.
     */
    public void reset() {
        start = this.now();
    }

    /**
     * Get current system time in milliseconds.
     * @return current system time in milliseconds.
     */
    protected long now() {
        return new Date().getTime();
    }
}
