package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SettingsUtilTest {
    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "local")
    void bodyRuleTest() {
        boolean local_glo = SettingsUtil.matchBodyRule("local glo");
        assertTrue(local_glo);
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "local")
    void methodBodyRuleTest() {
        boolean local_glo = SettingsUtil.matchBodyRule("POST", "local glo");
        assertTrue(local_glo);
    }

    @Test
    @AnyStubId
    @AnySettingsHttp(bodyTrigger = "local", bodyMask = "xxx")
    void maskBodyTest() {
        String masked = SettingsUtil.maskBody("lakjsd,zmncxxx qweq");
        assertEquals("lakjsd,zmnc... qweq", masked);
    }

    @Test
    @AnySettingsHttp(bodyMask = {"secret", "password", "....-.*\\.\\d{2,10}", "\\d{4}-\\d{1,2}-\\d{1,2}"})
    void multiMaskBodyTest() {
        String msg = String.format("hypothetical request containing a secret data like a password, "+
                        "or a variable timestamp: %s in the middle of request. date\":[%s]",
                LocalDateTime.now().toString(), LocalDate.now().toString());
        String masked = SettingsUtil.maskBody(msg);
        assertEquals("hypothetical request containing a ... data like a ..., "+
                "or a variable timestamp: ... in the middle of request. date\":[...]", masked, msg);
    }

    @Test
    void noMaskBodyTest() {

        String msg = String.format("hypothetical request containing a secret data like a password, "+
                        "or a variable timestamp: %s in the middle of request. date\":[%s]",
                LocalDateTime.now().toString(), LocalDate.now().toString());
        String masked = SettingsUtil.maskBody(msg);
        Assertions.assertFalse(masked.contains("..."));
    }
}