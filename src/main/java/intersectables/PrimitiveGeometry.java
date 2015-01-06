package intersectables;

import base.HitRecord;
import base.Intersectable;
import base.Material;
import base.Ray;

/**
 * Any Primitive Geometry is an intersectable and has a certain material assigned to.
 * Created by simplaY on 06.01.2015.
 */
public abstract class PrimitiveGeometry implements Intersectable {

    // material assigned to any PrimitiveGeometry.
    // NB: Cannot be set after initialization.
    protected final Material material;

    /**
     * Material that should be set to Geometry.
     *
     * @param material
     */
    public PrimitiveGeometry(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public abstract HitRecord intersect(Ray ray);
}
