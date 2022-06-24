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

public class StringUtil {
    public static final String BASE64_PREFIX = "BASE64 ";
    public static final String TEXT_PREFIX = "TEXT ";

    private StringUtil() {
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
        if (StringUtil.isText(bytes)) {
            String bodyText = new String(bytes, StandardCharsets.UTF_8);
            result = escapeCharacterString(bodyText);
        } else {
            String encode = Base64.getEncoder().encodeToString(bytes);
            result = BASE64_PREFIX + encode;
        }
        return result;
    }

    /**
     * escape text/base64 markers string
     * @param bodyText
     * @return
     */
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

    /**
     * recover binary data from string from stub file
     *
     * @param in string from stub file
     * @return
     */
    public static byte[] recoverBinaryData(String in) {
        if (in.startsWith(TEXT_PREFIX)) {
            return in.substring(TEXT_PREFIX.length()).getBytes();
        } else if (in.startsWith(BASE64_PREFIX)) {
            String base64Entity = in.substring(BASE64_PREFIX.length());
            return Base64.getDecoder().decode(base64Entity);
        } else {
            return in.getBytes();
        }
    }

    /**
     * reads data from inputStream
     * @param inputStream
     * @return
     */
    public static byte[] readStream(InputStream inputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int r;
            while ((r = inputStream.read()) != -1) {
                byteArrayOutputStream.write(r);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new UnsupportedOperationException("failed save InputStream");
        }
    }

    public static String toCharacterString(InputStream inputStream) {
        return StringUtil.toCharacterString(readStream(inputStream));
    }

    public static InputStream recoverInputStream(String in) {
        byte[] bytes = recoverBinaryData(in);
        return new ByteArrayInputStream(bytes);
    }


    public static String toCharacterString(Reader reader) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            int r;
            while ((r = reader.read()) != -1) {
                byteArrayOutputStream.write(r);
            }
            return StringUtil.toCharacterString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new UnsupportedOperationException("failed save InputStream");
        }
    }
}
