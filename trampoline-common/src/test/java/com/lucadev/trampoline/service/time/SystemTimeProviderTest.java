package com.lucadev.trampoline.service.time;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:Luca.Camphuisen@hva.nl">Luca Camphuisen</a>
 * @since 21-4-18
 */
public class SystemTimeProviderTest {

    private SystemTimeProvider systemTimeProvider;

    @Before
    public void before() {
        this.systemTimeProvider = new SystemTimeProvider();
    }

    @After
    public void after() {
        this.systemTimeProvider = null;
    }

    @Test
    public void shouldSucceedMatchUnix() {
        long expected = System.currentTimeMillis();
        long result = systemTimeProvider.unix();
        assertAcceptedRange(expected, result);
    }

    @Test
    public void shouldSucceedMatchDate() {
        long expected = System.currentTimeMillis();
        long result = systemTimeProvider.now().getTime();
        assertAcceptedRange(expected, result);
    }

    private void assertAcceptedRange(long expected, long result) {
        //100ms difference accepted
        long ACCEPTED_DIFFERENCE = 100;
        assertTrue("Measured result too low", result >= expected - ACCEPTED_DIFFERENCE);
        assertTrue("Measured result too high", result <= expected + ACCEPTED_DIFFERENCE);
    }

}