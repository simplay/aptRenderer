import base.Spectrum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by simplaY on 31.12.2014.
 */

public class SpectrumTest {

    @Test
    public void passingNoArgsIsZeroSpectrumTest() {
        Spectrum s = new Spectrum();
        assertEquals(s.r + s.g + s.b, 0f, 0.0f);
    }

    @Test
    public void initializingGrayscaleTest() {
        float grayscaleValue = 0.2f;
        Spectrum s = new Spectrum(grayscaleValue);
        assertEquals(s.r + s.g + s.b, grayscaleValue*3, 0.0f);
    }

    @Test
    public void scaleSpectrumTest() {
        float factor = 0.12f;
        Spectrum s = new Spectrum(0.31f, 1.0f, 0.19f);
        Spectrum s2 = new Spectrum(s);
        s.scale(factor);
        assertEquals(s.r + s.g + s.b, (s2.r + s2.g + s2.b)*factor, 0.0f);
    }

    @Test
    public void multSpectrumTest() {
        Spectrum s = new Spectrum(4f, 10f, 18f);
        Spectrum s1 = new Spectrum(1f, 2f, 3f);
        s1.mult(new Spectrum(4f, 5f, 6f));
        assertEquals(s.r, s1.r, 0.0f);
        assertEquals(s.g, s1.g, 0.0f);
        assertEquals(s.b, s1.b, 0.0f);
    }

    @Test
    public void addSpectrumTest() {
        Spectrum s = new Spectrum(4f, 6f, 8f);
        Spectrum s1 = new Spectrum(3f, 4f, 5f);
        s1.add(new Spectrum(1f, 2f, 3f));
        assertEquals(s.r, s1.r, 0.0f);
        assertEquals(s.g, s1.g, 0.0f);
        assertEquals(s.b, s1.b, 0.0f);
    }

    @Test
    public void shiftSpectrumTest() {
        float bias = 0.15f;
        Spectrum s = new Spectrum(4f, 6f, 8f);
        Spectrum s1 = new Spectrum(s);
        s1.shift(bias);
        assertEquals(s.r+bias, s1.r, 0.0f);
        assertEquals(s.g+bias, s1.g, 0.0f);
        assertEquals(s.b+bias, s1.b, 0.0f);
    }

    @Test
    public void divideSpectrumTest() {
        Spectrum s = new Spectrum(0.5f, 2f, 2f);
        Spectrum s1 = new Spectrum(2f, 4f, 6f);
        s1.divide(new Spectrum(4f, 2f, 3f));
        assertEquals(s.r, s1.r, 0.0f);
        assertEquals(s.g, s1.g, 0.0f);
        assertEquals(s.b, s1.b, 0.0f);
    }

    @Test
    public void squaredSpectrumTest() {
        Spectrum s = new Spectrum(0.5f, 3f, -4f);
        Spectrum sq = new Spectrum(0.25f, 9f, 16f);
        Spectrum s1 = s.squared();
        assertEquals(sq.r, s1.r, 0.0f);
        assertEquals(sq.g, s1.g, 0.0f);
        assertEquals(sq.b, s1.b, 0.0f);
    }

    @Test
    public void squareSpectrumTest() {
        Spectrum s = new Spectrum(0.5f, 3f, -4f);
        Spectrum sq = new Spectrum(0.25f, 9f, 16f);
        s.square();
        assertEquals(sq.r, s.r, 0.0f);
        assertEquals(sq.g, s.g, 0.0f);
        assertEquals(sq.b, s.b, 0.0f);
    }

    @Test
    public void clampingMinMaxTest() {
        Spectrum overMax = new Spectrum(1.1f, 31.1f, -0.99f);
        overMax.clamp(0f, 1f);
        overMax.sub(new Spectrum(1.0f, 1.0f, 0f));
        assertEquals(overMax.r + overMax.g + overMax.b, 0f, 0.00001f);
    }

    @Test
    public void clampingKeepsComponentsWhenWithinRangeTest() {
        Spectrum overMax = new Spectrum(0.31f, 1.0f, 0.19f);
        overMax.clamp(0f, 1f);
        overMax.sub(new Spectrum(overMax));
        assertEquals(overMax.r + overMax.g + overMax.b, 0f, 0.00001f);
    }

    @Test
    public void luminanceTest() {
        Spectrum s = new Spectrum(1f);
        assertEquals(s.luminance(), 1f, 0.0f);
    }
}
