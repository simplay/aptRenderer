package scenes;

import base.*;
import cameras.PinholeCamera;
import imageprocessing.BoxfilterFilm;
import imageprocessing.ClampTonemapper;
import integrators.PointLightIntegratorFactory;
import intersectables.IntersectableList;
import intersectables.geometries.Plane;
import intersectables.geometries.Sphere;
import lightsources.PointLight;
import materials.BlinnMaterial;
import samplers.OneSamplerFactory;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Created by simplaY on 07.01.2015.
 */
public class BlinnTest extends Scene {

    public BlinnTest(int width, int height, int spp) {
        super(width, height, spp);
        this.filePathName = "blinn_test_scene";
        this.film = new BoxfilterFilm(width, height);
        this.tonemapper = new ClampTonemapper();
        this.integratorFactory = new PointLightIntegratorFactory();
        this.samplerFactory = new OneSamplerFactory();

        LightGeometry pointLight1 = new PointLight(new Vector3f(.5f, .5f, 2.f), new Spectrum(1.f, 1.f, 1.f));
        LightGeometry pointLight2 = new PointLight(new Vector3f(-.75f, .75f, 2.f), new Spectrum(1.f, 1.f, 1.f));
        lightList = new LightList();
        lightList.add(pointLight1);
        lightList.add(pointLight2);
    }

    @Override
    public Camera initializeCamera() {
        Vector3f eye = new Vector3f(0.f, 0.f, 3.f);
        Vector3f lookAt = new Vector3f(0.f, 0.f, 0.f);
        Vector3f up = new Vector3f(0.f, 1.f, 0.f);
        float fov = 60.f;
        float aspect = width / height;
        return new PinholeCamera(eye, lookAt, up, fov, aspect, width, height);
    }

    @Override
    public Intersectable initializeGeometries() {
        IntersectableList intersectableList = new IntersectableList();
        intersectableList.add(new Plane(new Vector3f(0.f, 1.f, 0.f), 1.f));
        intersectableList.add(new Sphere(new Point3f(), 1f, new BlinnMaterial(new Spectrum(1,1,0), new Spectrum(0.6f), 50f)));
        return intersectableList;
    }
}
