package base;

import javax.vecmath.*;

/**
 * Materials implement functionality for shading surfaces using their BRDFs. Light sources
 * are implemented using materials that return an emission term.
 * Created by simplaY on 03.01.2015.
 */
public interface Material {

    /**
     * Evaluate BRDF for pair of incoming and outgoing directions. This method
     * is typically called by an integrator when the integrator obtained the incident
     * direction by sampling a point on a light source.
     *
     * @param hitRecord Information about hit point
     * @param wOut Outgoing direction, normalized and pointing away from the surface
     * @param wIn Incoming direction, normalized and pointing away from the surface
     * @return BRDF value
     */
    public Spectrum evaluateBRDF(HitRecord hitRecord, Vector3f wOut, Vector3f wIn);

    /**
     * Evaluate emission for outgoing direction. This method is typically called
     * by an integrator when the integrator obtained the outgoing direction of
     * the emission by sampling a point on a light source.
     *
     * @param hitRecord Information about hit point on light source
     * @param wOut Outgoing direction, normalized and pointing away from the surface
     * @return emission value
     */
    public Spectrum evaluateEmission(HitRecord hitRecord, Vector3f wOut);

    /**
     * Return whether material has perfect specular reflection.
     */
    public boolean hasSpecularReflection();

    /**
     * Evaluate specular reflection. This method is typically called by a recursive
     * ray tracer to follow the path of specular reflection.
     */
    public ShadingSample evaluateSpecularReflection(HitRecord hitRecord);

    /**
     * Return whether the material has perfect specular refraction.
     */
    public boolean hasSpecularRefraction();

    /**
     * Evaluate specular refraction. This method is typically called by a recursive
     * ray tracer to follow the path of specular refraction.
     */
    public ShadingSample evaluateSpecularRefraction(HitRecord hitRecord);

    /**
     * Calculate a shading sample, given a uniform random sample as input. This
     * method is typically called in a path tracer to sample and evaluate the
     * next path segment. The methods decides which component of the material to
     * sample (diffuse, specular reflection or refraction, etc.), computes an
     * incident direction, and returns the BRDF value, the direction, and the
     * probability density (stored in a {@link ShadingSample}).
     */
    public ShadingSample getShadingSample(HitRecord hitRecord, float[] sample);

    /**
     * Calculate an emission sample, given a hit record and a uniform random
     * sample as input. This method is typically called in a bidirectional
     * path tracer to sample and evaluate the first light path segment. The
     * methods computes an outgoing direction, and returns the emission value,
     * the direction, and the probability density (all stored in a
     * {@link ShadingSample}).
     */
    public ShadingSample getEmissionSample(HitRecord hitRecord, float[] sample);

    /**
     * Indicate whether the material casts shadows or not.
     */
    public boolean castsShadows();


    public void evaluateBumpMap(HitRecord hitRecord);
}