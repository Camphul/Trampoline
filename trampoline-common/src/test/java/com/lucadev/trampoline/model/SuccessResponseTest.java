package com.lucadev.trampoline.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Way too many tests for a simple model
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SuccessResponseTest {

    @Test
    public void shouldSucceedOkSuccess() {
        SuccessResponse successResponse = new SuccessResponse(true);
        assertEquals(true, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedEmptyMessageWithOkSuccess() {
        SuccessResponse successResponse = new SuccessResponse(true);
        assertEquals("ok", successResponse.getMessage());
    }

    @Test
    public void shouldSucceedFailSuccess() {
        SuccessResponse successResponse = new SuccessResponse(false);
        assertEquals(false, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedEmptyMessageWithFailSuccess() {
        SuccessResponse successResponse = new SuccessResponse(false);
        assertEquals("error", successResponse.getMessage());
    }

    @Test
    public void shouldSucceedOkSuccessWithMessage() {
        String msg = "test string";
        SuccessResponse successResponse = new SuccessResponse(true, msg);
        assertEquals(true, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedFailSuccessWithMessage() {
        String msg = "test string";
        SuccessResponse successResponse = new SuccessResponse(false, msg);
        assertEquals(false, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedMessageWithOkSuccess() {
        String msg = "test string";
        SuccessResponse successResponse = new SuccessResponse(true, msg);
        assertEquals(msg, successResponse.getMessage());
    }

    @Test
    public void shouldSucceedMessageWithFailSuccess() {
        String msg = "test string";
        SuccessResponse successResponse = new SuccessResponse(false, msg);
        assertEquals(msg, successResponse.getMessage());
    }

}