package com.lucadev.trampoline.reflect;

import java.lang.reflect.Parameter;
import java.util.function.BiConsumer;

/**
 * Functional interface to handle method parameter callbacks. Integer is the index of the
 * parameter within the method parameters.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/18/19
 */
@FunctionalInterface
public interface ParameterCallback extends BiConsumer<Parameter, Integer> {

}
