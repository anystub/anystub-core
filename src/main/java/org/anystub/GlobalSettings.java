package org.anystub;

import org.anystub.mgmt.BaseManagerImpl;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * keeps global settings from resources/anystub/config.yml
 * file locator and Http interceptors use the setting
 * loads once before any tests starts
 * immutable
 */
public class GlobalSettings {

    static {
        TestSettings load = ConfigFileUtil.get("config");
        globalHeaders = load.headers;
        globalBodyTrigger = load.bodyTrigger;
        globalRequestMask = load.requestMask;
        globalBodyMethods = load.bodyMethods;
    }

    private GlobalSettings() {
    }


    /**
     * list of headers to save for all requests
     */
    public static final String[] globalHeaders;

    /**
     * patterns, if URL match any of the trigger request body saves in stub
     */
    public static final String[] globalBodyTrigger;

    /**
     * pattern, to cut off out of the request body when saves in stub
     * Use it to cut off variable part of the request body: ex. timestamp, random sequences, secrets
     */
    public static final String[] globalRequestMask;

    /**
     * http-methods to include request body in request key (case-sensitive)
     */
    public static final String[] globalBodyMethods;

}
