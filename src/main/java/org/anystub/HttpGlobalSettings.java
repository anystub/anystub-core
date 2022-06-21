package org.anystub;

/**
 * keeps global settings for Http interceptors
 * set them globally before running tests
 * no thread-safe
 */
public class HttpGlobalSettings {

    /**
     * enables to save all request headers in stub
     * use it in debug mode - to manually validate headers
     */
    public static boolean globalAllHeaders = false;

    /**
     * list of headers to save for all requests
     */
    public static String[] globalHeaders = {};

    /**
     * patterns, if URL match any of the trigger request body saves in stub
     */
    public static String[] globalBodyTrigger = {};

    /**
     * pattern, to cut off out of the request body when saves in stub
     * Use it to cut off variable part of the request body: ex. timestamp, random sequences, secrets
     */
    public static String[] globalBodyMask = {};

}
