package com.lucadev.trampoline.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * BigDecimal value DTO.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 5/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BigDecimalValueDto {

	private BigDecimal value;

	/**
	 * If the number is 0 or higher.
	 * @return larger or equal to 0
	 */
	public boolean isPositive() {
		return BigDecimal.ZERO.compareTo(value) <= 0;
	}

	/**
	 * If the number is 0 or lower.
	 * @return lower or equal to 0
	 */
	public boolean isNegative() {
		return BigDecimal.ZERO.compareTo(value) >= 0;
	}

	/**
	 * If the number is zero.
	 * @return is zero
	 */
	public boolean isZero() {
		return BigDecimal.ZERO.equals(value);
	}

	/**
	 * If the number is above 0
	 * @return larger than 0
	 */
	public boolean isNonZeroPositive() {
		return BigDecimal.ZERO.compareTo(value) < 0;
	}

	/**
	 * Number is under 0
	 * @return lower than 0
	 */
	public boolean isNonZeroNegative() {
		return BigDecimal.ZERO.compareTo(value) > 0;
	}

}
