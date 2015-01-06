package base;

/**
 * Makes an {@link Integrator}.
 * Created by simplaY on 03.01.2015.
 */
public interface IntegratorFactory {
    public Integrator make(Scene scene);

    public void prepareScene(Scene scene);
}
