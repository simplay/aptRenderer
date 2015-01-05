import base.Scene;
import constants.SceneTask;

/**
 * Created by simplaY on 31.12.2014.
 */
public class RenderOptions {
    private final Scene selectedScene;

    public RenderOptions(String[] userInput) {
        int width = Integer.parseInt(userInput[0]);
        int height = Integer.parseInt(userInput[1]);
        int spp = Integer.parseInt(userInput[2]);
        int task = Integer.parseInt(userInput[3]);
        this.selectedScene = SceneTask.valueOf(task).initialize(width, height, spp);
    }

    public Scene getSelectedScene() {
        return selectedScene;
    }
}
