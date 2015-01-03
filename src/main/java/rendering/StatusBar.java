package rendering;

/**
 * Thread safe status bar for displaying current rendering progress.
 * Created by simplaY on 31.12.2014.
 */
public class StatusBar {

    // Number of tokens we want to show in the status bar message
    private final int TOKEN_COUNT = 20;

    // Symbol used in order to represent the Status
    private final String TOKEN = "*";

    // Current number of finished tasks.
    private int finishedTasks = 0;

    // total number of tasks
    private final int totalTaskCount;

    // Rounded up fraction between total number of tasks and TOKEN_COUNT
    private final int stepSize;

    /**
     * Constructor of status bar.
     * @param totalTaskCount total number of rendering tasks.
     */
    public StatusBar(int totalTaskCount) {
        this.totalTaskCount = totalTaskCount;
        this.stepSize = (int)Math.ceil(totalTaskCount/TOKEN_COUNT);
    }

    /**
     * Thread safe finished task counter updater
     * Update Progress bar every time there was an multiple of stepSize updates.
     */
    public synchronized void update() {
        finishedTasks++;
        if (finishedTasks % stepSize == 1) {
            System.out.printf(TOKEN);
        }
        if (finishedTasks == totalTaskCount) {
            System.out.println();
        }
    }
}
