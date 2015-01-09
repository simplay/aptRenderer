package intersectables;

import base.Material;
import base.Ray;
import constants.BelongsTo;
import constants.BoundaryType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * A CSG node combines two CSG solids using a set operation, such as intersection,
 * addition, or subtraction.
 * Created by simplaY on 09.01.2015.
 */
public class CSGNode extends CSGSolid {

    protected final CSGSolid left;
    protected final CSGSolid right;
    protected final OperationType operationType;

    public CSGNode(CSGSolid left, CSGSolid right, OperationType operationType, Material material) {
        super(material);
        this.left = left;
        this.right = right;
        this.operationType = operationType;
    }

    /**
     * Get boundaries of intersection intervals. The main idea is to first get the boundaries
     * of the two CSG solids to be combined. Then, the boundaries are merged according
     * to the set operation specified by the node.
     *
     * @param ray the ray that intersects the CSG solid
     * @return
     */
    @Override
    ArrayList<IntervalBoundary> getIntervalBoundaries(Ray ray) {
        ArrayList<IntervalBoundary> combined = new ArrayList<IntervalBoundary>();

        // Get interval boundaries of left and right children
        ArrayList<IntervalBoundary> leftIntervals = left.getIntervalBoundaries(ray);
        ArrayList<IntervalBoundary> rightIntervals = right.getIntervalBoundaries(ray);

        // Tag interval boundaries with left or right node
        Iterator<IntervalBoundary> it = leftIntervals.iterator();
        while (it.hasNext()) {
            IntervalBoundary b = it.next();
            b.setBelongsTo(BelongsTo.LEFT);
        }
        it = rightIntervals.iterator();
        while (it.hasNext()) {
            IntervalBoundary b = it.next();
            b.setBelongsTo(BelongsTo.RIGHT);
        }

        // Combine interval boundaries and sort
        combined.addAll(leftIntervals);
        combined.addAll(rightIntervals);
        Collections.sort(combined);

        // Traverse interval boundaries and set inside/outside
        // according to Boolean set operation to combine the two child solids
        boolean inLeft, inRight;
        inLeft = false;
        inRight = false;

        it = combined.iterator();
        while (it.hasNext()) {
            IntervalBoundary b = it.next();

            if (b.getBelongsTo() == BelongsTo.LEFT) {
                if (b.getType() == BoundaryType.START)
                    inLeft = true;
                else
                    inLeft = false;
            }
            if (b.getBelongsTo() == BelongsTo.RIGHT) {
                if (b.getType() == BoundaryType.START)
                    inRight = true;
                else
                    inRight = false;
            }

            switch (operationType) {
                case INTERSECT: {
                    if (inLeft && inRight)
                        b.setType(BoundaryType.START);
                    else
                        b.setType(BoundaryType.END);
                    break;
                }
                case SUBTRACT: {
                    if (inLeft && !inRight)
                        b.setType(BoundaryType.START);
                    else
                        b.setType(BoundaryType.END);

                    // In a subtract operation, the subtracted solid is turned inside out,
                    // or it "switches sign", so we need to flip its normal direction
                    if (b.getBelongsTo() == BelongsTo.RIGHT && b.getHitRecord().hasIntersection()) {
                        b.getHitRecord().getNormal().negate();
                    }
                    break;
                }
                case ADD: {
                    if (inLeft || inRight)
                        b.setType(BoundaryType.START);
                    else
                        b.setType(BoundaryType.END);
                    break;
                }
            }
        }

        // Clean up
        it = combined.iterator();
        IntervalBoundary prev = new IntervalBoundary();
        prev.setType(BoundaryType.END);
        IntervalBoundary b;
        while (it.hasNext()) {
            b = it.next();
            if (b.getType() == prev.getType())
                it.remove();
            prev.setType(b.getType());
        }

        return combined;
    }

    /**
     * CSG Node Operations
     */
    public enum OperationType {
        INTERSECT,
        ADD,
        SUBTRACT;
    }
}
