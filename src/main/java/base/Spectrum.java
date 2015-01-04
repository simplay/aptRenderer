package base;

/**
 * A Spectrum is a representation of colors implemented as RGB colors.
 */
public class Spectrum {

    public float r, g, b;

    /**
     * default spectrum (0,0,0)
     */
    public Spectrum() {
        r = 0.f;
        g = 0.f;
        b = 0.f;
    }

    /**
     * Set spectrum to passed RGB values
     * @param r red
     * @param g green
     * @param b blue
     */
    public Spectrum(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * spectrum is equal passed grayscale value g (g, g, g)
     * @param grayscale
     */
    public Spectrum(float grayscale) {
        this(grayscale, grayscale, grayscale);
    }

    /**
     * use clone other spectrum
     * @param s spectrum
     */
    public Spectrum(Spectrum s) {
        this.r = s.r;
        this.g = s.g;
        this.b = s.b;
    }

    /**
     * scale this spectrum's
     * components by t
     * @param t scalar value
     */
    public void scale(float t) {
        r = r * t;
        g = g * t;
        b = b * t;
    }

    /**
     * multiply this spectrum
     * by other spectrum component-wise
     * @param s spectrum to multiply component-wise to this spectrum.
     */
    public void mult(Spectrum s) {
        r = r * s.r;
        g = g * s.g;
        b = b * s.b;
    }

    /**
     * add another spectrum to this spectrum
     * @param s spectrum to add to this spectrum.
     */
    public void add(Spectrum s) {
        r = r + s.r;
        g = g + s.g;
        b = b + s.b;
    }

    /**
     * Shift this spectrum by a given scalar value (bias).
     * @param t scalar used to shift this spectrum
     */
    public void shift(float t) {
        r = r + t;
        g = g + t;
        b = b + t;
    }

    /**
     * subtract spectrum s from this spectrum
     * @param s spectrum to subtract from this spectrum.
     */
    public void sub(Spectrum s) {
        r = r - s.r;
        g = g - s.g;
        b = b - s.b;
    }

    /**
     * divide this spectrum component-wise
     * by other spectrum s.
     * @param s spectrum to divide by
     */
    public void divide(Spectrum s) {
        r = r / s.r;
        g = g / s.g;
        b = b / s.b;
    }

    /**
     * get a new squared instance of this spectrum.
     * @return spectrum t = s^2
     */
    public Spectrum squared() {
        Spectrum squared = new Spectrum(this);
        squared.mult(squared);
        return squared;
    }

    /**
     * square this spectrum's components.
     */
    public void square() {
        mult(this);
    }

    /**
     * Components of this spectrum are bounded by given range.
     * @param min lower tolerated spectrum bound
     * @param max upper tolerated spectrum bound
     */
    public void clamp(float min, float max) {
        r = Math.min(max, Math.max(min, r));
        g = Math.min(max, Math.max(min, g));
        b = Math.min(max, Math.max(min, b));
    }

    /**
     * return Luminance of spectrum by returning
     * Y component of spectrum in YUV color coordinate system
     * Y = 0,299*R + 0,587*G + 0,114*B.
     * @return Luminance of spectrum.
     */
    public float luminance() {
        return 0.299f*r + 0.587f*g + 0.114f*b;
    }

    /**
     * Get normalized and clamped RGB value represented as int
     * We assume that each color channel is encoded as 8bit number
     * @return 24 rgb color representation
     */
    public int normalizedClamped24BitRGB() {
        Spectrum s = new Spectrum(this);
        s.clamp(0,1);
        return ((int)(255.f*s.r) << 16) | ((int)(255.f*s.g) << 8) | ((int)(255.f*s.b) << 0);

    }
}
