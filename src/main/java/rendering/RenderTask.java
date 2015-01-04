package rendering;


import base.*;

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
    private final Sampler sampler;

    public RenderTask(Scene scene, int left, int right, int bottom, int top) {
        this.scene = scene;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;

        // The render task has its own sampler and integrator. This way threads don't
        // compete for access to a shared sampler/integrator, and thread contention
        // can be reduced.
        this.integrator = scene.getIntegratorFactory().make(scene);
        this.sampler = scene.getSamplerFactory().make();;
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

    public void evaluateSampleAt(int i, int j) {
        float[][] samples = integrator.makePixelSamples(sampler, scene.getSpp());
        for (int k = 0; k < samples.length; k++) {
            Ray primaryRay = scene.getCamera().makeWorldSpaceRay(i, j, samples[k]);

            // Evaluate ray
            Spectrum s = integrator.integrate(primaryRay);

            // Write to film
            scene.getFilm().addSample((double)i+(double)samples[k][0], (double)j+(double)samples[k][1], s);
        }
    }

}
