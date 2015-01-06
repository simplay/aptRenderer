package util;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Vector3f;
import java.util.Random;

/**
 * Utility class for performing some basic vector math calculus.
 * Created by simplaY on 05.01.2015.
 */
public class VectorMath {

    // Random number generator
    private static Random rand = new Random();

    /**
     * Squared 2-norm distance between two given Tuple3f instances t1,t2.
     * NB: Using squared distances saves us from applying sign checks when comparing
     * two distances (i.e. order of t1, t2 does not matter) and
     * it is way more sensitive for small distance values.
     *
     * @param t1 first tuple
     * @param t2 second tuple
     * @return squared distance between first and second tuple.
     */
    public static float dist2(Tuple3f t1, Tuple3f t2) {
        Vector3f distance = new Vector3f(t1);
        distance.sub(t2);
        return distance.lengthSquared();
    }

    /**
     * Make a direction from t2 to t1.
     *
     * @param t1 to tuple
     * @param t2 from tuple
     * @return Vector pointing from second parameter to first parameter.
     */
    public static Vector3f sub(Tuple3f t1, Tuple3f t2) {
        Vector3f dir_from_t2_to_t1 = new Vector3f(t1);
        dir_from_t2_to_t1.sub(t2);
        return dir_from_t2_to_t1;
    }

    /**
     * Get an the opposite direction of a copy of a given vector.
     * I.e. when given a vector v, then return -copy(v).
     *
     * @param v direction we are looking for the opposite direction.
     * @return Opposite direction of a given vector.
     */
    public static Vector3f negate(Vector3f v) {
        Vector3f copiedV = new Vector3f(v);
        copiedV.negate();
        return copiedV;
    }

    /**
     * Get an inverted copy of a given matrix.
     *
     * @param m Matrix to invert.
     * @return inverted copy of given matrix.
     */
    public static Matrix4f invert(Matrix4f m) {
        Matrix4f copiedM = new Matrix4f(m);
        copiedM.invert();
        return copiedM;
    }

    /**
     * Compute mirror reflected direction r from an incidence direction i and a surface normal n.
     * Can be used to compute the specular reflection direction.
     * NB: Usually, the incidence direction is point away from the surface.
     * We rely on Snell's Law: r = 2 dot(n, i)*n - i
     *
     * @param normal surface normal at point where incident ray hits the surface.
     * @param wIn    Opposite direction of ray hitting the surface point.
     * @return reflected direction (also pointing away from surface).
     */
    public static Vector3f mirrorReflectionOF(Vector3f normal, Vector3f wIn) {
        float cosThetaI = wIn.dot(normal);
        Vector3f reflected = new Vector3f();
        reflected.scaleAdd(2f * cosThetaI, normal, negate(wIn));
        return reflected;
    }

    /**
     * Compute the cosine of the angle between a given normal and direction by using the formula
     * cos(theta) = <n,v>/(||n||*||v||).
     * NB: we assume that n and v are normalized vectors.
     *
     * @param normal    normalized normal vector.
     * @param direction normalized direction vector.
     * @return cosine of angle between given two vectors.
     */
    public static float cosTheta(Vector3f normal, Vector3f direction) {
        float cos_norm_dir = normal.dot(direction);
        return cos_norm_dir;
    }

    /**
     * Retrieve a random number within the range [0,1]
     *
     * @return random float within the range [0,1]
     */
    public static float randomFloat() {
        return rand.nextFloat();
    }

    /**
     * Get the position on a parametric ray given a certain parameter value t.
     * Any ray can be defined as a parametrization given the origin and a direction in the following form;
     * Ray r(t) = origin + t*direction
     *
     * @param origin a point where ray/line goes through.
     * @param dir    direction vector of ray.
     * @param t      parameter of ray used to define a certain point on a ray.
     * @return point on ray of a given parameter value.
     */
    public static Point3f pointOnRay(Point3f origin, Vector3f dir, float t) {
        Point3f pointOnRay = new Point3f(dir);
        pointOnRay.scaleAdd(t, origin);
        return pointOnRay;
    }

    /**
     * Get transformed copy of a Vector.
     *
     * @param T Homogeneous transformation matrix
     * @param v vector to transform.
     * @return transformed copy of given input.
     */
    public static Vector3f transformed(Matrix4f T, Vector3f v) {
        Vector3f copiedV = new Vector3f(v);
        T.transform(copiedV);
        return copiedV;
    }

    /**
     * Get transformed copy of a Point.
     *
     * @param T Homogeneous transformation matrix
     * @param p point to transform.
     * @return transformed copy of given input.
     */
    public static Point3f transformed(Matrix4f T, Point3f p) {
        Point3f copiedP = new Point3f(p);
        T.transform(copiedP);
        return copiedP;
    }
}
