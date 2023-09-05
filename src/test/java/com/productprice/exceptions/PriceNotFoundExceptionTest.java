package com.productprice.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PriceNotFoundExceptionTest {

    @Test
    public void testDefaultContructorShouldWork() {
        PriceNotFoundException ex = new PriceNotFoundException();
        assertNotNull(ex);
    }

    @Test
    public void testMessageContructorShouldWork() {
        PriceNotFoundException ex = new PriceNotFoundException("message");
        assertNotNull(ex);
    }

    @Test
    public void testExceptionContructorShouldWork() {
        RuntimeException originalEx = new RuntimeException("mensaje Business");
        PriceNotFoundException ex = new PriceNotFoundException(originalEx);
        assertNotNull(ex);
    }
}