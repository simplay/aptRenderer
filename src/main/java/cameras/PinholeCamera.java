package cameras;



import base.Camera;
import base.Ray;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

/**
 * Created by simplaY on 04.01.2015.
 */
public class PinholeCamera implements Camera {
    private Matrix4f C;
    private Vector3f eye;
    private Vector3f lookAt;
    private Vector3f up;
    private float fov;
    private float aspect;
    private int width;
    private int height;
    private float t,b,l,r;

    @Override
    public String toString() {
        return C.toString();
    }

    /**
     *
     * @param eye from position of camera
     * @param lookAt to position of camera
     * @param up height
     * @param fov field of view angle [degree]
     * @param aspect aspect retio w/h
     * @param width image width
     * @param height image height
     */
    public PinholeCamera(Vector3f eye, Vector3f lookAt, Vector3f up, float fov,
                         float aspect, int width, int height) {

        this.eye = eye;
        this.lookAt = lookAt;
        this.up = up;
        this.fov = fov;
        this.aspect = aspect;
        this.width = width;
        this.height = height;

        computeCameraMatrix();
        computeImageCorners();
        System.out.println(this.t + " " + this.b + " " + this.l + " " + this.r );
    }

    /**
     * Compute image corner
     */
    private void computeImageCorners(){
        double angularFov = Math.PI * (fov / 180);
        this.t = (float) Math.tan(angularFov/2d);
        this.b = -t;
        this.r = aspect*t;
        this.l = -r;
    }

    /**
     * Compute camera to world matrix
     */
    private void computeCameraMatrix() {
        Point3f from = new Point3f(eye);
        Point3f to = new Point3f(lookAt);
        Vector3f up = new Vector3f(this.up);
        Matrix4f tmpCamera = new Matrix4f();

        // z-axis:
        Vector3f w = new Vector3f(0.0f, 0.0f, 0.0f);
        w.sub(from, to);
        w.normalize();
        Vector4f ZC = new Vector4f(w);

        // x-axis:
        Vector3f u = new Vector3f(0.0f, 0.0f, 0.0f);
        u.cross(up, w);
        u.normalize();
        Vector4f XC = new Vector4f(u);

        // y-axis:
        Vector3f v = new Vector3f(0.0f, 0.0f, 0.0f);
        v.cross(w, u);
        v.normalize();
        Vector4f YC = new Vector4f(v);

        tmpCamera.setColumn(0, XC);
        tmpCamera.setColumn(1, YC);
        tmpCamera.setColumn(2, ZC);
        tmpCamera.setColumn(3, new Vector4f(from.x, from.y, from.z, 1));

        this.C = tmpCamera;
    }

    @Override
    public Ray makeWorldSpaceRay(int i, int j, float[] sample) {
        // System.out.println("(i,j)=("+i + "," + j+ ")");
        float u_ij = l + (r-l)*(i+sample[0])/width;
        float v_ij = b + (t-b)*(j+sample[1])/height;
        float w_ij = -1f;

        Vector4f s_uvw_minusZeros = new Vector4f(u_ij, v_ij, w_ij, 0f);
        Vector4f dir4f = new Vector4f();
        C.transform(s_uvw_minusZeros, dir4f);
        Vector3f dir3f = new Vector3f(dir4f.x, dir4f.y, dir4f.z);
        //System.out.println(r+ "," +l + "u_ij " + u_ij +"(i,j)=("+i+","+j+")"+dir3f.x + " " + dir3f.y + " " + dir3f.z);
        Ray ray = new Ray(new Point3f(eye), dir3f, (float) Math.random());

        return ray;
    }
}

