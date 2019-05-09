package com.lucadev.trampoline.web.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Response which wraps an object in a data field. Useful for returning lists and whatnot.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class DataResponse {

	private final Object data;

}
