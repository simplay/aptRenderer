package constants;

import base.HitRecord;

/**
 * /**
 * HitRecord Singleton object
 * Used to represent a no intersection object in order to avoid null checks.
 * Created by simplaY on 05.01.2015.
 */
public class HitSentinel extends HitRecord {

    private static HitRecord instance = null;

    private HitSentinel() {
        super();
    }

    public static HitRecord getInstance() {
        if (instance == null) {
            instance = new HitSentinel();
        }
        return instance;
    }
}