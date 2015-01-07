package constants;

import base.Scene;
import scenes.BlinnTest;
import scenes.CameraTest;
import scenes.DummyTest;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Each SceneTask value is mapped to a particular Scene used for rendering.
 * Created by simplaY on 04.01.2015.
 */
public enum SceneTask {
    DUMMY_TEST(DummyTest.class),
    CAMERA_TEST(CameraTest.class),
    BLINN_TEST(BlinnTest.class);

    // Associates an unique integer key to each SceneTask enum value.
    private static Map<Integer, SceneTask> map = new HashMap<Integer, SceneTask>();

    // enum member index counter. used for assigning each enum an unique int idx.
    private static int idxCounter = 0;

    // associated enum base name
    private final String base;

    // unique enum key
    private final int id;

    // store each enum value in a hash map. this allows us to access each constant by a unqiue number.
    static {
        for (SceneTask task : SceneTask.values()) {
            map.put(task.id, task);
        }
    }

    /**
     * Each SceneTask has a unique integer key and
     * a certain {@lin base.Scene} instance assigned to.
     *
     * @param base
     */
    private SceneTask(Class base) {
        this.id = nextCounter();
        this.base = base.getName();
    }

    /**
     * Provided unique integers for this enum
     *
     * @return a unique integer within this enum.
     */
    private static int nextCounter() {
        return idxCounter++;
    }

    /**
     * retrieve a particular enum by an integer key.
     * assigns null if there is no SceneTask enum mapped to this key.
     *
     * @param taskId SceneTask integer key of interest.
     * @return an Scene task associated to a given integer key.
     */
    public static SceneTask valueOf(int taskId) {
        return map.get(taskId);
    }

    /**
     * Initialized a user-specified Scene by relying on reflection.
     *
     * @param width  scene height.
     * @param height scene width.
     * @param spp    samples per pixel.
     * @return user specified scene object.
     */
    public Scene initialize(int width, int height, int spp) {
        Integer w = new Integer(width);
        Integer h = new Integer(height);
        Integer s = new Integer(spp);
        Object selectedScene = null;
        try {

            Class sceneClass = Class.forName(base);
            Constructor sceneConstructor = sceneClass.getConstructor(int.class, int.class, int.class);
            selectedScene = sceneConstructor.newInstance(w.intValue(), h.intValue(), s.intValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (Scene) selectedScene;
    }

}
