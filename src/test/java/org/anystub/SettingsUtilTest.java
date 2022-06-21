package org.anystub;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsUtilTest {
    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "local")
    void bodyRuleTest() {
        HttpGlobalSettings.globalBodyTrigger = new String[]{"global"};

        boolean local_glo = SettingsUtil.matchBodyRule("local glo");
        assertTrue(local_glo);
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "local")
    void maskBodyTest() {
        HttpGlobalSettings.globalBodyMask = new String[]{"xxx"};

        String masked = SettingsUtil.maskBody("lakjsd,zmncxxx qweq");
        assertEquals("lakjsd,zmnc... qweq", masked);
    }

}