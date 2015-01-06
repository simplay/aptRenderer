package integrators;

/**
 * Created by simplaY on 05.01.2015.
 */

import base.*;
import util.VectorMath;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.Iterator;

public class PointLightIntegrator implements Integrator {

    private final LightList lightList;
    private final Intersectable root;

    /**
     * Integrate a given scene.
     *
     * @param scene Scene we want to apply our integrator.
     */
    public PointLightIntegrator(Scene scene) {
        this.lightList = scene.getLightList();
        this.root = scene.getIntersectable();
    }

    /**
     * Basic Point Light integrator iterates over the light sources and accumulates
     * their BRDF contributions. Performs shading.
     * No reflection, refraction effects modeled
     * No area light sources
     *
     * @param cameraRay primary camera ray
     * @return total contribution of this primary ray
     */
    public Spectrum integrate(Ray cameraRay) {
        HitRecord intersectionsEyeScene = root.intersect(cameraRay);
        Spectrum totalContribution = new Spectrum(0.f);
        if (intersectionsEyeScene.hasIntersection()) {

            // Iterate over all light sources
            Iterator<LightGeometry> lightSources = lightList.iterator();
            while (lightSources.hasNext()) {
                LightGeometry lightSource = lightSources.next();
                Spectrum currentContribution = getContributionOf(lightSource, intersectionsEyeScene, cameraRay.getT());
                totalContribution.add(currentContribution);
            }
        }
        return totalContribution;
    }

    public float[][] makePixelSamples(Sampler sampler, int n) {
        return sampler.makeSamples(n, 2);
    }

    /**
     * Check whether hitPosition receives light
     *
     * @param hitPosition viewer ray hit (closest)
     * @param L           light direction vector
     * @param t           parameter of ray equation p_uvw(t) = 0 + t(s_uvw-0)
     * @return is light source occluded by object at hitPostion?
     */
    private boolean isOccluded(Point3f hitPosition, Vector3f L, float t, float eps) {
        boolean isShaddowed = false;
        Ray shadowRay = new Ray(hitPosition, L, t, true);
        HitRecord shadowHit = root.intersect(shadowRay);

        if (shadowHit.hasIntersection()) {
            float distShadHitViewHit2 = VectorMath.dist2(shadowHit.getPosition(), hitPosition);
            if (shadowHit.getMaterial().castsShadows() && distShadHitViewHit2 < eps) {
                isShaddowed = true;
            }
        }
        return isShaddowed;
    }

    /**
     * Compute BRDF contribution for a given source at closest intersection point.
     *
     * @param lightSource current point light source.
     * @param hitRecord   closest intersection primary ray with scene.
     * @param t           parameter of ray equation p_uvw(t) = 0 + t(s_uvw-0).
     * @return returns current spectrum of light source at intersaction point.
     */
    private Spectrum getContributionOf(LightGeometry lightSource, HitRecord hitRecord, float t) {
        // Make direction from hit point to light source position;
        // this is only supposed to work with point lights
        HitRecord lightHit = lightSource.sample(null);
        Vector3f lightDir = VectorMath.sub(lightHit.getPosition(), hitRecord.getPosition());

        float d2 = lightDir.lengthSquared();
        lightDir.normalize();

        boolean hasShaddow = isOccluded(hitRecord.getPosition(), lightDir, t, d2);
        if (hasShaddow) return new Spectrum(0f);

        // Evaluate the BRDF
        Spectrum brdfValue = hitRecord.getMaterial().evaluateBRDF(hitRecord, hitRecord.getW(), lightDir);

        // shading: brdf * emission * ndotl * geometry term
        Spectrum contribution = new Spectrum(brdfValue);

        Vector3f L = VectorMath.negate(lightDir);
        Spectrum lightEmission = lightHit.getMaterial().evaluateEmission(lightHit, L);
        contribution.mult(lightEmission);

        // cos_theta: angle between surface normal and light direction
        float cos_theta = hitRecord.getNormal().dot(lightDir);
        cos_theta = Math.max(cos_theta, 0f);
        contribution.scale(cos_theta);

        // Geometry term(for point lights): multiply with 1/(squared distance)
        contribution.scale(1.f / d2);
        return contribution;
    }
}
