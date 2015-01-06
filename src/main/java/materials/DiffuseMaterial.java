package materials;

/**
 * Created by simplaY on 05.01.2015.
 */


        import base.HitRecord;
import base.Material;
import base.ShadingSample;
import base.Spectrum;

import javax.vecmath.Matrix3f;
import javax.vecmath.Vector3f;

/**
 * A basic diffuse material.
 */
public class DiffuseMaterial implements Material {

    private final Spectrum kd;

    /**
     * Note that the parameter value {@param kd} is the diffuse reflectance,
     * which should be in the range [0,1], a value of 1 meaning all light
     * is reflected (diffusely), and none is absorbed. The diffuse BRDF
     * corresponding to {@param kd} is actually {@param kd}/pi.
     *
     * @param kd the diffuse reflectance
     */
    public DiffuseMaterial(Spectrum kd) {
        this.kd = new Spectrum(kd);
        // Normalize
        this.kd.scale(1f/(float)Math.PI);
    }

    /**
     * Default diffuse material with reflectance (1,1,1).
     */
    public DiffuseMaterial() {
        this(new Spectrum(1.f, 1.f, 1.f));
    }

    /**
     * Returns diffuse BRDF value, that is, a constant.
     *
     *  @param wOut outgoing direction, by convention towards camera
     *  @param wIn incident direction, by convention towards light
     *  @param hitRecord hit record to be used
     */
    public Spectrum evaluateBRDF(HitRecord hitRecord, Vector3f wOut, Vector3f wIn) {
        return new Spectrum(kd);
    }

    public boolean hasSpecularReflection() {
        return false;
    }

    public ShadingSample evaluateSpecularReflection(HitRecord hitRecord) {
        return null;
    }
    public boolean hasSpecularRefraction() {
        return false;
    }

    public ShadingSample evaluateSpecularRefraction(HitRecord hitRecord) {
        return null;
    }


    public ShadingSample getShadingSample(HitRecord hitRecord, float[] sample) {

        Vector3f dir = new Vector3f();
        float sqr_psi_1 = (float) Math.sqrt(sample[0]);
        float two_pi_psi_2 = (float) (sample[1]*2*Math.PI);

        dir.x = (float) (Math.cos(two_pi_psi_2)*sqr_psi_1);
        dir.y = (float) (Math.sin(two_pi_psi_2)*sqr_psi_1);
        dir.z = (float) Math.sqrt(1 - sample[0]);
        assert(Math.abs(dir.lengthSquared() - 1) < 1e-5f);

        //map to directional vector
        Matrix3f m = hitRecord.getTBS();
        m.transform(dir);
        dir.normalize();

        float p = (float) (dir.dot(hitRecord.getNormal())/Math.PI);
        assert p > 0;
        return new ShadingSample(evaluateBRDF(hitRecord, hitRecord.getW(), dir), new Spectrum(), dir, false, p);

    }

    public boolean castsShadows() {
        return true;
    }

    public Spectrum evaluateEmission(HitRecord hitRecord, Vector3f wOut) {
        return null;
    }

    public ShadingSample getEmissionSample(HitRecord hitRecord, float[] sample) {
        // TODO make a sentinel
        return new ShadingSample(null, null, null, false, 0f);
    }

    @Override
    public void evaluateBumpMap(HitRecord hitRecord) {
    }

}

