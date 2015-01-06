package intersectables;

import base.Intersectable;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A list of intersectable instances.
 * Usually, a scene consists of many different intersectable instances.
 * Created by simplaY on 05.01.2015.
 */
public class IntersectableList extends Aggregate {

    // Data-Structure containing all intersectable instances in this list.
    private LinkedList<Intersectable> container;

    public IntersectableList() {
        container = new LinkedList<Intersectable>();
    }

    /**
     * Add an intersectable instance to the list
     *
     * @param intersectable intersectable to insert.
     */
    public void add(Intersectable intersectable) {
        container.add(intersectable);
    }

    @Override
    public Iterator<Intersectable> iterator() {
        return container.iterator();
    }

    @Override
    public int length() {
        return container.size();
    }
}
