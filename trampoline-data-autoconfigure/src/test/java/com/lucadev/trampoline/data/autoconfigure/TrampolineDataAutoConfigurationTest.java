package com.lucadev.trampoline.data.autoconfigure;

import com.lucadev.trampoline.data.configuration.TrampolinePersistenceConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class TrampolineDataAutoConfigurationTest {

	private AnnotationConfigApplicationContext context;

	@Before
	public void setUp() throws Exception {
		context = new AnnotationConfigApplicationContext();
	}

	@After
	public void tearDown() throws Exception {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void registersTrampolineDataConfigurationAutomatically() {
		this.context.register(TrampolineDataAutoConfiguration.class);
		this.context.refresh();
		TrampolinePersistenceConfiguration config = this.context.getBean(TrampolinePersistenceConfiguration.class);
		assertThat(config, instanceOf(TrampolinePersistenceConfiguration.class));
	}

}