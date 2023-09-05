package com.productprice.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BrandNotFoundExceptionTest {

    @Test
    public void testDefaultContructorShouldWork() {
        BrandNotFoundException ex = new BrandNotFoundException();
        assertNotNull(ex);
    }

    @Test
    public void testMessageContructorShouldWork() {
        BrandNotFoundException ex = new BrandNotFoundException("message");
        assertNotNull(ex);
    }

    @Test
    public void testExceptionContructorShouldWork() {
        RuntimeException originalEx = new RuntimeException("mensaje Business");
        BrandNotFoundException ex = new BrandNotFoundException(originalEx);
        assertNotNull(ex);
    }
}