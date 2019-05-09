package com.lucadev.trampoline.web.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Model unit test
 *
 * @author <a href="mailto:luca@camphuisen.com">Luca Camphuisen</a>
 * @since 5/9/19
 */
public class ListResponseTest {

	private List<String> expectedList;

	private ListResponse listResponse;

	@Before
	public void setUp() throws Exception {
		expectedList = Arrays.asList("hello", "world");
		listResponse = new ListResponse(expectedList);
	}

	@After
	public void tearDown() throws Exception {
		expectedList = null;
		listResponse = null;
	}

	@Test
	public void shouldSucceedGetContent() {
		assertTrue(listResponse.getContent().containsAll(expectedList));
	}

	@Test(expected = NullPointerException.class)
	public void shouldFailConstruct_null() {
		new ListResponse(null);
	}

	@Test
	public void shouldSucceedGetSize() {
		assertEquals(expectedList.size(), listResponse.getSize());
	}

	@Test
	public void shouldSucceedGetSizeIncorrect() {
		assertFalse(expectedList.size() + 1 == listResponse.getSize());
	}

	@Test
	public void shouldSucceedIsEmpty() {
		ListResponse emptyListResponse = new ListResponse(Collections.emptyList());
		assertTrue(emptyListResponse.isEmpty());
	}

	@Test
	public void shouldSucceedIsEmpty_false() {
		assertFalse("ListResponse#isEmpty should return false", listResponse.isEmpty());
	}

}