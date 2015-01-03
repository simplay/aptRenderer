package rendering;

/**
 * Created by simplaY on 31.12.2014.
 */
public class RenderThread implements Runnable {

    // shared resource that contains all rendering task.
    private final TaskQueue queue;
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

                }
            }
            statusBar.update();
        }
    }

    private void evaluateSample(RenderTask task, float samples[][], int i, int j) {
        for (int k = 0; k < samples.length; k++) {
            // TODO implement me
        }
    }


}
