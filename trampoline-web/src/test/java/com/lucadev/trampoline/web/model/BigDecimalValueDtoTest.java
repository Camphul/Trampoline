package com.lucadev.trampoline.web.model;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class BigDecimalValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		BigDecimal expected = new BigDecimal("4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		BigDecimal expected = new BigDecimal("314.15");
		BigDecimalValueDto dto = new BigDecimalValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedIsPositive_0() {
		BigDecimal value = new BigDecimal("0.0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should still be positive. See #isNonZeroPositive for > 0", dto.isPositive());
	}

	@Test
	public void shouldSucceedIsPositive() {
		BigDecimal value = new BigDecimal("4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be positive.", dto.isPositive());
	}

	@Test
	public void shouldSucceedIsPositive_false() {
		BigDecimal value = new BigDecimal("-4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be negative.", dto.isPositive());
	}


	@Test
	public void shouldSucceedIsNegative_0() {
		BigDecimal value = new BigDecimal("0.0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should still be negative. See #isNonZeroNegative for > 0", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsNegative() {
		BigDecimal value = new BigDecimal("-4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be negative.", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsNegative_false() {
		BigDecimal value = new BigDecimal("4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be negative.", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsZero() {
		BigDecimal value = new BigDecimal("0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be zero.", dto.isZero());
	}

	@Test
	public void shouldSucceedIsZero_negative0() {
		BigDecimal value = new BigDecimal("-0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be zero.", dto.isZero());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive_0_false() {
		BigDecimal value = new BigDecimal("0.0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive() {
		BigDecimal value = new BigDecimal("4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be positive.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive_false() {
		BigDecimal value = new BigDecimal("-4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative_0_false() {
		BigDecimal value = new BigDecimal("0.0");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be positive.", dto.isNonZeroNegative());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative() {
		BigDecimal value = new BigDecimal("-4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertTrue("Value should be Negative.", dto.isNonZeroNegative());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative_false() {
		BigDecimal value = new BigDecimal("4.2");
		BigDecimalValueDto dto = new BigDecimalValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroNegative());
	}
}