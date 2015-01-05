package base;

import javax.vecmath.*;

/**
 * Stores information about a ray-surface intersection. This information
 * is typically used for shading.
 * Created by simplaY on 03.01.2015.
 */
public class HitRecord {

    /**
     * Hit position.
     */
    private Tuple3f position;

    /**
     * Normal at hit point.
     */
    private Vector3f normal;

    /**
     * Tangent vectors at hit point.
     */
    private Vector3f tangent, bitangent;

    /**
     * Texture coordinates at hit point.
     */
    private float u, v;

    /**
     * Direction towards origin of ray that hit surface. By convention it points away from
     * the surface, that is, in the direction opposite to the incident ray.
     */
    private Vector3f w;

    /**
     * t parameter of the ray at the hit point.
     */
    private float t;

    /**
     * The {@link Intersectable} that was hit.
     */
    private Intersectable intersectable;

    /**
     * The material at the hit point.
     */
    private Material material;

    /**
     * Area probability density. This is typically used when a hit record is generated by
     * sampling the geometry, like in implementations of {@link LightGeometry}.
     */
    private float probablity;

    /**
     * tangent space transformation
     */
    private Matrix3f TBS;

    protected boolean isIntersecting = true;


    /**
     * Constructor Hit record.
     * @param t parameter of the ray at the hit point.
     * @param position hit point at surface.
     * @param normal normal at hit point.
     * @param w incident ray direction pointing away from surface.
     * @param intersectable intersectable object that was hit.
     * @param material material at hit position.
     * @param u first texture coordinate at hit point.
     * @param v second texture coordinate at hit point.
     * @param tangent tangent vector at hit point.
     */
    public HitRecord(float t, Tuple3f position, Vector3f normal, Vector3f w,
                     Intersectable intersectable, Material material, float u, float v, Vector3f tangent) {
        this.t = t;
        this.position = position;
        this.normal = normal;
        this.w = w;
        this.intersectable = intersectable;
        this.material = material;
        this.u = u;
        this.v = v;

        // Make tangent frame: t1, t2, normal is a right handed frame
        this.assignTangentsUsing(normal, tangent);
        this.assingTBS();

        // TODO reimplement this
        //this.material.evaluateBumpMap(this);
    }

    /**
     * Constructor Hit record.
     * @param t parameter of the ray at the hit point
     * @param position hit point at surface
     * @param normal normal at hit point
     * @param w incident ray direction pointing away from surface.
     * @param intersectable intersectable object that was hit.
     * @param material material at hit position
     * @param u first texture coordinate at hit point
     * @param v second texture coordinate at hit point
     */
    public HitRecord(float t, Tuple3f position, Vector3f normal, Vector3f w, Intersectable intersectable, Material material, float u, float v) {
        this(t, position, normal, w, intersectable, material, u, v, estimatedTangent());
    }

    /**
     * used for creating a sentinel
     */
    protected HitRecord() {
        isIntersecting = false;
    }


    /**
     * Was there an intersection?
     * @return a boolean indicating whether there was an intersection
     */
    public boolean hasIntersection() {
        return isIntersecting;
    }

    /**
     * If there was no tangent specified in the constructor, estimate one.
     * @return returns a normalized vector pointing to towards positive x-axis.
     */
    private static Vector3f estimatedTangent() {
        return new Vector3f(1, 0, 0);
    }

    private void assignTangentsUsing(Vector3f normal, Vector3f tangent) {
        this.tangent = tangent;
        this.tangent.cross(this.tangent, normal);
        if (this.tangent.length() == 0) {
            this.tangent = new Vector3f(0, 1, 0);
            this.tangent.cross(this.tangent, normal);
        }
        this.tangent.normalize();
        this.bitangent = new Vector3f();
        this.bitangent.cross(normal, this.tangent);
    }

    /**
     * compute and assign the tangent space transformation matrix.
     */
    private void assingTBS() {
        TBS = new Matrix3f();
        TBS.setColumn(0, this.tangent);
        TBS.setColumn(1, this.bitangent);
        TBS.setColumn(2, normal);
    }

    public Matrix3f getTBS() {
        return TBS;
    }

    public Tuple3f getPosition() {
        return position;
    }

    public Vector3f getNormal() {
        return normal;
    }

    public Vector3f getTangent() {
        return tangent;
    }

    public Vector3f getBitangent() {
        return bitangent;
    }

    public float getU() {
        return u;
    }

    public float getV() {
        return v;
    }

    public Vector3f getW() {
        return w;
    }

    public float getT() {
        return t;
    }

    public Intersectable getIntersectable() {
        return intersectable;
    }

    public Material getMaterial() {
        return material;
    }

    public float getProbablity() {
        return probablity;
    }

    public void setProbablity(float probablity) {
        this.probablity = probablity;
    }
    
}