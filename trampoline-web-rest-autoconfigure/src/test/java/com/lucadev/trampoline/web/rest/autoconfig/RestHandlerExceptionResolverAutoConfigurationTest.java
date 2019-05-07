package com.lucadev.trampoline.web.rest.autoconfig;

import com.lucadev.trampoline.web.model.MessageResponse;
import com.lucadev.trampoline.web.rest.error.exception.handler.AbstractRestExceptionHandler;
import com.lucadev.trampoline.web.rest.error.exception.handler.RestExceptionHandler;
import com.lucadev.trampoline.web.rest.error.exception.internal.ResponseEntityResponseProcessor;
import com.lucadev.trampoline.web.rest.error.exception.resolver.RestHandlerExceptionResolver;
import com.lucadev.trampoline.web.rest.error.exception.resolver.TrampolineRestHandlerExceptionResolver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests to see if the autoconfiguration delivers the correct bean.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 3/10/19
 */
public class RestHandlerExceptionResolverAutoConfigurationTest {

	private AnnotationConfigApplicationContext context;
	private ResponseEntityResponseProcessor responseProcessor;
	private List<RestExceptionHandler> exceptionHandlers;

	@Before
	public void setUp() {
		context = new AnnotationConfigApplicationContext();
		responseProcessor = mock(ResponseEntityResponseProcessor.class);
		exceptionHandlers = new ArrayList<>();
		exceptionHandlers.add(new ExampleRestExceptionHandler());
	}

	@After
	public void tearDown() {
		if (this.context != null) {
			this.context.close();
		}
		responseProcessor = null;
		exceptionHandlers = null;
	}

	@Test
	public void registersRestHandlerExceptionResolverAutomatically() {
		this.context.registerBean(RestHandlerExceptionResolverAutoConfiguration.class, exceptionHandlers, responseProcessor);
		this.context.refresh();

		RestHandlerExceptionResolver resolver = this.context.getBean(RestHandlerExceptionResolver.class);


		assertThat(resolver, instanceOf(TrampolineRestHandlerExceptionResolver.class));
	}

	@Test
	public void registersCustomRestHandlerExceptionResolverAutomatically() {
		this.context.registerBean(CustomExceptionResolverConfig.class);
		this.context.registerBean(RestHandlerExceptionResolverAutoConfiguration.class, exceptionHandlers, responseProcessor);
		this.context.refresh();

		RestHandlerExceptionResolver resolver = this.context.getBean(RestHandlerExceptionResolver.class);


		assertThat(resolver, instanceOf(ExampleRestHandlerExceptionResolver.class));
	}


	@Configuration
	protected static class CustomExceptionResolverConfig {
		@Bean
		public RestHandlerExceptionResolver restHandlerExceptionResolver() {
			return new ExampleRestHandlerExceptionResolver();
		}
	}

	protected static class ExampleRestExceptionHandler extends AbstractRestExceptionHandler<IOException, MessageResponse> {

		/**
		 * Construct new handler.
		 */
		public ExampleRestExceptionHandler() {
			super(IOException.class, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		@Override
		public MessageResponse handle(IOException exception, HttpServletRequest request) {
			return new MessageResponse("ok");
		}
	}

	protected static class ExampleRestHandlerExceptionResolver implements RestHandlerExceptionResolver {

		@Override
		public ResponseEntity handleException(Exception ex, HttpServletRequest request) {
			return null;
		}

		@Override
		public <EX extends Exception> void addExceptionHandler(Class<EX> exception, RestExceptionHandler<EX, ?> handler) {

		}

		@Override
		public RestExceptionHandler resolveExceptionHandler(Exception ex) {
			return null;
		}

		@Override
		public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
			return null;
		}
	}

}