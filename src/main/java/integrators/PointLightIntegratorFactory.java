package integrators;

import base.Integrator;
import base.IntegratorFactory;
import base.Scene;

/**
 * Makes a {@link PointLightIntegrator}.
 * simplaY on 05.01.2015.
 */
public class PointLightIntegratorFactory implements IntegratorFactory {

    @Override
    public Integrator make(Scene scene) {
        return new PointLightIntegrator(scene);
    }

    @Override
    public void prepareScene(Scene scene) {
    }
}
