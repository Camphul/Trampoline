package com.lucadev.trampoline.security.logging.activity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
@Getter
@ToString
@AllArgsConstructor
public class UserActivityMethodDetails {

	private final String className;
	private final String methodName;
	private final boolean exceptionThrown;
	private final long startExecution;
	private final long finishExecution;

}
