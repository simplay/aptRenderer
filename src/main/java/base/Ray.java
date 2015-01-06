package base;

import util.VectorMath;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A base.Ray is determined by an origin and direction.
 * In our case a ray is given by a set of point fulfilling the parametrization
 * p(t) = origin + t*direction, for any t in |R.
 * For modeling light, we use the concept of rays.
 * Created by simplay on 30/12/14.
 */
public class Ray {

    // origin of the ray in world coordinates.
    private Point3f origin;

    // direction of ray in world coordinates.
    private Vector3f direction;

    // parameter to determine a particular position of the ray.
    private float t;

    // number of times a ray already was reflected of
    private int bounceCount = 0;

    // perturbation threshold value
    private final float EPS = 1e-5f;


    /**
     * A ray is given by its direction and origin.
     * In order to evaluate a particular position on this ray
     * (this position is supposed to depict a intersection with another object)
     * we have to provide an intersection parameter t.
     * @param origin position where ray start world space
     * @param direction direction where rays is pointing at
     * @param t intersection paramter.
     */
    public Ray(Point3f origin, Vector3f direction, float t) {
        this(origin, direction, t, false);
    }

    /**
     * A ray is given by its direction and origin.
     * In order to evaluate a particular position on this ray
     * (this position is supposed to depict a intersection with another object)
     * we have to provide an intersection parameter t.
     * In addition, we can pass a boolean determining whether
     * we want to slightly perturbate the origin of the ray
     * @param origin position where ray start world space
     * @param direction direction where rays is pointing at
     * @param t intersection parameter.
     * @param isPerturbated should we slightly perturbate the origin.
     */
    public Ray(Point3f origin, Vector3f direction, float t, boolean isPerturbated) {
        Point3f perturbatedOrigin = new Point3f();
        if (isPerturbated) {
            perturbatedOrigin.scaleAdd(EPS, direction, origin);
        } else {
            perturbatedOrigin.set(origin);
        }

        this.origin = perturbatedOrigin;
        this.direction = direction;
        this.t = t;
    }

    /**
     * Get the position on this ray given a certain parameter value t.
     * NB: Any ray can be defined as a parametrization given the origin and a direction in the following form;
     * Ray r(t) = origin + t*direction
     * @param t parameter of ray used to define a certain point on a ray.
     * @return point on ray of a given parameter value.
     */
    public Point3f pointAt(float t) {
        return VectorMath.pointOnRay(origin, direction, t);
    }

    /**
     * Get the normalized opposite direction of this ray's direction.
     * It is used in order to define the incident direction w_in
     * used in the rendering equation.
     * By convention this has the opposite direction of the incident ray.
     * NB: Calling this method does not the state of this ray.
     * @return normalized negated direction of this ray.
     */
    public Vector3f wIn() {
        Vector3f dirCopied = new Vector3f(direction);
        dirCopied.normalize();
        dirCopied.negate();
        return dirCopied;
    }

    /**
     * Set bounce count of this ray equal to
     * the bounce count of another ray incremented by one.
     * @param ray other ray used as base bounce count.
     */
    public void setNextBounceCount(Ray ray) {
        this.bounceCount = ray.getBounceCount() + 1;
    }

    /**
     * Get bounce count of this ray
     * @return bounce count of this ray
     */
    public int getBounceCount() {
        return bounceCount;
    }

    public float getT() {
        return t;
    }

    public Point3f getOrigin() {
        return origin;
    }

    public Vector3f getDirection() {
        return direction;
    }


}