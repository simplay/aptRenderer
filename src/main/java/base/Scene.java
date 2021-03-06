package base;

/**
 * Defines scene properties that need to be made accessible to the renderer.
 * Created by simplaY on 31.12.2014.
 */
public abstract class Scene {

    // base path for rendered images. It is still possible to specify sub-paths in the file-path name.
    private final String basePath = "output/";

    // Path and name of rendered image file.
    protected String filePathName;

    // Samples per pixel used for rendering.
    protected int spp;

    // Image pixel width.
    protected int width;

    // Image pixel height.
    protected int height;

    // Represents the eye of a viewer.
    private Camera camera;

    // Some kind filter applied to rendered image.
    protected Film film;

    // Initialized a particular integrator.
    protected IntegratorFactory integratorFactory;

    // Defines a particular sampler used for sampling image locations.
    protected SamplerFactory samplerFactory;

    // Defines how to map the color the rendered image to an 8-bit monitor.
    protected Tonemapper tonemapper;

    // Top level scene object encapsulating hierarchically all scene objects.
    private Intersectable root;

    // Scene light list storing all light geometries.
    private LightList lightList;

    /**
     * Every scene has to have specified a width, height and spp.
     *
     * @param width  image width.
     * @param height image height.
     * @param spp    samples per pixels.
     */
    public Scene(int width, int height, int spp) {
        this.width = width;
        this.height = height;
        this.spp = spp;
        camera = this.initializeCamera();
        root = this.initializeGeometries();
        lightList = this.initializeLights();
    }

    protected abstract Camera initializeCamera();

    protected abstract Intersectable initializeGeometries();

    protected abstract LightList initializeLights();


    public String getFilePathName() {
        return basePath + filePathName;
    }

    public int getSpp() {
        return spp;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Camera getCamera() {
        return camera;
    }

    public Film getFilm() {
        return film;
    }

    public IntegratorFactory getIntegratorFactory() {
        return integratorFactory;
    }

    public SamplerFactory getSamplerFactory() {
        return samplerFactory;
    }

    public Tonemapper getTonemapper() {
        return tonemapper;
    }

    public Intersectable getIntersectable() {
        return root;
    }

    public LightList getLightList() {
        return lightList;
    }

}
