package imageprocessing;

import base.Film;
import base.Tonemapper;

import java.awt.image.BufferedImage;

/**
 * Tone maps a film by clamping all color channels to range [0,1].
 * Created by simplaY on 04.01.2015.
 */
public class ClampTonemapper implements Tonemapper {

    /**
     * Perform tone mapping and return a {@link java.awt.image.BufferedImage}.
     *
     * @param film the film to be tonemapped
     * @return the output image
     */
    @Override
    public BufferedImage process(Film film) {
        BufferedImage img = new BufferedImage(film.getWidth(), film.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < film.getWidth(); i++) {
            for (int j = 0; j < film.getHeight(); j++) {
                int rgb = film.getImage()[i][j].normalizedClamped24BitRGB();
                img.setRGB(i, film.getHeight() - 1 - j, rgb);
            }
        }
        return img;
    }
}
