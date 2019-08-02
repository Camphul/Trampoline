package com.lucadev.trampoline.data.gdpr;

import com.lucadev.trampoline.data.gdpr.crypto.FieldDecrypter;
import com.lucadev.trampoline.data.gdpr.crypto.FieldEncrypter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreLoadEvent;
import org.hibernate.event.spi.PreLoadEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

/**
 * Listener for hibernate events and encrypts/decrypts when necessary.
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 8/1/19
 */
@Slf4j
@RequiredArgsConstructor
public class PersonalDataEntityListener
		implements PreLoadEventListener, PreInsertEventListener, PreUpdateEventListener {

	private final EntityManagerFactory entityManagerFactory;

	private final FieldDecrypter fieldDecrypter;

	private final FieldEncrypter fieldEncrypter;

	@PostConstruct
	public void registerListener() {
		SessionFactoryImpl sessionFactory = this.entityManagerFactory
				.unwrap(SessionFactoryImpl.class);
		EventListenerRegistry registry = sessionFactory.getServiceRegistry()
				.getService(EventListenerRegistry.class);
		registry.appendListeners(EventType.PRE_INSERT, this);
		registry.appendListeners(EventType.PRE_UPDATE, this);
		registry.appendListeners(EventType.PRE_LOAD, this);
	}

	@Override
	public void onPreLoad(PreLoadEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		this.fieldDecrypter.decrypt(state, propertyNames, entity);
	}

	@Override
	public boolean onPreInsert(PreInsertEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		this.fieldEncrypter.encrypt(state, propertyNames, entity);
		return false;
	}

	@Override
	public boolean onPreUpdate(PreUpdateEvent event) {
		Object[] state = event.getState();
		String[] propertyNames = event.getPersister().getPropertyNames();
		Object entity = event.getEntity();
		this.fieldEncrypter.encrypt(state, propertyNames, entity);
		return false;
	}

}
