package base;

/**
 * Created by simplaY on 31.12.2014.
 */
public abstract class Scene {

    // name of rendered image file
    protected String fileName;

    // samples per pixel
    protected int spp;

    // image pixel width
    protected int width;

    // image pixel height
    protected int height;

    protected Camera camera;
    protected Film film;
    protected IntegratorFactory integratorFactory;
    protected SamplerFactory samplerFactory;
    protected Tonemapper tonemapper;
    protected Intersectable root;
    protected LightList lightList;


}
