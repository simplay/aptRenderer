package intersectables;

import base.*;
import constants.HitSentinel;
import materials.DiffuseMaterial;
import util.VectorMath;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A Infinite Plane is an Intersectable define by its distance to origin and its normal.
 * The distance is relative along its specified normal vector.
 * Thus the distance to the origin is signed.
 *
 * Note that it is enough to use a distance value and a normal vector
 * to define an infinite plane by relying on the following idea:
 * The given distance is the distance to the origin within the world space
 * when following a line that goes through that world origin
 * having the same direction as the provided normal vector
 *
 * Created by simplaY on 04.01.2015.
 */
public class Plane implements Intersectable {

    // plane normal vector
    protected final Vector3f normal;

    // Signed distance from world origin to the plane measured along the normal direction.
    // NB: therefore this distance value is inverse signed to
    protected final float distance;

    // material assigned to plane - default: DiffuseMaterial
    protected final Material material;


    /**
     * Plane determined by its normal and its distance to the origin relative to its given normal vector.
     *
     * @param normal   normal vector of the plane.
     * @param distance distance to origin along the the direction of the normal.
     * @param material Material used for shading when intersecting with Intersectable.
     */
    public Plane(Vector3f normal, float distance, Material material) {
        this.normal = normal;
        this.normal.normalize();
        this.distance = distance;
        this.material = material;
    }

    /**
     * Plane determined by its normal and its distance to the origin relative to its given normal vector.
     * Assign a Diffuse material having a random spectrum assigned to.
     *
     * @param normal   normal vector of the plane.
     * @param distance distance to origin along the the direction of the normal.
     */
    public Plane(Vector3f normal, float distance) {
        this(normal, distance, new DiffuseMaterial(new Spectrum(VectorMath.randomFloat(), VectorMath.randomFloat(), VectorMath.randomFloat())));
    }

    @Override
    public HitRecord intersect(Ray ray) {
        float cos_norm_dir = VectorMath.cosTheta(normal, ray.getDirection());
        if (cos_norm_dir != 0f) {

            // compute plane intersection parameter.
            // using <(ray.origin + t*ray.dir - a),n> = 0
            // t = <(a-ray.origin),n>/<ray.dir, n>
            float t = -(VectorMath.cosTheta(normal, new Vector3f(ray.getOrigin())) + distance) / cos_norm_dir;

            Point3f position = VectorMath.pointOnRay(ray.getOrigin(), ray.getDirection(), t);
            Vector3f retNormal = new Vector3f(normal);

            // incident direction pointing away from plane's surface.
            Vector3f wIn = ray.wIn();

            // hit was in front of the user's eye
            if (t > 0f) {
                return new HitRecord(t, position, retNormal, wIn, this, material, 0.f, 0.f);
            }
        }
        return HitSentinel.getInstance();
    }
}
