import rendering.Renderer;

/**
 * Format
 *  -width WIDTH INTEGER
 *  -height HEIGHT INTEGER
 *  -task TASK INTEGER
 * Created by simplaY on 29.12.2014.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(args[1]);
        new Renderer();
    }
}
