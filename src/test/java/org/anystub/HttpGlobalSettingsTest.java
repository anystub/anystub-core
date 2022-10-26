package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class HttpGlobalSettingsTest {


    @Test
    void testNotExistCfg() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test-doesnot-exist.yml");
        Assertions.assertArrayEquals(new String[0], load.bodyMask.get());
    }

    @Test
    void testWrongProp() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test.yml");
        Assertions.assertArrayEquals(new String[0], load.bodyMask.get());
    }

    @Test
    void testProp1() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test1.yml");
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{""}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,"}, load.bodyMask.get());
    }

    @Test
    void testEmptyAsNone() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test2.yml");
        Assertions.assertArrayEquals(new String[0], load.bodyTrigger.get());
    }

    @Test
    void testLists() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test3.yml");
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{"http", "http2"}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,",
                "test",
                "test2",
                "test4"
        }, load.bodyMask.get());
        load = HttpGlobalSettings.load("src/test/resources/test4.yml");
        assertNotNull(load);
        load = HttpGlobalSettings.load("src/test/resources/test5.yml");
        assertNotNull(load);
        load = HttpGlobalSettings.load("src/test/resources/test-badyml.yml");
        assertNotNull(load);

    }

    @Test
    void testBodyMethods() {
        HttpGlobalSettings.AnystubCfg load = HttpGlobalSettings.load("src/test/resources/testBodyMethods.yml");

        Assertions.assertArrayEquals(new String[]{"CUSTOM"}, load.bodyMethods.get());
    }


}