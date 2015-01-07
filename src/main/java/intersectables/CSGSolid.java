package intersectables;

import base.HitRecord;
import base.Material;
import base.Ray;
import constants.BoundaryType;
import constants.HitSentinel;
import util.VectorMath;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * A CSG solid object that can be intersected by a ray. If a CSG object is intersected
 * by a ray, we determine all intersection intervals and their boundaries, that is, the intervals
 * along the ray where the ray is either inside or outside the object. Each interval has two
 * boundaries, a start and an end, where the ray enters and leaves the solid. The actual
 * intersection point with the object is the first interval boundary where the ray enters the
 * object the first time.
 * Created by simplaY on 07.01.2015.
 */
public abstract class CSGSolid extends PrimitiveGeometry {

    public CSGSolid(Material material) {
        super(material);
    }

    @Override
    public HitRecord intersect(Ray ray) {

        // Get the intersection interval boundaries
        ArrayList<IntervalBoundary> intervalBoundaries = getIntervalBoundaries(ray);

        // Return the first hit in front of the camera, that is, make sure
        // the hit is along the positive ray direction
        Iterator<IntervalBoundary> iterator = intervalBoundaries.iterator();
        while(iterator.hasNext()) {
            HitRecord hit = iterator.next().getHitRecord();

            if(hit.hasIntersection() && hit.getT() > 0.f) {
                hit.setIntersectable(this);
                return hit;
            }
        }

        return HitSentinel.getInstance();
    }

    /**
     * Determines the type of this Boundary.
     * IF it is starting, then the Ray hit the positive side
     * of the tangent plane spanned by the normal.
     * @param hit {@link base.HitRecord} on surface.
     * @param ray {@link base.Ray} that hit the surface
     * @return Is this Boundary Starting or Ending {@link BoundaryType}.
     */
    protected BoundaryType findBoundaryType(HitRecord hit, Ray ray) {
        if (VectorMath.cosTheta(hit.getNormal(), ray.getDirection()) < 0f) {
            return BoundaryType.START;
        } else {
            return BoundaryType.END;
        }
    }

    /**
     * Compute the boundaries of the intersection intervals of this CSG solid with a ray.
     *
     * @param ray the ray that intersects the CSG solid
     * @return boundaries of intersection intervals
     */
    abstract ArrayList<IntervalBoundary> getIntervalBoundaries(Ray ray);
}
