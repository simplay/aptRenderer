package rendering;

import base.Scene;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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

    // width of image to render.
    private final int width;

    // height of image to render.
    private final int height;

    // user specified rendering scene.
    private final Scene scene;

    // task queue used as a shared resource among all threads.
    private final TaskQueue queue;

    // status bar of the rendering process used as a shared resource among all threads.
    private final StatusBar statusBar;

    // measures the time in milliseconds to process all render tasks.
    private final Timer renderTaskTimer;

    // measures the total time in milliseconds spent in the renderer.
    private final Timer totalTimer;

    public Renderer(Scene scene) {
        totalTimer = new Timer();
        renderTaskTimer = new Timer();

        this.scene = scene;
        this.width = scene.getWidth();
        this.height = scene.getHeight();

        queue = prepareTasks();
        statusBar = new StatusBar(queue.size());

        System.out.println("Starting rendering...");
        this.start();
    }

    /**
     * Task partitioning for rendering process. The image is rendered block-wise.
     * Each rendering task is a square block within the image of size taskSize.
     *
     * @return A list of rendering tasks
     */
    private TaskQueue prepareTasks() {
        TaskQueue tasks = new TaskQueue();
        for (int j = 0; j < (int) Math.ceil((double) height / (double) taskSize); j++) {
            for (int i = 0; i < (int) Math.ceil((double) width / (double) taskSize); i++) {
                int left = i * taskSize;
                int right = Math.min((i + 1) * taskSize, width);
                int bottom = j * taskSize;
                int top = Math.min((j + 1) * taskSize, height);

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

        renderTaskTimer.reset();
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
        long renderTaskTime = renderTaskTimer.timeElapsed();

        BufferedImage frame = scene.getTonemapper().process(scene.getFilm());
        try {
            ImageIO.write(frame, "png", new File(scene.getFilePathName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        long renderTime = totalTimer.timeElapsed();

        System.out.println("finished rendering...");
        System.out.println("Writing image...");
        System.out.println("Finished whole processing...");
        System.out.println("Total Time: " + renderTime + " ms ");
        System.out.println("Render Task Time: " + renderTaskTime + " ms ");
    }

}
