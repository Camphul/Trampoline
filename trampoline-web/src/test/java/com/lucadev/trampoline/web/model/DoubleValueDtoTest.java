package com.lucadev.trampoline.web.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Model unit test.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class DoubleValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		double expected = 4.2;
		DoubleValueDto dto = new DoubleValueDto(expected);
		assertEquals(expected, dto.getValue(), 0);
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		double expected = -4.2;
		DoubleValueDto dto = new DoubleValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.getValue(), 0);
	}

}