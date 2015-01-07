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
 * A sphere with a given radius, center and material.
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
     * @param center sphere center
     * @param radius radius
     * @param material surface material used
     */
    public Sphere(Point3f center, float radius, Material material) {
        super(material);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public HitRecord intersect(Ray ray) {

        Vector3f originCenter = VectorMath.sub(ray.getOrigin(), center);
        float a = ray.getDirection().lengthSquared();
        float b = 2f*ray.getDirection().dot(originCenter);
        float c = originCenter.lengthSquared() - radius*radius;

        Float zero = BaseMath.solveForBestZeroOfQuadratic(a, b, c);
        if (zero != null) {
            return makeHitRecord(zero.floatValue(), ray);
        }
        return HitSentinel.getInstance();

    }

    private HitRecord makeHitRecord(float zero, Ray ray) {
        Point3f position = ray.pointAt(zero);
        position.scale(-1f);
        Vector3f normal = VectorMath.sub(position, center);

        // scale by radius
        normal.scale(1f / radius);

        Vector3f w_in = ray.wIn();

        // texture coordinates using spherical coordinates.
        float u = 0.5f + (float)(Math.atan2(position.z, position.x)/(2f*Math.PI));
        float v = 0.5f - (float)(Math.asin(position.y)/Math.PI);

        return new HitRecord(zero, position, normal, w_in, this, material, u, v);

    }
}
