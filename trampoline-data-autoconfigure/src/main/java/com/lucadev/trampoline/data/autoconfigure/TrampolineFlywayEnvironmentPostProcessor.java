package com.lucadev.trampoline.data.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Environment postprocessor used to configure trampoline migration scripts for flyway.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 12/9/19
 */
@Slf4j
public class TrampolineFlywayEnvironmentPostProcessor
		implements EnvironmentPostProcessor, Ordered {

	private static final String DIALECT_KEY = "spring.jpa.properties.hibernate.dialect";

	private static final String FLYWAY_CHECK_LOCATIONS_KEY = "spring.flyway.check-location";

	private static final String FLYWAY_LOCATIONS_KEY = "spring.flyway.locations";

	private static final String FLYWAY_BASELINE_VERSION_KEY = "spring.flyway.baseline-version";

	private static final String PROPERTY_SOURCE_NAME = "defaultProperties";

	private static final String DEFAULT_MIGRATION_LOCATION_BASE = "classpath:db/migration/";

	private static final String DEFAULT_TRAMPOLINE_MIGRATION_LOCATION_BASE = "classpath:db/trampoline/";

	private static final String H2_DIR_POSTFIX = "h2";

	private static final String MYSQL_DIR_POSTFIX = "mysql";

	private static final boolean FLYWAY_CHECK_LOCATIONS = true;

	private static final int FLYWAY_BASELINE_VERSION = 1;

	private static final String DEFAULT_HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL8Dialect";

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
		if (!isFlywayPresent()) {
			log.debug(
					"Flyway not present in classpath. Not running environment post processor.");
			return;
		}
		String databaseProvider = findMigrationDirPostFix(environment);

		log.debug("Running trampoline flyway environment postprocessor.");

		Map<String, Object> overrides = new HashMap<>();
		overrides.put(FLYWAY_LOCATIONS_KEY,
				DEFAULT_TRAMPOLINE_MIGRATION_LOCATION_BASE + databaseProvider + ","
						+ DEFAULT_MIGRATION_LOCATION_BASE
						+ findMigrationDirPostFix(environment));
		overrides.put(FLYWAY_CHECK_LOCATIONS_KEY, FLYWAY_CHECK_LOCATIONS);
		// Using custom baseline version we can put all trampoline queries into V__0
		overrides.put(FLYWAY_BASELINE_VERSION_KEY, FLYWAY_BASELINE_VERSION);
		merge(environment.getPropertySources(), overrides);
		log.debug("Configured flyway locations for trampoline.");
	}

	private String findMigrationDirPostFix(ConfigurableEnvironment environment) {
		String dialect = environment.getProperty(DIALECT_KEY, DEFAULT_HIBERNATE_DIALECT);

		if (!DEFAULT_HIBERNATE_DIALECT.equals(dialect)) {
			if (dialect.toLowerCase().contains(H2_DIR_POSTFIX)) {
				log.debug("Switched to H2 migrations for flyway.");
				return H2_DIR_POSTFIX;
			}
			else if (dialect.toLowerCase().contains(MYSQL_DIR_POSTFIX)) {
				log.debug(
						"Switched to mysql migrations for flyway from unknown mysql dialect: {}",
						dialect);
				return MYSQL_DIR_POSTFIX;
			}
		}
		log.warn(
				"Unkown dialect. Cannot decide on migrations location for Trampoline. Using MYSQL location as default.");
		return MYSQL_DIR_POSTFIX;
	}

	/**
	 * Check if flyway is in the classpath/available.
	 * @return true when present.
	 */
	private boolean isFlywayPresent() {
		return ClassUtils.isPresent("org.flywaydb.core.Flyway", null);
	}

	/**
	 * Merges custom property source with defaults.
	 * @param propertySources environment property source.
	 * @param map custom configuration.
	 * @see PropertySource
	 */
	private void merge(MutablePropertySources propertySources, Map<String, Object> map) {
		MapPropertySource target = null;
		if (propertySources.contains(PROPERTY_SOURCE_NAME)) {
			PropertySource<?> source = propertySources.get(PROPERTY_SOURCE_NAME);
			if (source instanceof MapPropertySource) {
				target = (MapPropertySource) source;
				for (String key : map.keySet()) {
					if (!target.containsProperty(key)) {
						Object val = map.get(key);
						log.debug("Adding property key {} with value {}", key, val);
						target.getSource().put(key, val);
					}
				}
			}
		}
		if (target == null) {
			log.debug("Configuring completely new property source.");
			target = new MapPropertySource(PROPERTY_SOURCE_NAME, map);
		}
		if (!propertySources.contains(PROPERTY_SOURCE_NAME)) {
			log.debug("Appending property source.");
			propertySources.addLast(target);
		}
	}

	/**
	 * Order for this environment post processor.
	 * @return loading order.
	 */
	@Override
	public int getOrder() {
		return 40;
	}

}
