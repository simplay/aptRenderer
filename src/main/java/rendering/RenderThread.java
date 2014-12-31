package rendering;

import java.util.LinkedList;

/**
 * Created by simplaY on 31.12.2014.
 */
public class RenderThread implements Runnable {

    // shared resource that contains all rendering task.
    private final LinkedList<RenderTask> queue;

    public RenderThread(LinkedList<RenderTask> queue) {
        this.queue = queue;

    }

    @Override
    public void run() {
        while(true) {
            RenderTask task = acquireTask();
            if (task == null) break;

            for (int j = task.getBottom(); j < task.getTop(); j++) {
                for (int i = task.getLeft(); i < task.getRight(); i++) {

                }
            }
        }
    }

    private void evaluateSample(RenderTask task, float samples[][], int i, int j) {
        for (int k = 0; k < samples.length; k++) {
            // TODO implement me
        }
    }

    private RenderTask acquireTask() {
        synchronized (queue) {
            if (queue.size() == 0) return null;
            return queue.poll();
        }
    }
}
