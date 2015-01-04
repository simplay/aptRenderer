package samplers;

import base.Sampler;
import base.SamplerFactory;

/**
 * Makes a {@link OneSampler}.
 * Created by simplaY on 04.01.2015.
 */
public class OneSamplerFactory implements SamplerFactory {
    @Override
    public Sampler make() {
        return new OneSampler();
    }
}
