import rendering.Renderer;

/**
 * Format
 * -width WIDTH INTEGER
 * -height HEIGHT INTEGER
 * -spp SAMPLES PER PIXEL
 * -task TASK INTEGER starts counting with 0
 * Created by simplaY on 29.12.2014.
 */
public class Main {
    public static void main(String[] args) {
        RenderOptions ro = new RenderOptions(args);
        new Renderer(ro.getSelectedScene());
    }
}
