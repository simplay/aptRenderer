package imageprocessing;

import base.Film;
import base.Spectrum;

/**
 * Created by simplaY on 04.01.2015.
 */
public class BoxfilterFilm implements Film {

    private final Spectrum[][] image;
    private final int width;
    private final int height;
    private final Spectrum[][] unnormalized;
    private final float nSamples[][];

    public BoxfilterFilm(int width, int height) {
        this.width = width;
        this.height = height;
        image = new Spectrum[width][height];
        unnormalized = new Spectrum[width][height];
        nSamples = new float[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image[i][j] = new Spectrum();
                unnormalized[i][j] = new Spectrum();
                nSamples[i][j] = 0.0f;
            }
        }
    }

    @Override
    public void addSample(double x, double y, Spectrum s) {
        if (isWithinRange(x, y)) {
            unnormalized[(int) x][(int) y].r += s.r;
            unnormalized[(int) x][(int) y].g += s.g;
            unnormalized[(int) x][(int) y].b += s.b;
            nSamples[(int) x][(int) y]++;
            image[(int) x][(int) y].r = unnormalized[(int) x][(int) y].r / nSamples[(int) x][(int) y];
            image[(int) x][(int) y].g = unnormalized[(int) x][(int) y].g / nSamples[(int) x][(int) y];
            image[(int) x][(int) y].b = unnormalized[(int) x][(int) y].b / nSamples[(int) x][(int) y];
        }
    }

    private boolean isWithinRange(double x, double y) {
        return (int) x >= 0 && (int) x < width && (int) y >= 0 && (int) y < height;
    }

    @Override
    public Spectrum[][] getImage() {
        return image;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}
