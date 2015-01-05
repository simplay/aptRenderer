package util;

import javax.vecmath.*;

/**
 * Utility class for performing some basic vector math calculus.
 * Created by simplaY on 05.01.2015.
 */
public class VectorMath {

    /**
     * Squared 2-norm distance between two given Tuple3f instances t1,t2.
     * NB: Using squared distances saves us from applying sign checks when comparing
     * two distances (i.e. order of t1, t2 does not matter) and
     * it is way more sensitive for small distance values.
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
     * @param wIn Opposite direction of ray hitting the surface point.
     * @return reflected direction (also pointing away from surface).
     */
    public static Vector3f mirrorReflectionOF(Vector3f normal, Vector3f wIn) {
        float cosThetaI = wIn.dot(normal);
        Vector3f reflected = new Vector3f();
        reflected.scaleAdd(2f*cosThetaI, normal, negate(wIn));
        return reflected;
    }
}
