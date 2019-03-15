package com.lucadev.trampoline.security.abac.spel;

import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 19-12-18
 */
public class SpelDeserializerTest {

	@Test
	public void shouldCreateElParser() {
		assertTrue(new SpelDeserializer().getElParser() instanceof SpelExpressionParser);
	}

}