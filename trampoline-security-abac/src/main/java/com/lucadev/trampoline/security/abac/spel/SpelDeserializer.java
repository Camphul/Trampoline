package com.lucadev.trampoline.security.abac.spel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.IOException;

/**
 * Deserializes a String into a Spring expression object
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 20-5-18
 */
public class SpelDeserializer extends StdDeserializer<Expression> {

    /**
     * The compiler mode for SpeL
     */
    public static final SpelCompilerMode SPEL_COMPILER_MODE = SpelCompilerMode.MIXED;
    private final ExpressionParser elParser;

    public SpelDeserializer() {
        this(null);
    }

    protected SpelDeserializer(Class<?> vc) {
        super(vc);
        elParser = createSpelParser();
    }

    private ExpressionParser createSpelParser() {
        SpelParserConfiguration parserConfiguration = new SpelParserConfiguration(SPEL_COMPILER_MODE, null);
        return new SpelExpressionParser(parserConfiguration);
    }

    @Override
    public Expression deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String expresion = jp.getCodec().readValue(jp, String.class);
        return elParser.parseExpression(expresion);
    }

    public ExpressionParser getElParser() {
        return elParser;
    }
}