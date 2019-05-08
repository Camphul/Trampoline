package com.lucadev.trampoline.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * A DTO which contains a {@link UUID}. This is useful when wanting to reference an id and
 * a success state.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UUIDDto {

	private UUID id;

}
