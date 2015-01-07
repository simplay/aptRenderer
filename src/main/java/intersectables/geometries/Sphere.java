package intersectables.geometries;

import base.HitRecord;
import base.Material;
import base.Ray;
import constants.HitSentinel;
import intersectables.PrimitiveGeometry;
import util.BaseMath;
import util.VectorMath;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A sphere with a given radius r and center c and {@link base.Material}.
 * Any 3d Sphere S can be modeled as an implicit function in the form of
 * <p/>
 * S(x,y,z) := (x-c_x)^2 + (y-c_y)^2 + (z-c_z)^2 = r^2
 * <p/>
 * where c = (c_x, c_y, c_z) denotes its center.
 * NB: all points (x,y,z) that fulfill S(x,y,z) lie on the surface of the sphere.
 * Created by simplaY on 07.01.2015.
 */
public class Sphere extends PrimitiveGeometry {

    /**
     * Sphere center in world coordinates.
     */
    private final Point3f center;

    /**
     * Radius of sphere
     */
    private final float radius;

    /**
     * Creates a complete simple sphere instance
     *
     * @param center   Sphere center
     * @param radius   Sphere radius
     * @param material surface material used for the integration.
     */
    public Sphere(Point3f center, float radius, Material material) {
        super(material);
        this.center = center;
        this.radius = radius;
    }

    /**
     * Computes {@link base.HitRecord} Data in case a ray intersects the surface of this sphere.
     * Intersection of a Ray r(t) := e + t*d and a Sphere S(x,y,z) can be computed as in the following:
     * A ray intersects a surface IFF a certain point on that ray is equal a point on the Surface of the Sphere.
     * Such point can be found by embedding the parametric Ray function r(t) into the implicit function S(x,y,z)
     * and then solve for t. Intersection points can then be computed by putting the retrieved value of t into r(t).
     * In other words:
     * <p/>
     * Any sphere S with center c and radius r can be represented by the following implicit function:
     * S(x,y,z) := (x-c_x)^2 + (y-c_y)^2 + (z-c_z)^2 = r^2
     * => S(x,y,z) := (x-c_x)^2 + (y-c_y)^2 + (z-c_z)^2 - r^2 = 0
     * => S(p) := (p-c)^2 - r^2 = 0, for all p in |R^3
     * <p/>
     * Any Ray r can with origin e and direction d can be represented by the following parametric function:
     * r(t) := e + t*d, for all t in R and e,d in |R^3
     * NB: e=(e_x, e_y, e_z) and d = (d_x, d_y, d_z).
     * <p/>
     * 1. Choose p = r(t), then plug it into S(p):
     * S(r(t)) := (r(t)-c)^2 - r^2 = 0
     * => (e_x + t*d_x - c_x)^2 + (e_y + t*d_y - c_y)^2 + (e_z + t*d_z - c_z)^2 - r^2 = 0
     * <p/>
     * 2. Reorder terms to retrieve a quadratic function of the form a*t^2 + b*t + c = 0:
     * => (ec_x + t*d_x)^2 + (ec_y + t*d_y)^2 + (ec_z + t*d_z)^2 - r^2 = 0 with ec := e-c
     * => (ec_x^2 + 2*t*ec_x*d_x + t^2 * d_x^2) + ... + (ec_z^2 + 2*t*ec_z*d_z + t^2 * d_z^2) - r^2 = 0
     * =>  t^2 *(d_x^2 + ... + d_z^2) + t*(2*ec_x*d_x + ... + 2*ec_z*d_z) + (ec_x^2 + ... + ec_z^2 - r^2) = 0
     * => t^2 * ||d||^2 + t*(2*<ec,d>) + (||ec||^2-r^2) = 0
     * => t^2 * a + t*b + c = 0
     * => a*t^2 + b*t + c = 0 with
     * a := ||d||^2, b := (2*<ec,d>), c := (||ec||^2-r^2)
     * <p/>
     * 3. Solve a*t^2 + b*t + c = 0 for t => [t1, t2].
     * 4. Take closest positive t value if existent.
     * 5. a) IF there is no such t, then report: no intersection
     * 5. b) If there is such a t, plug it into the definition of r(t).
     * <p/>
     * Compute the normal at that hit position:
     * Nabla(S)(r(t)) = 0
     * Compute texture coordinates by inverse Spherical coordinate mapping.
     *
     * @param ray intersecting ray
     * @return Hit Record containing all intersection data.
     */
    @Override
    public HitRecord intersect(Ray ray) {

        Vector3f originCenter = VectorMath.sub(ray.getOrigin(), center);
        float a = ray.getDirection().lengthSquared();
        float b = 2f * ray.getDirection().dot(originCenter);
        float c = originCenter.lengthSquared() - radius * radius;

        Float t = BaseMath.solveForBestZeroOfQuadratic(a, b, c);
        if (t != null) {
            // intersection position
            Point3f position = ray.pointAt(t);
            Vector3f normal = VectorMath.sub(position, center);

            // scale by radius
            normal.scale(1f / radius);

            // incident direction
            Vector3f w_in = ray.wIn();

            // texture coordinates using spherical coordinates.
            float u = 0.5f + (float) (Math.atan2(position.z, position.x) / (2f * Math.PI));
            float v = 0.5f - (float) (Math.asin(position.y) / Math.PI);

            return new HitRecord(t, position, normal, w_in, this, material, u, v);
        }
        return HitSentinel.getInstance();
    }
}
