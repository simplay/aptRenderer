package scenes;

import base.*;
import cameras.PinholeCamera;
import imageprocessing.BoxfilterFilm;
import imageprocessing.ClampTonemapper;
import integrators.PointLightIntegratorFactory;
import intersectables.IntersectableList;
import intersectables.geometries.Plane;
import lightsources.PointLight;
import samplers.OneSamplerFactory;

import javax.vecmath.Vector3f;

/**
 * Created by simplaY on 04.01.2015.
 */
public class CameraTest extends Scene {
    public CameraTest(int width, int height, int spp) {
        super(width, height, spp);
        this.filePathName = "camera_test_scene";
        this.film = new BoxfilterFilm(width, height);
        this.tonemapper = new ClampTonemapper();
        this.integratorFactory = new PointLightIntegratorFactory();
        this.samplerFactory = new OneSamplerFactory();
    }

    @Override
    public Camera initializeCamera() {
        Vector3f eye = new Vector3f(0.5f, 0.5f, 3.f);
        Vector3f lookAt = new Vector3f(0.5f, 0.f, 0.f);
        Vector3f up = new Vector3f(0.2f, 1.f, 0.f);
        float fov = 60.f;
        float aspect = width / height;
        return new PinholeCamera(eye, lookAt, up, fov, aspect, width, height);
    }

    @Override
    public Intersectable initializeGeometries() {
        IntersectableList intersectableList = new IntersectableList();
        intersectableList.add(new Plane(new Vector3f(1.f, 0.f, 0.f), 1.f));
        intersectableList.add(new Plane(new Vector3f(-1.f, 0.f, 0.f), 1.f));
        intersectableList.add(new Plane(new Vector3f(0.f, 1.f, 0.f), 1.f));
        intersectableList.add(new Plane(new Vector3f(0.f, -1.f, 0.f), 1.f));
        intersectableList.add(new Plane(new Vector3f(0.f, 0.f, 1.f), 1.f));
        return intersectableList;
    }

    @Override
    protected LightList initializeLights() {
        LightGeometry pointLight = new PointLight(new Vector3f(0.f, 0.f, 3.f),
                new Spectrum(10.f, 10.f, 10.f));
        LightList lightList = new LightList();
        lightList.add(pointLight);
        return lightList;
    }
}
