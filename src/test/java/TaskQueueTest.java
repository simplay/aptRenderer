import org.junit.Test;
import rendering.RenderTask;
import rendering.TaskQueue;

import static org.junit.Assert.assertEquals;

/**
 * Created by simplaY on 03.01.2015.
 */
public class TaskQueueTest {
    @Test
    public void acquireTaskFromEmptyQueueGivesNullTest() {
        TaskQueue queue = new TaskQueue();
        assertEquals(queue.acquireTask(), null);
    }

    @Test
    public void acquireTaskFromQueueGivesHeadTest() {
        TaskQueue queue = new TaskQueue();
        RenderTask task = new RenderTask(null, 1, 1, 1, 1);
        queue.add(task);
        assertEquals(queue.acquireTask(), task);
    }

    @Test
    public void acquireTaskFromQueueRemovesElementFromQueueTest() {
        TaskQueue queue = new TaskQueue();
        RenderTask task = new RenderTask(null, 1, 1, 1, 1);
        assertEquals(queue.size(), 0);
        queue.add(task);
        assertEquals(queue.size(), 1);
        queue.acquireTask();
        assertEquals(queue.size(), 0);
    }

}
