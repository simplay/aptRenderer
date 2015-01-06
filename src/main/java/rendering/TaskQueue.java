package rendering;

import java.util.LinkedList;

/**
 * Thread-safe queue data structure with a very reduced set of methods,
 * containing RenderTask instances. The Queue is implemented as a linked list.
 * Created by simplaY on 03.01.2015.
 */
public class TaskQueue {

    private final LinkedList<RenderTask> container;

    public TaskQueue() {
        container = new LinkedList<RenderTask>();
    }

    /**
     * Add a RenderTask to this queue.
     *
     * @param task supposed to be not null.
     */
    public synchronized void add(RenderTask task) {
        container.add(task);
    }

    /**
     * Number of RenderTask instances stored in this queue.
     *
     * @return size of this Queue.
     */
    public synchronized int size() {
        return container.size();
    }

    /**
     * Retrieves and removes head from queue.
     *
     * @return
     */
    public synchronized RenderTask acquireTask() {
        return container.poll();
    }

}
