package com.lucadev.trampoline.data.gdpr.repository;

import com.lucadev.trampoline.data.gdpr.PersonalData;
import com.lucadev.trampoline.data.gdpr.crypto.StringCrypto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Method interceptor which encrypts if necessary.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/2/19
 */
@Slf4j
@RequiredArgsConstructor
public class CompliantRepositoryMethodInterceptor implements MethodInterceptor {

	private final StringCrypto stringCrypto;

	// Cache to fasten up invocation.
	private final Map<String, int[]> METHOD_INTERCEPTS = new HashMap<>();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String methodDescription = getMethodDescription(invocation.getMethod());
		// Check if the method description is registered in the map.
		// If it is not we can simply return the invocation.
		if (!this.METHOD_INTERCEPTS.containsKey(methodDescription)) {
			return invocation.proceed();
		}

		// Else we will parse the arguments and apply encryption where necessary.
		Object[] arguments = invocation.getArguments();
		for (int paramIndex : this.METHOD_INTERCEPTS.get(methodDescription)) {
			arguments[paramIndex] = this.stringCrypto
					.encrypt((String) arguments[paramIndex]);
		}

		// Proceed
		return invocation.proceed();
	}

	/**
	 * Registers methods which we will intercept. Will improve performance.
	 * @param method method to cache.
	 */
	public void registerQueryMethodForIntercept(Method method) {
		// Fetch param indices that are annotated with @PersonalData
		int[] indices = resolvePersonalDataParameterIndices(method.getParameters());

		// Check if we found any params at all that are annotated.
		if (indices.length == 0) {
			throw new IllegalStateException(
					"Cannot register method which does not include @PersonalData");
		}

		// Get the method description
		String methodDescription = getMethodDescription(method);
		// Put the indices annotated with @PersonalData in a map entry with the method
		// description as key.
		this.METHOD_INTERCEPTS.put(methodDescription, indices);
		log.debug("Registered query method {} in cache.", methodDescription);
	}

	/**
	 * Resolve parameter indices which need encryption.
	 * @param parameters the method parameters.
	 * @return array of indices which require encryption.
	 */
	private int[] resolvePersonalDataParameterIndices(Parameter[] parameters) {
		List<Integer> indices = new ArrayList<>(parameters.length);

		// Loop through params
		for (int i = 0; i < parameters.length; i++) {
			Parameter parameter = parameters[i];
			// Check if we need to encrypt
			if (AnnotationUtils.findAnnotation(parameter, PersonalData.class) != null) {
				if (!parameter.getType().equals(String.class)) {
					throw new IllegalArgumentException("Argument \"" + parameter.getName()
							+ "\" with @PersonalData must be of type String.");
				}
				indices.add(i);
			}
		}
		return indices.stream().mapToInt(Integer::intValue).toArray();
	}

	/**
	 * Not to be confused with Java method descriptions. This simply contains classname
	 * and methodname.
	 * @param method method to get description of.
	 * @return classname and method name.
	 */
	private String getMethodDescription(Method method) {
		return method.getDeclaringClass().getName() + "#" + method.getName();
	}

}
