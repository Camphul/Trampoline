package com.lucadev.trampoline.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Int value DTO.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntValueDto {

	private int value;

	@Override
	public String toString() {
		return String.valueOf(this.value);
	}

}
