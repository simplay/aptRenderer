package base;

/**
 * An intersectable supports ray-surface intersection.
 * Created by simplaY on 03.01.2015.
 */
public interface Intersectable {
    /**
     * Implement ray-surface intersection in this method. Implementations of this
     * method need to make and return a {@link HitRecord} correctly, following
     * the conventions of assumed for {@link HitRecord}.
     *
     * @param ray the ray used for intersection testing
     * @return a hit record, should return null if there is no intersection
     */
    public HitRecord intersect(Ray ray);

    // public BoundingBox getBoundingBox();
}
