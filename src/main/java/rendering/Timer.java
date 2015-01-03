package rendering;

import java.util.Date;

/**
 * Used to measure rendering timings or other methods runtimes.
 * Created by simplaY on 03.01.2015.
 */
public class Timer {
    private long start;

    public Timer() {
        this.reset();
    }

    /**
     * Get Elapsed time in miliseconds since last timer reset.
     * @return elapsed time in miliseconds.
     */
    public long timeElapsed() {
        return this.now()-start;
    }

    /**
     * Set timer to current system time in miliseconds.
     */
    public void reset() {
        start = this.now();
    }

    /**
     * Get current system time in miliseconds.
     * @return current system time in miliseconds.
     */
    protected long now() {
        return new Date().getTime();
    }
}
