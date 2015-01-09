package intersectables;

import base.HitRecord;
import constants.BelongsTo;
import constants.BoundaryType;
import constants.HitSentinel;

/**
 * Created by simplaY on 07.01.2015.
 */
public class IntervalBoundary implements Comparable<IntervalBoundary> {

    // intersection parameter assigned to this Interval Boundary.
    private final float t;

    // The type of intersection boundary. Is either START or END.
    private BoundaryType type;

    // Hit record of intersection stored in this IntervalBoundary.
    private final HitRecord hitRecord;

    // denotes the type of node in an hierarchical (binary tree) structure. Is either LEFT or RIGHT.
    private BelongsTo belongsTo;

    /**
     * Creates an interval boundary.
     * Used when traversing hierarchical structures containing this and other intervals of CSG instances.
     * @param t Intersection parameter.
     * @param type is it a starting or ending interval boundary.
     * @param hitRecord hit record at intersection point.
     * @param belongsTo Identifier in hierarchical Structure.
     */
    public IntervalBoundary(float t, BoundaryType type, HitRecord hitRecord, BelongsTo belongsTo) {
        this.t = t;
        this.type = type;
        this.hitRecord = hitRecord;
        this.belongsTo = belongsTo;
    }

    /**
     * Regular Constructor.
     * @param t Intersection parameter.
     * @param type is it a starting or ending interval boundary.
     * @param hitRecord hit record at intersection point.
     */
    public IntervalBoundary(float t, BoundaryType type, HitRecord hitRecord) {
        this(t, type, hitRecord, BelongsTo.NOT_SET);
    }

    /**
     * Empty Interal
     */
    public IntervalBoundary() {
        this(0, null, HitSentinel.getInstance());
    }

    /**
     * In this method, the intersection parameter of this interval boundary is compared to the intersection parameter of another interval boundary.
     * IT Returns an:
     * + NEGATIVE integer IF this interval boundary's intersection parameter is smaller than the one from the comparison interval boundary.
     * + ZERO IF the intersection parameter of this and the other interval boundary are the same.
     * + POSITIVE integer IF this interval boundary's intersection parameter is greater than the one from the comparison interval boundary.
     *
     * @param other Interval boundary we want to compare with this interval.
     * @return a flag indicating whether the intersection parameter of this object is smaller than, equal to, or greater than a given reference interval boundary instance
     */
    @Override
    public int compareTo(IntervalBoundary other) {
        if (t < other.getT()) {
            return -1;
        } else if (t == other.getT()) {
            return 0;
        } else {
            return 1;
        }
    }

    public float getT() {
        return t;
    }

    public BoundaryType getType() {
        return type;
    }

    public HitRecord getHitRecord() {
        return hitRecord;
    }

    public BelongsTo getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(BelongsTo belongsTo) {
        this.belongsTo = belongsTo;
    }

    public void setType(BoundaryType type) {
        this.type = type;
    }
}
