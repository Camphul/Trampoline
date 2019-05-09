package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test model.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class BooleanValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		boolean expected = true;
		BooleanValueDto dto = new BooleanValueDto(expected);
		assertEquals(expected, dto.isValue());
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		boolean expected = true;
		BooleanValueDto dto = new BooleanValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.isValue());
	}

}