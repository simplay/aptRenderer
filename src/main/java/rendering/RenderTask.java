package rendering;

/**
 * Created by simplaY on 31.12.2014.
 */
public class RenderTask {
    private final int left;
    private final int right;
    private final int top;
    private final int bottom;
    private final Integrator integrator;
    private final Scene scene;
    private Sampler sampler;

    public RenderTask(Scene scene, int left, int right, int bottom, int top) {
        this.scene = scene;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        // TODO assign integrator and scene...
        this.integrator = null;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public Integrator getIntegrator() {
        return integrator;
    }

    public Scene getScene() {
        return scene;
    }

    public Sampler getSampler() {
        return sampler;
    }


}
