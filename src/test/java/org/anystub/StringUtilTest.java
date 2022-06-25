package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class StringUtilTest {

    @Test
    void testToCharacterString() {
        String s = StringUtil.toCharacterString("{Привет:}\"".getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals("{Привет:}\"", s);
    }


}