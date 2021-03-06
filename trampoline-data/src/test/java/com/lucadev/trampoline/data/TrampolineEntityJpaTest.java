package com.lucadev.trampoline.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

/**
 * JpaTest to test the base entity functionality
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 21-4-18
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@EnableJpaAuditing
@EnableCaching
@ActiveProfiles(profiles = "test")
public class TrampolineEntityJpaTest {

	// Max time difference in tests. in ms
	private static final long TIME_BOUNDS = 100;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private SimpleTrampolineEntityRepository repository;

	private SimpleTrampolineEntity newEntity(String payload) {
		return new SimpleTrampolineEntity(payload);
	}

	/**
	 * Just check if we can persist an entity without trouble
	 */
	@Test
	public void shouldSucceedPersistNewEntity() {
		String payload = "testPersist1";
		SimpleTrampolineEntity entity = newEntity(payload);
		SimpleTrampolineEntity persisted = repository.save(entity);
		assertEquals(payload, persisted.getPayload());
	}

	/**
	 * Check if the created date matches the time of persisting
	 */
	@Test
	public void shouldSucceedCreatedDateCheckNewEntity() {
		String payload = "testPersist1";
		SimpleTrampolineEntity entity = newEntity(payload);
		long timeStamp = System.currentTimeMillis();
		SimpleTrampolineEntity persisted = repository.save(entity);
		assertBounds(timeStamp, persisted.getCreated().toEpochMilli(), TIME_BOUNDS);
	}

	/**
	 * Should fail because the entity data does not meet the set contraints
	 */
	@Test(expected = Exception.class)
	public void shouldFailPersistNewEntityBecauseConstraintViolation() {
		SimpleTrampolineEntity persisted = repository.save(newEntity(null));
		repository.flush();
		assertNull("Entity should not be persisted due to constraints", persisted);
	}

	/**
	 * Should be able to update entity
	 */
	@Test
	public void shouldSucceedUpdateEntity() {
		String payload = "testPersist1";
		SimpleTrampolineEntity entity = newEntity(payload);
		entityManager.persist(entity);
		entityManager.flush();
		String updatedPayload = "haha it has been updated";
		entity.setPayload(updatedPayload);
		SimpleTrampolineEntity updatedEntity = repository.saveAndFlush(entity);
	}

	/**
	 * Check if the last update date of the entity matches
	 */
	@Test
	public void shouldSucceedUpdateDateCheckUpdatedEntity() throws InterruptedException {
		String payload = "testPersist2";
		SimpleTrampolineEntity entity = newEntity(payload);
		entityManager.persistAndFlush(entity).getUpdated().toEpochMilli();
		sleep(2, TimeUnit.SECONDS);
		String updatedPayload = "haha it has been updated now! ";
		entity.setPayload(updatedPayload);
		long timestamp = System.currentTimeMillis();
		SimpleTrampolineEntity updatedEntity = repository.saveAndFlush(entity);
		assertBounds(timestamp, updatedEntity.getUpdated().toEpochMilli(), TIME_BOUNDS);
	}

	private void assertBounds(long expected, long result, long bounds) {
		assertTrue(result >= expected - bounds);
		assertTrue(result <= expected + bounds);
	}

	private void sleep(long duration, TimeUnit timeUnit) {
		long startWait = System.currentTimeMillis();
		await().atMost(timeUnit.toMillis(duration) + TimeUnit.SECONDS.toMillis(1),
				TimeUnit.MILLISECONDS).until(System::currentTimeMillis,
						greaterThan(startWait + timeUnit.toMillis(duration)));
	}

}
