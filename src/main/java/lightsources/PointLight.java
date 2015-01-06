package lightsources;

import base.HitRecord;
import base.LightGeometry;
import base.Ray;
import base.Spectrum;
import constants.HitSentinel;
import materials.PointLightMaterial;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A point light source is a single identifiable localised light source.
 * Light originates from a single point, and spreads its emission outward in all directions.
 * Implements a point light using a {@link materials.PointLightMaterial}.
 * Created by simplaY on 05.01.2015.
 */
public class PointLight implements LightGeometry {

    // position of point light source.
    private final Vector3f position;

    // point light material used for computing the light emission.
    private final PointLightMaterial pointLightMaterial;

    /**
     * Construct a point light source by defining its position and its color.
     *
     * @param position position of light source
     * @param emission light emission spectrum
     */
    public PointLight(Vector3f position, Spectrum emission) {
        this.position = new Vector3f(position);
        pointLightMaterial = new PointLightMaterial(emission);
    }

    /**
     * There is no intersection when a light hits a point light source
     *
     * @param ray intersecting ray
     * @return HitSentinel since a light source cannot get intersected.
     */
    public HitRecord intersect(Ray ray) {
        return HitSentinel.getInstance();
    }

    /**
     * Sample a point on the light geometry. On a point light, always return
     * light position with probability one. Set normal to null.
     */

    /**
     * Sample a point on the light geometry.
     * Light position is returned with probability one and normal is set to null.
     *
     * @param s samples
     * @return hit record at sample.
     */
    public HitRecord sample(float[] s) {
        return new HitRecord(new Point3f(position), pointLightMaterial, 1f);
    }

}
