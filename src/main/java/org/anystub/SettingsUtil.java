package org.anystub;


import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

public class SettingsUtil {

    /**
     * checks if URL should trigger saving request body
     * finds settings in the stack
     * @param url url to test
     * @return
     */
    @Deprecated
    public static boolean matchBodyRule(String url) {
        return matchBodyRule("POST", url, AnySettingsHttpExtractor.httpSettings());
    }

    /**
     * checks if URL should trigger saving request body
     * finds settings in the stack
     * @param method method of the request
     * @param url url to test
     * @return true if method and url match setting
     */
    public static boolean matchBodyRule(String method, String url) {
        return matchBodyRule(method, url, AnySettingsHttpExtractor.httpSettings());
    }

    /**
     * checks if URL should trigger saving request body
     * get settings from parameters
     * @param url url to test
     * @param settings test settings
     * @return
     */
    public static boolean matchBodyRule(String httpMethod, String url, AnySettingsHttp settings) {
        if(!asList(settings.bodyMethods()).contains(httpMethod)) {
            return false;
        }
        if (stream(settings.bodyTrigger())
                .filter(rule -> rule.startsWith("-"))
                .anyMatch(rule -> url.contains(rule.substring(1)))){
            return false;
        }

        if (settings.bodyTrigger().length == 0 ) {
            return true;
        }

        return stream(settings.bodyTrigger())
                .filter(rule -> !rule.startsWith("-"))
                .anyMatch(url::contains);
    }

    /**
     * masks a string
     * @param s string to mask
     * @return masked string
     */
    public static String maskBody(String s) {
        return maskBody(s, AnySettingsHttpExtractor.httpSettings());
    }
    /**
     * combines all available rules from HttpGlobalSettings.globalBodyMask and AnySettingsHttp.bodyMask
     * and replace match with ellipsis (...).
     * If rules not specified - no replacements performed
     * @param s string to mask
     * @param settings test settings
     * @return masked string
     */
    public static String maskBody(String s, AnySettingsHttp settings) {

        if (settings.bodyMask().length==0) {
            return s;
        }

        String combinedRule;
        if (settings.bodyMask().length>1) {
            combinedRule = stream(settings.bodyMask()).
                    map(r->String.format("(%s)", r))
                    .collect(Collectors.joining("|"));
        } else {
            combinedRule = settings.bodyMask()[0];
        }

        return s.replaceAll(combinedRule, "...");
    }

}
