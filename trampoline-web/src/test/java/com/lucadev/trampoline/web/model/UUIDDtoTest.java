package com.lucadev.trampoline.web.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Test {@link UUIDDto}
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8-12-18
 */
public class UUIDDtoTest {

	@Test
	public void shouldSucceedUUID() {
		UUID expected = UUID.randomUUID();
		UUIDDto resp = new UUIDDto(expected);
		assertEquals(expected, resp.getId());
	}

}