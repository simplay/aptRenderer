package lightsources;

/**
 * Created by simplaY on 05.01.2015.
 */

import base.HitRecord;
import base.LightGeometry;
import base.Ray;
import base.Spectrum;
import constants.HitSentinel;
import materials.PointLightMaterial;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.util.Random;

/**
 * Implements a point light using a {@link materials.PointLightMaterial}.
 */
public class PointLight implements LightGeometry {

    Vector3f position;
    PointLightMaterial pointLightMaterial;
    Random rand;

    public PointLight(Vector3f position, Spectrum emission) {
        this.position = new Vector3f(position);
        this.rand = new Random();
        pointLightMaterial = new PointLightMaterial(emission);
    }

    /**
     * A ray never hit a point.
     */
    public HitRecord intersect(Ray r) {
        return HitSentinel.getInstance();
    }

    /**
     * Sample a point on the light geometry. On a point light, always return
     * light position with probability one. Set normal to null.
     */
    public HitRecord sample(float[] s) {
        return new HitRecord(new Point3f(position), pointLightMaterial, 1f);
    }

}
