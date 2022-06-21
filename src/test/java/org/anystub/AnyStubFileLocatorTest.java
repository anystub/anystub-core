package org.anystub;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AnyStubId
public class AnyStubFileLocatorTest {

    @Test
    @AnyStubId
    public void testcase() {
        assertEquals("testcase.yml", AnyStubFileLocator.discoverFile().filename());
    }

    @Test
    @AnyStubId(filename = "special")
    public void testcase1() {
        assertEquals("special.yml", AnyStubFileLocator.discoverFile().filename());
    }

    @Test
    public void testname() {
        assertEquals("AnyStubFileLocatorTest.yml", AnyStubFileLocator.discoverFile().filename());

    }

}