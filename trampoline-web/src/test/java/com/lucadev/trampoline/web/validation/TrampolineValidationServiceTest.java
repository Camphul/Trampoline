package com.lucadev.trampoline.web.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 3/10/19
 */
public class TrampolineValidationServiceTest {

	private TrampolineValidationService validationService;
	private BindingResult bindingResult;

	@Before
	public void before() {
		validationService = new TrampolineValidationService();
		bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		FieldError fieldError = new FieldError("testObj", "testField","testDescription");
		List<FieldError> errors = new ArrayList<>();
		errors.add(fieldError);
		when(bindingResult.getFieldErrors()).thenReturn(errors);
	}

	@After
	public void after() {
		validationService = null;
		bindingResult = null;
	}

	@Test
	public void createBindingResultResponseSuccess() {
		BindingResultResponse response = validationService.createBindingResultResponse(bindingResult);

		assertEquals(1, response.getFieldErrors().size());
		assertEquals("testDescription", response.getFieldErrors().get("testField"));
	}

	@Test(expected = NullPointerException.class)
	public void createBindingResultResponseError() {
		validationService.createBindingResultResponse(null);
	}
}