package util;

/**
 * Created by simplaY on 05.01.2015.
 */



import javax.vecmath.*;

public class StaticVecmath {

    public static float dist2(Tuple3f position, Tuple3f position2) {
        Vector3f tmp = new Vector3f(position);
        tmp.sub(position2);
        return tmp.lengthSquared();
    }

    public static Vector3f sub(Tuple3f position, Tuple3f position2) {
        Vector3f r = new Vector3f(position);
        r.sub(position2);
        return r;
    }

    public static Vector3f negate(Vector3f v) {
        Vector3f r = new Vector3f(v);
        r.negate();
        return r;
    }

    public static Matrix4f invert(Matrix4f m) {
        Matrix4f r = new Matrix4f(m);
        r.invert();
        return r;
    }



    public static Vector3f reflect(Vector3f normal, Vector3f wIn) {
        float cosThetaI = wIn.dot(normal);
        Vector3f reflected = new Vector3f();
        reflected.scaleAdd(2 * cosThetaI, normal, negate(wIn));
        return reflected;
    }
}
