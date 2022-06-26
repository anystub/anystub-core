package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@AnyStubId
@AnySettingsHttp(headers = "classHeader")
class AnySettingsHttpExtractorTest {

    @Test
    @AnySettingsHttp
    void discoveryTest() {
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertFalse(anySettingsHttp.overrideGlobal());
        assertFalse(anySettingsHttp.allHeaders());
        assertEquals(0, anySettingsHttp.headers().length);
        assertEquals(0, anySettingsHttp.bodyTrigger().length);
        assertEquals(0, anySettingsHttp.bodyMask().length);

    }

    @Test
    @AnySettingsHttp(overrideGlobal = true,
        allHeaders = true,
            headers = {"accept", "Content-Type"},
            bodyTrigger = "http",
            bodyMask = "111"
    )
    void discoveryAllPropTest() {
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertTrue(anySettingsHttp.overrideGlobal());
        assertTrue(anySettingsHttp.allHeaders());
        assertArrayEquals(new String[]{"accept", "Content-Type"}, anySettingsHttp.headers());
        assertArrayEquals(new String[]{"http"}, anySettingsHttp.bodyTrigger());
        assertArrayEquals(new String[]{"111"}, anySettingsHttp.bodyMask());
    }

    static class OneException extends RuntimeException {

    }
    @Test
    @AnySettingsHttp(overrideGlobal = true,
            allHeaders = true,
            headers = {"accept1", "Content-Type"},
            bodyTrigger = "http",
            bodyMask = "111"
    )
    void discoveryAllPropInLambdaTest() {
        Assertions.assertThrows(OneException.class, ()->{
            AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
            assertTrue(anySettingsHttp.overrideGlobal());
            assertTrue(anySettingsHttp.allHeaders());
            assertArrayEquals(new String[]{"accept1", "Content-Type"}, anySettingsHttp.headers());
            assertArrayEquals(new String[]{"http"}, anySettingsHttp.bodyTrigger());
            assertArrayEquals(new String[]{"111"}, anySettingsHttp.bodyMask());
            throw new OneException();
        });
    }


    static class ProxyClass {
        AnySettingsHttp get(int a, String b) {
            AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
            return anySettingsHttp;
        }
    }

    @Test
    @AnySettingsHttp(overrideGlobal = true,
            allHeaders = true,
            headers = {"accept", "Content-Type"},
            bodyTrigger = "http2",
            bodyMask = "111"
    )
    void discoveryAllPropInInnerClassTest() {
        AnySettingsHttp anySettingsHttp = new ProxyClass().get(1,"");
        assertTrue(anySettingsHttp.overrideGlobal());
        assertTrue(anySettingsHttp.allHeaders());
        assertArrayEquals(new String[]{"accept", "Content-Type"}, anySettingsHttp.headers());
        assertArrayEquals(new String[]{"http2"}, anySettingsHttp.bodyTrigger());
        assertArrayEquals(new String[]{"111"}, anySettingsHttp.bodyMask());

    }

    @Test
    public void testDiscoverSettingsClass() {
        AnyStubId s = AnyStubFileLocator.discoverFile();
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertNotNull(anySettingsHttp);
        assertFalse(anySettingsHttp.allHeaders());
        assertArrayEquals(new String[]{"classHeader"}, anySettingsHttp.headers());
    }
    @Test
    @AnySettingsHttp(headers = {"test1", "test2"}, allHeaders = false, bodyTrigger = {"http://", "234"})
    public void testDiscoverSettings1() {
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertNotNull(anySettingsHttp);
        Object headers = new String[]{"test1", "test2"};
        assertFalse(anySettingsHttp.allHeaders());
        assertArrayEquals(new String[]{"test1", "test2"}, anySettingsHttp.headers());
        assertArrayEquals(new String[]{"http://", "234"}, anySettingsHttp.bodyTrigger());
    }

    @Test
    @AnySettingsHttp(headers = {})
    public void testDiscoverSettings2() {
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertNotNull(anySettingsHttp);
        Object headers = new String[]{"test1", "test2"};
        assertFalse(anySettingsHttp.allHeaders());
        assertArrayEquals(new String[]{}, anySettingsHttp.headers());
    }

    @Test
    @AnySettingsHttp(allHeaders = true)
    void testSettingsInTestLambda() {
        AnySettingsHttp anySettingsHttp = AnySettingsHttpExtractor.discoverSettings();
        assertNotNull(anySettingsHttp);
        assertTrue(anySettingsHttp.allHeaders());

        Runnable r = () -> {
            AnySettingsHttp anySettingsHttp1 = AnySettingsHttpExtractor.discoverSettings();
            assertNotNull(anySettingsHttp1);
            assertTrue(anySettingsHttp1.allHeaders());
        };

        r.run();
    }

}