package com.lucadev.trampoline.reflect;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utils class similar to Spring
 * {@link org.springframework.core.annotation.AnnotationUtils} but for methods.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 10/18/19
 */
public final class MethodReflectionUtils {

	/**
	 * Get a map with parameter and value(argument).
	 * @param method method to make a map of.
	 * @param arguments arguments supplied.
	 * @return map of params and args.
	 */
	public static Map<Parameter, Object> mapParametersToArguments(Method method,
			Object[] arguments) {
		int length = method.getParameterCount();
		if (length != arguments.length) {
			throw new IllegalArgumentException(
					"Argument size does not match parameter count.");
		}
		Map<Parameter, Object> argMap = new HashMap<>();
		doWithParameters(method, (param, index) -> argMap.put(param, arguments[index]));
		return argMap;
	}

	/**
	 * Map map key from type parameter to string.
	 * @param maps the map.
	 * @return remapped keys.
	 */
	public static Map<String, Object> mapToParameterNameKeys(
			Map<Parameter, Object> maps) {
		return maps.entrySet().stream().collect(
				Collectors.toMap(entry -> entry.getKey().getName(), Map.Entry::getValue));
	}

	/**
	 * Find the first parameter of a method annotated with a given annotation.
	 *
	 * @param method          the method to check parameters of.
	 * @param annotationClass annotation class.
	 * @param <A>             annotation type.
	 * @return parameter which is annotated or null.
	 */
	public static <A extends Annotation> Parameter findFirstParameterWithAnnotation(
			Method method, Class<A> annotationClass) {
		return Arrays.stream(method.getParameters()).filter((parameter) -> {
			A annotation = AnnotationUtils.findAnnotation(parameter, annotationClass);
			if (annotation != null) {
				return true;
			}
			return false;
		}).findFirst().orElse(null);
	}

	/**
	 * Run through parameters.
	 * @param method
	 * @param parameterCallback
	 */
	public static void doWithParameters(Method method,
			ParameterCallback parameterCallback) {
		for (int i = 0; i < method.getParameters().length; i++) {
			parameterCallback.accept(method.getParameters()[i], i);
		}
	}

}
