package rendering;

import base.Scene;

import java.util.LinkedList;

/**
 * TODO: Complete doc: where is image stored
 * Renders an image of a given scene description and its assigned primitives, materials and light sources
 * according to pre-specified setting. The image will be stored ...
 * Created by simplaY on 31.12.2014.
 */
public class Renderer {

    // number of threads used for rendering.
    private final int threadCount = 8;

    // Each rendering task renders a square image block of this size.
    private final int taskSize = 4;

    // TODO remove these hard-coded resolutions and make them parameterizable defined within the scene object
    private int width = 100;
    private int height = 100;

    // user specified rendering scene.
    private final Scene scene;

    // task queue used as a shared resource among all threads.
    private final LinkedList<RenderTask> queue;

    // status bar of the renderign process used as a shared resource among all threads.
    private final StatusBar statusBar;

    public Renderer() {
        // TODO implement scene using enums in order to determine what demo should be run...
        scene = null;//new Scene();
        queue = prepareTasks();
        statusBar = new StatusBar(queue.size());

        System.out.println("Starting rendering...");
        start();
    }

    /**
     * Task partitioning for rendering process. The image is rendered block-wise.
     * Each rendering task is a square block within the image of size taskSize.
     * @return A list of rendering tasks
     */
    private LinkedList<RenderTask> prepareTasks() {
        LinkedList<RenderTask> tasks = new LinkedList<RenderTask>();
        for (int j = 0; j < (int)Math.ceil((double)height / (double)taskSize); j++) {
            for (int i = 0; i < (int)Math.ceil((double)width / (double) taskSize); i++) {
                int left = i*taskSize;
                int right = Math.min((i+1)*taskSize, width);
                int bottom = j*taskSize;
                int top = Math.min((j+1)*taskSize, height);

                tasks.add(new RenderTask(scene, left, right, bottom, top));

            }
        }
        return tasks;
    }

    /**
     * Processes the scene concurrently.
     */
    private void start() {
        LinkedList<Thread> threads = new LinkedList<Thread>();

        for (int k = 0; k < threadCount; k++) {
            threads.add(new Thread(new RenderThread(queue, statusBar), Integer.toString(k)));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finished rendering...");

        System.out.println("Writing image...");
        // TODO write to image buffer
        System.out.println("Finished whole processing...");

    }
}
