package org.anystub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class Util {
    public static final String BASE64_PREFIX = "BASE64 ";
    public static final String TEXT_PREFIX = "TEXT ";

    private Util() {
    }


    public static boolean isText(String text) {
        return Arrays.stream(text.split("\n"))
                .allMatch(s -> s.matches("\\p{Print}*"));
    }

    public static boolean isText(byte[] symbols) {
        for (byte b : symbols) {
            if ((b < 0x20 || b > 0x7E) && (b !=(byte) 0x0A && b != (byte) 0x0D)) {
                return false;
            }
        }
        return true;
    }

    /**
     * converts binary for keeping in stub-file
     *
     * @param bytes
     * @return
     */
    public static String toCharacterString(byte[] bytes) {
        String result;
        if (Util.isText(bytes)) {
            String bodyText = new String(bytes, StandardCharsets.UTF_8);
            result = escapeCharacterString(bodyText);
        } else {
            String encode = Base64.getEncoder().encodeToString(bytes);
            result = BASE64_PREFIX + encode;
        }
        return result;
    }

    public static String escapeCharacterString(String bodyText) {
        if (bodyText.startsWith("TEXT") || bodyText.startsWith("BASE")) {
            return TEXT_PREFIX + bodyText;
        }
        return bodyText;
    }

    public static String addTextPrefix(String bodyText) {
        return TEXT_PREFIX + bodyText;
    }



    public static String encode(Serializable s) {
        try (ByteArrayOutputStream of = new ByteArrayOutputStream();
             ObjectOutputStream so = new ObjectOutputStream(of)) {
            so.writeObject(s);
            so.flush();
            return Base64.getEncoder().encodeToString(of.toByteArray());
        } catch (IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T extends Serializable> T decode(String s) {
        byte[] decode = Base64.getDecoder().decode(s);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decode);
             ObjectInputStream si = new ObjectInputStream(byteArrayInputStream)) {
            return (T) si.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String[] toArray(Object... keys) {
        String[] sKeys = new String[keys.length];

        for (int i = 0; i < keys.length; i++) {
            sKeys[i] = new EncoderJson<>().encode(keys[i]);
        }
        return sKeys;
    }
}
