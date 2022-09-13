package org.anystub;


import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class SettingsUtil {

    /**
     * checks if URL should trigger saving request body
     * finds settings in the stack
     * @param url url to test
     * @return
     */
    public static boolean matchBodyRule(String url) {
        return matchBodyRule(url, AnySettingsHttpExtractor.httpSettings());
    }

    /**
     * checks if URL should trigger saving request body
     * get settings from parameters
     * @param url url to test
     * @param settings test settings
     * @return
     */
    public static boolean matchBodyRule(String url, AnySettingsHttp settings) {
        return stream(settings.bodyTrigger())
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
