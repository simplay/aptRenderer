package base;

import java.util.ArrayList;
import java.util.Random;

/**
 * A list of light sources represented as {@link LightGeometry} instances
 * that allows to retrieve random light source.
 */
public class LightList extends ArrayList<LightGeometry> {
    private final Random randomizer = new Random();

    /**
     * Get a random light source stored within this list.
     *
     * @return get one random light source.
     */
    public LightGeometry getRandomLightSource() {
        int idx = randomizer.nextInt(this.size());
        return this.get(idx);
    }
}