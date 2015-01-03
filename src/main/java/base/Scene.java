package base;

/**
 * Defines scene properties that need to be made accessible to the renderer.
 * Created by simplaY on 31.12.2014.
 */
public abstract class Scene {

    // name of rendered image file
    protected String filePathName;

    // samples per pixel
    protected int spp;

    // image pixel width
    protected int width;

    // image pixel height
    protected int height;

    protected Camera camera;
    protected Film film;


    // TODO create their getters
    protected IntegratorFactory integratorFactory;
    protected SamplerFactory samplerFactory;
    protected Tonemapper tonemapper;

    //protected Intersectable root;
    //protected LightList lightList;


    public String getFilePathName() {
        return filePathName;
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


}
