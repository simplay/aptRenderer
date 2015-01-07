package constants;

/**
 * Helper Identifier when dealing with binary structures to denote a left and a right child.
 * For the case when the order matters.
 * For example, we use this to denote an interval as the left or right interval,
 * when applying CSG operations.
 * Created by simplaY on 07.01.2015.
 */
public enum BelongsTo {
    LEFT,
    RIGHT,
    NOT_SET;
}
