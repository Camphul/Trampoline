package com.lucadev.trampoline.security.abac.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Attribute converter for Spring expressions.
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 22-5-18
 */
@Converter
public class SpelAttributeConverter implements AttributeConverter<Expression, String> {

	private static final ExpressionParser PARSER = new SpelDeserializer().getElParser();

	@Override
	public String convertToDatabaseColumn(Expression expression) {
		if (expression == null) {
			return "";
		}
		return expression.getExpressionString();
	}

	@Override
	public Expression convertToEntityAttribute(String s) {
		if (s == null) {
			s = "";
		}
		return PARSER.parseExpression(s);
	}
}
