import base.Spectrum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Created by simplaY on 31.12.2014.
 */

public class SpectrumTest {

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
}
