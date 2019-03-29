package com.lucadev.trampoline.security.abac.spel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import static org.junit.Assert.assertEquals;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 19-12-18
 */
public class SpelAttributeConverterTest {

	private SpelAttributeConverter attributeConverter;

	@Before
	public void setup() {
		attributeConverter = new SpelAttributeConverter();
	}

	@After
	public void tearDown() {
		attributeConverter = null;
	}

	@Test
	public void shouldConvertToDatabaseColumn() {
		ExpressionParser expressionParser = new SpelDeserializer().getElParser();
		String expected = "true && true";
		Expression expr = expressionParser.parseExpression(expected);
		assertEquals(expected, attributeConverter.convertToDatabaseColumn(expr));
	}

	@Test
	public void shouldConvertToEntityAttribute() {
		String expected = "true && true";
		Expression result = attributeConverter.convertToEntityAttribute(expected);
		assertEquals(expected, result.getExpressionString());
	}


}