package util;

/**
 * Singleton Math helper for solving equations.
 * Created by simplaY on 07.01.2015.
 */
public class BaseMath {
    /**
     * Find zeros @param x of a given quadratic equation of the form ax^2 + bx + c = 0
     * by using the the following formula x_1,2 = [-b +- sqrt(b^2 - 4ac)]/2a
     *
     * @return Zeros of quadratic
     */
    public static Float solveForBestZeroOfQuadratic(float a, float b, float c) {
        float discriminant = b * b - 4f * a * c;
        if (discriminant < 0f) {
            return null;
        }

        float x1 = (float) ((-b + Math.sqrt(discriminant)) / (2f * a));
        float x2 = (float) ((-b - Math.sqrt(discriminant)) / (2f * a));

        if (x1 > 0f && x2 > 0f) {
            return new Float (Math.max(x1, x2));
        } else if (x1 > 0f) {
            return new Float(x1);
        } else if (x2 > 0f) {
            return new Float(x2);
        } else {
            return null;
        }
    }
}
