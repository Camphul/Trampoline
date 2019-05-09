package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class MessageResponseTest {

	/**
	 * Contents should indeed match
	 */
	@Test
	public void shouldSucceedMessage() {
		String expectedMessage = "Hello world!";
		MessageResponse messageResponse = new MessageResponse(expectedMessage);
		assertEquals(expectedMessage, messageResponse.getMessage());
	}

	@Test
	public void shouldSucceedNullMessage() {
		MessageResponse messageResponse = new MessageResponse(null);
		assertNull(messageResponse.getMessage());
	}

}