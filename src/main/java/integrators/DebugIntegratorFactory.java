package integrators;

import base.Integrator;
import base.IntegratorFactory;
import base.Scene;

/**
 * Makes a {@link DebugIntegrator}.
 * Created by simplaY on 04.01.2015.
 */
public class DebugIntegratorFactory implements IntegratorFactory {
    @Override
    public Integrator make(Scene scene) {
        return new DebugIntegrator(scene);
    }

    @Override
    public void prepareScene(Scene scene) {}
}
