package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalSettingsTest {


    @Test
    void testNotExistCfg() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/test-doesnot-exist.yml");
        Assertions.assertArrayEquals(new String[0], load.requestMask.get());
    }

    @Test
    void testWrongProp() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/test.yml");
        Assertions.assertArrayEquals(new String[0], load.requestMask.get());
    }

    @Test
    void testProp1() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/test1.yml");
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{""}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,"}, load.requestMask.get());
    }

    @Test
    void testEmptyAsNone() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/test2.yml");
        Assertions.assertArrayEquals(new String[0], load.headers.get());
        Assertions.assertArrayEquals(new String[0], load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[0], load.requestMask.get());
        Assertions.assertArrayEquals(new String[0], load.bodyMethods.get());

        Assertions.assertFalse(load.testFilePrefix);
    }

    @Test
    void testLists() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/test3.yml");
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{"http", "http2"}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,",
                "test",
                "test2",
                "test4"
        }, load.requestMask.get());
        load = GlobalSettings.load("src/test/resources/test4.yml");
        assertNotNull(load);
        load = GlobalSettings.load("src/test/resources/test5.yml");
        assertNotNull(load);
        load = GlobalSettings.load("src/test/resources/test-badyml.yml");
        assertNotNull(load);

    }

    @Test
    void testBodyMethods() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/testBodyMethods.yml");

        Assertions.assertArrayEquals(new String[]{"CUSTOM"}, load.bodyMethods.get());
    }


    @Test
    void testFilePrefix() {
        GlobalSettings.AnystubCfg load = GlobalSettings.load("src/test/resources/testFilePrefix.yml");

        Assertions.assertTrue(load.testFilePrefix);
    }

}