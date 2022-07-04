package org.anystub;

import org.anystub.mgmt.BaseManagerImpl;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * keeps global settings for Http interceptors
 * set them globally before running tests
 * no thread-safe
 */
public class HttpGlobalSettings {

    static {
        String path = new File(BaseManagerImpl.DEFAULT_PATH, "config.yml").getPath();
        AnystubCfg load = load(path);
        globalAllHeaders = load.allHeaders;
        globalHeaders = load.headers.get();
        globalBodyTrigger = load.bodyTrigger.get();
        globalBodyMask = load.bodyMask.get();
    }


    public static AnystubCfg load(String s) {
        try (InputStream input = new FileInputStream(s)) {
            Constructor constructor = new Constructor(AnystubCfg.class);
            PropertyUtils propertyUtils = new PropertyUtils();
            propertyUtils.setSkipMissingProperties(true);
            constructor.setPropertyUtils(propertyUtils);
            Yaml yaml = new Yaml(constructor);
            AnystubCfg anystubCfg = yaml.loadAs(input, AnystubCfg.class);
            if(anystubCfg.headers == null) {
                anystubCfg.headers = new AnystubCfg.StringOrArray();
            }
            if(anystubCfg.bodyTrigger == null) {
                anystubCfg.bodyTrigger = new AnystubCfg.StringOrArray();
            }
            if(anystubCfg.bodyMask == null) {
                anystubCfg.bodyMask = new AnystubCfg.StringOrArray();
            }

            return anystubCfg;

        } catch (IOException ex) {
            Logger.getLogger(HttpGlobalSettings.class.getName())
                    .finest(() -> String.format("can't load default properties from %s: %s", s, ex.getMessage()));
        } catch (YAMLException ex) {
            Logger.getLogger(HttpGlobalSettings.class.getName())
                    .warning(()->String.format("can't load default properties from %s: %s", s, ex.getMessage()));
        }

        return new AnystubCfg();
    }




    /**
     * enables to save all request headers in stub
     * use it in debug mode - to manually validate headers
     */
    public static final boolean globalAllHeaders ;

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
    public static final String[] globalBodyMask;

}
