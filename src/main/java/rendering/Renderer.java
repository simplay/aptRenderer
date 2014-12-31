package rendering;

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
    private final LinkedList<RenderTask> queue;

    public Renderer() {
        // TODO init
        queue = new LinkedList<RenderTask>();
        System.out.println("Starting rendering...");
        start();
    }

    /**
     * Processes the scene concurrently.
     */
    private void start() {
        LinkedList<Thread> threads = new LinkedList<Thread>();

        for (int k = 0; k < threadCount; k++) {
            threads.add(new Thread(new RenderThread(queue), Integer.toString(k)));
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
