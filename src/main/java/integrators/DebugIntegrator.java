package integrators;

import base.*;

/**
 * Use this integrator for debugging purposes. For example, you can simply return a white spectrum
 * if the ray hits something, and black otherwise. Any other visualization of data associated
 * with a hit record may be useful.
 * Created by simplaY on 04.01.2015.
 */
public class DebugIntegrator implements Integrator {

    private final Scene scene;

    public DebugIntegrator(Scene scene) {
        this.scene = scene;
    }


    /**
     * Return some value useful for debugging.
     */
    @Override
    public Spectrum integrate(Ray r) {
        HitRecord hitRecord = scene.getIntersectable().intersect(r);
        if (hitRecord != null) {

            // hit point in front of ray origin
            if(hitRecord.getT() > 0.f) {
                return new Spectrum(0.f,1.f,0.f);

            // hit point behind the ray's origin
            } else {
                return new Spectrum(1.f,0.f,0.f);
            }

        // there was no hit
        } else {
            return new Spectrum(0.f,0.f,0.f);
        }
    }

    /**
     * Make sample budget for a pixel. Since this integrator only samples the 2D
     * pixel area itself, the samples are 2D.
     *
     * @param sampler the sampler to be used for generating the samples
     * @param n the desired number of samples
     */
    @Override
    public float[][] makePixelSamples(Sampler sampler, int n) {
        return sampler.makeSamples(n, 2);
    }
}
