package intersectables;

import base.*;
import constants.HitSentinel;
import materials.DiffuseMaterial;
import util.VectorMath;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Infinite plane define by its distance to origin and its normal.
 * The distance is relative along its specified normal vector.
 * Thus the distance to the origin is signed.
 * Created by simplaY on 04.01.2015.
 */
public class Plane implements Intersectable {

    // plane normal vector
    protected Vector3f normal;

    // signed distance from world origin to plane measured along the normal direction.
    // NB: therefore this distance value is inverse signed to
    protected float distance;

    // material assigned to plane
    // default: DiffuseMaterial
    protected Material material;

    /**
     * Plane determined by its normal and its distance to the origin relative to its given normal vector.
     * @param normal normal vector of the plane.
     * @param distance distance to origin along the the direction of the normal.
     */
    public Plane(Vector3f normal, float distance) {
        this.normal = normal;
        this.normal.normalize();
        this.distance = distance;
        this.material = new DiffuseMaterial(new Spectrum(1.f));
    }

    @Override
    public HitRecord intersect(Ray r) {
        float cos_norm_dir = VectorMath.cosTheta(normal, r.getDirection());
        if (cos_norm_dir != 0f) {

            // compute plane intersection parameter.
            // using <(ray.origin + t*ray.dir - a),n> = 0
            // t = <(a-ray.origin),n>/<ray.dir, n>
            float t = -(VectorMath.cosTheta(normal, new Vector3f(r.getOrigin())) + distance)/cos_norm_dir;

            Point3f position = new Point3f(r.getDirection());
            position.scaleAdd(t, r.getOrigin());
            Vector3f retNormal = new Vector3f(normal);

            // incident direction pointing away from plane's surface.
            Vector3f wIn = new Vector3f(r.getDirection());
            wIn.negate();
            wIn.normalize();
            if (t > 0f) {
                return new HitRecord(t, position, retNormal, wIn, this, material, 0.f, 0.f);
            }
        }
        return HitSentinel.getInstance();
    }
}
