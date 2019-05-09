package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Model unit test.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class IntValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		int expected = 42;
		IntValueDto dto = new IntValueDto(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		int expected = -42;
		IntValueDto dto = new IntValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.getValue());
	}

}