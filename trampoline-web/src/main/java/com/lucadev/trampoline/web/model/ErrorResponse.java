package com.lucadev.trampoline.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/9/19
 */
@Getter
@ToString
@AllArgsConstructor
public class ErrorResponse implements Serializable {

	private final int status;
	private final String message;
	private final String details;
}
