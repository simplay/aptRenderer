package rendering;

/**
 * Each render thread tries to acquire render tasks and
 * then computes the pixel values of pixel locations corresponding to the task.
 * It runs as long as there are tasks available to process.
 * Created by simplaY on 31.12.2014.
 */
public class RenderThread implements Runnable {

    // shared resource that contains all rendering task.
    private final TaskQueue queue;

    // shared resource for updating rendering progress.
    private final StatusBar statusBar;

    public RenderThread(TaskQueue queue, StatusBar statusBar) {
        this.queue = queue;
        this.statusBar = statusBar;

    }

    @Override
    public void run() {
        while(true) {
            RenderTask task = queue.acquireTask();
            if (task == null) break;

            for (int j = task.getBottom(); j < task.getTop(); j++) {
                for (int i = task.getLeft(); i < task.getRight(); i++) {
                    this.runDummyTask();
                }
            }
            statusBar.update();
        }
    }

    // TODO remove me.
    private void runDummyTask() {
        try {
            Thread.currentThread().sleep((long) (5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
