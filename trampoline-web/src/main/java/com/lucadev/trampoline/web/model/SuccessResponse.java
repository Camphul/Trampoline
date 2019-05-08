package com.lucadev.trampoline.web.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Extension of {@link MessageResponse} to include a status flag to notify of success or failure.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@Getter
@ToString
@EqualsAndHashCode
public class SuccessResponse extends MessageResponse {

	/**
	 * Construct a {@code SuccessResponse} which returns an "ok" response.
	 */
	public SuccessResponse() {
		super("ok");
	}
}
