package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test to see if valid.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SuccessResponseTest {

	@Test
	public void shouldSucceedOkMessage() {
		SuccessResponse successResponse = new SuccessResponse();
		assertEquals("ok", successResponse.getMessage());
	}

}