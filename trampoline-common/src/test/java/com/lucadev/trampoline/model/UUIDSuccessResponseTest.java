package com.lucadev.trampoline.model;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * Test {@link UUIDSuccessResponse}
 *
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 8-12-18
 */
public class UUIDSuccessResponseTest {

    @Test
    public void shouldSucceedUUID() {
        UUID expected = UUID.randomUUID();
        UUIDSuccessResponse resp = new UUIDSuccessResponse(expected, true);
        assertEquals(expected, resp.getId());
    }

    @Test
    public void shouldSucceedUUIDWithMessage() {
        UUID expected = UUID.randomUUID();
        UUIDSuccessResponse resp = new UUIDSuccessResponse(expected, true, "testmessage");
        assertEquals(expected, resp.getId());
    }

    @Test
    public void shouldSucceedOkSuccess() {
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), true);
        assertEquals(true, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedEmptyMessageWithOkSuccess() {
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), true);
        assertEquals("ok", successResponse.getMessage());
    }

    @Test
    public void shouldSucceedFailSuccess() {
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), false);
        assertEquals(false, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedEmptyMessageWithFailSuccess() {
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), false);
        assertEquals("error", successResponse.getMessage());
    }

    @Test
    public void shouldSucceedOkSuccessWithMessage() {
        String msg = "test string";
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), true, msg);
        assertEquals(true, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedFailSuccessWithMessage() {
        String msg = "test string";
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), false, msg);
        assertEquals(false, successResponse.isSuccess());
    }

    @Test
    public void shouldSucceedMessageWithOkSuccess() {
        String msg = "test string";
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), true, msg);
        assertEquals(msg, successResponse.getMessage());
    }

    @Test
    public void shouldSucceedMessageWithFailSuccess() {
        String msg = "test string";
        UUIDSuccessResponse successResponse = new UUIDSuccessResponse(UUID.randomUUID(), false, msg);
        assertEquals(msg, successResponse.getMessage());
    }

}