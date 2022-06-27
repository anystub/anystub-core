package org.anystub;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

class StringUtilTest {

    @Test
    void testToCharacterString() {
        String s = StringUtil.toCharacterString("{Привет:}\"".getBytes(StandardCharsets.UTF_8));
        Assertions.assertEquals("{Привет:}\"", s);
    }

    @Test
    void testRecoverBinaryData() {
        byte[] tests;
        tests = StringUtil.recoverBinaryData("test");
        Assertions.assertArrayEquals("test".getBytes(), tests);

        tests = StringUtil.recoverBinaryData("TEXT test");
        Assertions.assertArrayEquals("test".getBytes(), tests);

        tests = StringUtil.recoverBinaryData("BASE64 Ym9keSBoZXgC");
        Assertions.assertArrayEquals(("body hex"+(char)2).getBytes(), tests);


    }

    @Test
    void testStreams() throws IOException {
        try (InputStream test = StringUtil.recoverInputStream("test")) {
            String s = StringUtil.toCharacterString(test);
            Assertions.assertEquals("test", s);

        }

        StringReader stringReader = new StringReader("test");
        String s = StringUtil.toCharacterString(stringReader);
        Assertions.assertEquals("test", s);

    }

}