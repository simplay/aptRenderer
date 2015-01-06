package intersectables;

import base.HitRecord;
import base.Intersectable;
import base.Ray;
import constants.HitSentinel;
import util.VectorMath;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Allows to render multiple copies of an intersectable without having to make copies of the intersectable.
 * This can be achieved by applying different transformations to the same intersectable.
 *
 * Created by simplaY on 06.01.2015.
 */
public class Instance implements Intersectable {

    // Intersectable we want to instance.
    private final PrimitiveGeometry baseGeometry;

    // Homogeneous Transformation applied to a given intersectable.
    private final Matrix4f T;

    // inverse Homogeneous Transformation of T.
    private final Matrix4f invT;

    // transposed inverse of T.
    private final Matrix4f tinvT;

    /**
     * Create an Instance of a given intersectable using a certain transformation.
     * @param baseGeometry intersectable we want to instance.
     * @param T homogeneous transformation matrix applied to intersectable.
     */
    public Instance(PrimitiveGeometry baseGeometry, Matrix4f T) {
        this.baseGeometry = baseGeometry;
        this.T = T;
        invT = new Matrix4f(T);
        invT.invert();
        tinvT = new Matrix4f(invT);
        tinvT.transpose();
    }

    @Override
    public HitRecord intersect(Ray ray) {

        // transform origin and direction of ray
        // (instead of the geometry) by applying the inverse transformation to the ray.
        Vector3f transformedInstanceDir = VectorMath.transformed(invT, ray.getDirection());
        Point3f transformedInstanceOrigin = VectorMath.transformed(invT, ray.getOrigin());

        Ray instanceRay = new Ray(transformedInstanceOrigin, transformedInstanceDir, ray.getT());
        HitRecord instanceRayHit = baseGeometry.intersect(instanceRay);

        if(instanceRayHit.hasIntersection()) {
            return transformBack(instanceRayHit);
        } else {
            return HitSentinel.getInstance();
        }
    }

    /**
     * Transforms HitRecord Data for transformed tay back to original coordinate system.
     * @param hit Hit record of ray transformed to same coordinate system as Instance has.
     * @return re-transformed Hit record data.
     */
    private HitRecord transformBack(HitRecord hit) {
        Point3f position = VectorMath.transformed(T, hit.getPosition());

        Vector3f normal = VectorMath.transformed(tinvT, hit.getNormal());
        normal.normalize();

        Vector3f wIn = VectorMath.transformed(tinvT, hit.getW());
        wIn.normalize();

        return new HitRecord(hit.getT(), position, normal, wIn, hit.getIntersectable(),
                baseGeometry.getMaterial(), hit.getU(), hit.getV(), hit.getTangent());
    }
}
