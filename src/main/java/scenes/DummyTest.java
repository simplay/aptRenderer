package scenes;

import base.Camera;
import base.Intersectable;
import base.Scene;

/**
 * Created by simplaY on 04.01.2015.
 */
public class DummyTest extends Scene {
    public DummyTest(int width, int height, int spp) {
        super(width, height, spp);
        this.filePathName = "dummy_test_scene";
        // TODO define scene
    }

    @Override
    public Camera initializeCamera() {
        return null;
    }

    @Override
    protected Intersectable initializeGeometries() {
        return null;
    }
}
