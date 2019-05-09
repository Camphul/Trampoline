package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test model
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class StringValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		String expected = "Trampoline testing string";
		StringValueDto dto = new StringValueDto(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		String expected = "Trampoline testing string";
		StringValueDto dto = new StringValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.getValue());
	}
}