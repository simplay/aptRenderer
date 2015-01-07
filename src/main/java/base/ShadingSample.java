package base;

import javax.vecmath.Vector3f;

/**
 * Stores information about a shading sample.
 * Created by simplaY on 03.01.2015.
 */
public class ShadingSample {

    /**
     * The BRDF value.
     */
    private Spectrum brdf;

    /**
     * The emission value.
     */
    private Spectrum emission;

    /**
     * The sampled direction.
     */
    private Vector3f w;

    /**
     * Tells the integrator whether this is a specular sample. In this case,
     * a cosine factor in the specular BRDF should be omitted in the returned
     * BRDF value, and the integrator should act accordingly.
     */
    private boolean isSpecular;

    /**
     * The (directional) probability density of the sample
     */
    private float p;

    private boolean isSampleable = true;

    public ShadingSample(Spectrum brdf, Spectrum emission, Vector3f w, boolean isSpecular, float p) {
        this.brdf = new Spectrum(brdf);
        this.emission = new Spectrum(emission);
        this.w = new Vector3f(w);
        this.isSpecular = isSpecular;
        this.p = p;
    }

    protected ShadingSample() {
        this.isSampleable = false;
    }

    public boolean isSampleable() {
        return isSampleable;
    }

    public Spectrum getBrdf() {
        return brdf;
    }

    public Spectrum getEmission() {
        return emission;
    }

    public Vector3f getW() {
        return w;
    }

    public boolean isSpecular() {
        return isSpecular;
    }

    public float getP() {
        return p;
    }
}