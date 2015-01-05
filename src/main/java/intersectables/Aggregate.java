package intersectables;

import base.HitRecord;
import base.Intersectable;
import base.Ray;
import constants.HitSentinel;

import java.util.Iterator;

/**
 * A group of {@link Intersectable} objects.
 * Created by simplaY on 05.01.2015.
 */
public abstract class Aggregate implements Intersectable {

    // current best ray line parameter closest to camera.
    // Remember: this parameter is used to determine the intersection position.
    private float closestLineParam;

    // Current closest hit point when checking for intersection
    private HitRecord closestHitRecord;

    // Threshold for determining intersection.
    private final float EPS = 1E-5f;

    @Override
    public HitRecord intersect(Ray ray) {
        this.flush();
        Iterator<Intersectable> iterator = iterator();
        while (iterator.hasNext()) {
            Intersectable sceneObject = iterator.next();
            HitRecord currentHit = sceneObject.intersect(ray);
            if (currentHit.hasIntersection()) {
                this.relax(currentHit);
            }
        }
        return closestHitRecord;
    }

    /**
     * Resets previous determined {@link base.HitRecord} and line_param value to to default values.
     */
    private void flush() {
        closestLineParam = Float.MAX_VALUE;
        closestHitRecord = HitSentinel.getInstance();
    }

    /**
     * Update closest hit-record if a closer has found in aggregated list.
     */
    private void relax(HitRecord hit) {
        if (hit.getT() < closestLineParam && hit.getT() > EPS) {
            closestLineParam = hit.getT();
            closestHitRecord = hit;
        }
    }

    /**
     * Iterator over all intersectable objects aggregated in this object
     * @return Iterator of Intersectable instances
     */
    public abstract Iterator<Intersectable> iterator();

    /**
     * @return number of aggregated intersectable instances.
     */
    public abstract int length();
}
