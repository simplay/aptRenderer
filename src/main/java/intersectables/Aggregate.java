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

    @Override
    public HitRecord intersect(Ray ray) {
        // Resets previous determined {@link base.HitRecord} and line_param value to to default values.
        HitRecord closestHitRecord = HitSentinel.getInstance();
        float closestLineParam = Float.MAX_VALUE;

        Iterator<Intersectable> iterator = iterator();
        while (iterator.hasNext()) {
            Intersectable sceneObject = iterator.next();
            HitRecord hit = sceneObject.intersect(ray);

            if (hit.hasIntersection()) {

                // relax values: Update closest hit-record if a closer has found in aggregated list.
                if (hit.getT() < closestLineParam && hit.getT() > 0f) {
                    closestLineParam = hit.getT();
                    closestHitRecord = hit;
                }
            }
        }
        return closestHitRecord;
    }

    /**
     * Iterator over all intersectable objects aggregated in this object
     *
     * @return Iterator of Intersectable instances
     */
    public abstract Iterator<Intersectable> iterator();

    /**
     * @return number of aggregated intersectable instances.
     */
    public abstract int length();
}
