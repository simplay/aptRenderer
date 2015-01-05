package materials;

/**
 * Created by simplaY on 05.01.2015.
 */



        import base.HitRecord;
import base.Material;
import base.ShadingSample;
import base.Spectrum;

import javax.vecmath.Vector3f;

/**
 * This material should be used with {@link lightsources.PointLight}.
 */
public class PointLightMaterial implements Material {

    private final Spectrum emission;

    public PointLightMaterial(Spectrum emission) {
        this.emission = new Spectrum(emission);

    }

    public Spectrum evaluateEmission(HitRecord hitRecord, Vector3f wOut) {
        return new Spectrum(emission);
    }

    /**
     * Return a random direction over the full sphere of directions.
     */
    public ShadingSample getEmissionSample(HitRecord hitRecord, float[] sample) {
        float theta = (float) (sample[0] * 2 * Math.PI);
        float z = sample[1] * 2 - 1;
        float s = (float) Math.sqrt(1 - z*z);
        Vector3f randomDir = new Vector3f((float)(s*Math.cos(theta)), (float)(s* Math.sin(theta)), z);
        return new ShadingSample(new Spectrum(), new Spectrum(emission), randomDir, false, (float)(1/(4*Math.PI)));
    }

    /**
     * Shouldn't be called on a point light
     */
    public ShadingSample getShadingSample(HitRecord hitRecord, float[] sample) {
        return null;
    }

    /**
     * Shouldn't be called on a point light
     */
    public boolean castsShadows() {
        return false;
    }

    /**
     * Shouldn't be called on a point light
     */
    public Spectrum evaluateBRDF(HitRecord hitRecord, Vector3f wOut, Vector3f wIn) {
        return new Spectrum();
    }

    /**
     * Shouldn't be called on a point light
     */
    public boolean hasSpecularReflection() {
        return false;
    }

    /**
     * Shouldn't be called on a point light
     */
    public ShadingSample evaluateSpecularReflection(HitRecord hitRecord) {
        return null;
    }

    /**
     * Shouldn't be called on a point light
     */
    public boolean hasSpecularRefraction() {
        return false;
    }

    /**
     * Shouldn't be called on a point light
     */
    public ShadingSample evaluateSpecularRefraction(HitRecord hitRecord) {
        return null;
    }


    @Override
    public void evaluateBumpMap(HitRecord hitRecord) {
    }
}
