package intersectables;

import base.*;
import constants.HitSentinel;
import materials.DiffuseMaterial;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Created by simplaY on 04.01.2015.
 */
public class Plane implements Intersectable {

    protected Vector3f normal;
    protected float distance;
    protected Material material;

    public Plane(Vector3f normal, float distance) {
        this.normal = normal;
        this.normal.normalize();
        this.distance = distance;
        this.material = new DiffuseMaterial(new Spectrum(1.f));
    }

    @Override
    public HitRecord intersect(Ray r) {
        float cos_norm_dir = normal.dot(r.getDirection());
        if (cos_norm_dir != 0f) {
            float t = -(normal.dot(new Vector3f(r.getOrigin())) + distance) / cos_norm_dir;
            Point3f position = new Point3f(r.getDirection());
            position.scaleAdd(t, r.getOrigin());
            Vector3f retNormal = new Vector3f(normal);
            // wIn is incident direction; convention is that it points away from surface
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
