package com.lucadev.trampoline.web.model;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class BigIntegerValueDtoTest {

	@Test
	public void shouldSucceedGetValueAfterConstructor() {
		BigInteger expected = new BigInteger("42");
		BigIntegerValueDto dto = new BigIntegerValueDto(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedGetValueAfterSetter() {
		BigInteger expected = new BigInteger("314");
		BigIntegerValueDto dto = new BigIntegerValueDto();
		dto.setValue(expected);
		assertEquals(expected, dto.getValue());
	}

	@Test
	public void shouldSucceedIsPositive_0() {
		BigInteger value = new BigInteger("0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should still be positive. See #isNonZeroPositive for > 0", dto.isPositive());
	}

	@Test
	public void shouldSucceedIsPositive() {
		BigInteger value = new BigInteger("42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be positive.", dto.isPositive());
	}

	@Test
	public void shouldSucceedIsPositive_false() {
		BigInteger value = new BigInteger("-42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be negative.", dto.isPositive());
	}


	@Test
	public void shouldSucceedIsNegative_0() {
		BigInteger value = new BigInteger("0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should still be negative. See #isNonZeroNegative for > 0", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsNegative() {
		BigInteger value = new BigInteger("-42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be negative.", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsNegative_false() {
		BigInteger value = new BigInteger("42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be negative.", dto.isNegative());
	}

	@Test
	public void shouldSucceedIsZero() {
		BigInteger value = new BigInteger("0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be zero.", dto.isZero());
	}

	@Test
	public void shouldSucceedIsZero_negative0() {
		BigInteger value = new BigInteger("-0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be zero.", dto.isZero());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive_0_false() {
		BigInteger value = new BigInteger("0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive() {
		BigInteger value = new BigInteger("42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be positive.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroPositive_false() {
		BigInteger value = new BigInteger("-42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroPositive());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative_0_false() {
		BigInteger value = new BigInteger("0");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be positive.", dto.isNonZeroNegative());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative() {
		BigInteger value = new BigInteger("-42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertTrue("Value should be Negative.", dto.isNonZeroNegative());
	}

	@Test
	public void shouldSucceedIsNonZeroNegative_false() {
		BigInteger value = new BigInteger("42");
		BigIntegerValueDto dto = new BigIntegerValueDto(value);
		assertFalse("Value should be negative.", dto.isNonZeroNegative());
	}
	
}