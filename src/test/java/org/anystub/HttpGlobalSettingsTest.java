package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class HttpGlobalSettingsTest {




    @Test
    void xx() throws FileNotFoundException {
        InputStream input = new FileInputStream("src/test/resources/test1.yml");
        Constructor safeConstructor = new Constructor(AnystubCfg.class);
//        TypeDescription typeDescription = new TypeDescription(AnystubCfg.class);
//        typeDescription.addPropertyParameters("headers", String.class, String[].class);
////        typeDescription.addPropertyParameters("headers", V.class);
//
//        safeConstructor.addTypeDescription(typeDescription);
//        safeConstructor.addTypeDescription(new TypeDescription(AnystubCfg.class));
        Yaml yaml = new Yaml(safeConstructor);
        AnystubCfg load = yaml.loadAs(input, AnystubCfg.class);
        assertTrue(load.allHeaders);

        }

    @Test
    void testNotExistCfg() {
        AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test-doesnot-exist.yml");
        Assertions.assertFalse(load.allHeaders);
        Assertions.assertArrayEquals(new String[0], load.bodyMask.get());
    }

    @Test
    void testWrongProp() {
        AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test.yml");
        Assertions.assertTrue(load.allHeaders);
        Assertions.assertArrayEquals(new String[0], load.bodyMask.get());
    }

    @Test
    void testProp1() {
        AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test1.yml");
        Assertions.assertTrue(load.allHeaders);
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{""}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,"}, load.bodyMask.get());
    }

    @Test
    void testEmptyAsNone() {
        AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test2.yml");
        Assertions.assertArrayEquals(new String[0], load.bodyTrigger.get());
    }

    @Test
    void testLists() {
        AnystubCfg load = HttpGlobalSettings.load("src/test/resources/test3.yml");
        Assertions.assertArrayEquals(new String[]{"headers"}, load.headers.get());
        Assertions.assertArrayEquals(new String[]{"http", "http2"}, load.bodyTrigger.get());
        Assertions.assertArrayEquals(new String[]{"password: .{2,10}\\,",
                "test",
                "test2",
                "test4"
        }, load.bodyMask.get());
    }



}