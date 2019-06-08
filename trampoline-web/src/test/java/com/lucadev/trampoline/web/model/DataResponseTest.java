package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Model unit test.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class DataResponseTest {

	@Test
	public void shouldSucceedGetData() {
		String expected = "Trampoline testing string";
		DataResponse dto = new DataResponse(expected);
		assertEquals(expected, dto.getData());
	}

}