package constants;

import base.ShadingSample;

/**
 * Created by simplaY on 07.01.2015.
 */
public class ShadingSampleSentinel extends ShadingSample {
    private static ShadingSampleSentinel instance = null;

    private ShadingSampleSentinel() {
        super();
    }

    public static ShadingSampleSentinel getInstance() {
        if (instance == null) {
            instance = new ShadingSampleSentinel();
        }
        return instance;
    }
}
