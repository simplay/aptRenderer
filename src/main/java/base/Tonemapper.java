package base;

import java.awt.image.BufferedImage;

/**
 * Compresses a raw rendered {@link Film} to an image that can be displayed on typical 8-bit displays.
 * Created by simplaY on 03.01.2015.
 */
public interface Tonemapper {
    BufferedImage process(Film film);
}
