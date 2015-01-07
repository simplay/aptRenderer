package materials;

import base.HitRecord;
import base.Material;
import base.ShadingSample;
import base.Spectrum;
import constants.ShadingSampleSentinel;

import javax.vecmath.Vector3f;

public class BlinnMaterial implements Material{

    private final Spectrum diffuse;
    private final Spectrum specular;
    private final float shinyness;

    /**
     *
     * @param diffuse
     * @param specular
     * @param shinyness
     */
    public BlinnMaterial(Spectrum diffuse, Spectrum specular, float shinyness){
        this.diffuse = diffuse;
        this.diffuse.scale(1f / (float) Math.PI);
        this.specular = specular;
        this.shinyness = shinyness;
    }


    @Override
    public Spectrum evaluateBRDF(HitRecord hitRecord, Vector3f wOut,
                                 Vector3f wIn) {

        Spectrum diffuseContribution = new  Spectrum(diffuse);
        Spectrum specularContribution = new Spectrum(specular);
        Spectrum totalContribution = new Spectrum();
        Vector3f halfway = new Vector3f();

        diffuseContribution.scale(wIn.dot(hitRecord.getNormal()));

        halfway.add(wIn, wOut);
        halfway.normalize();

        specularContribution.scale((float) Math.pow(halfway.dot(hitRecord.getNormal()), shinyness));
        Spectrum ambientContribution = new Spectrum(diffuse);
        totalContribution.add(diffuseContribution);
        totalContribution.add(specularContribution);
        totalContribution.add(ambientContribution);
        return totalContribution;
    }

    @Override
    public Spectrum evaluateEmission(HitRecord hitRecord, Vector3f wOut) {
        return null;
    }

    @Override
    public boolean hasSpecularReflection() {
        return false;
    }

    @Override
    public ShadingSample evaluateSpecularReflection(HitRecord hitRecord) {
        return ShadingSampleSentinel.getInstance();
    }

    @Override
    public boolean hasSpecularRefraction() {
        return false;
    }

    @Override
    public ShadingSample evaluateSpecularRefraction(HitRecord hitRecord) {
        return ShadingSampleSentinel.getInstance();
    }

    @Override
    public ShadingSample getShadingSample(HitRecord hitRecord, float[] sample) {
        return ShadingSampleSentinel.getInstance();
    }

    @Override
    public ShadingSample getEmissionSample(HitRecord hitRecord, float[] sample) {
        return ShadingSampleSentinel.getInstance();
    }

    @Override
    public boolean castsShadows() {
        return true;
    }

    @Override
    public void evaluateBumpMap(HitRecord hitRecord) {

    }

}
