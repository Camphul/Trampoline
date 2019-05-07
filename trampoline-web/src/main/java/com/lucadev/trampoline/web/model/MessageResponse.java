package com.lucadev.trampoline.web.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A simple model to return a String message as a model.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class MessageResponse {

	private final String message;

}
