package org.anystub;


import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class SettingsUtil {


    public static boolean matchBodyRule(String url) {

        AnySettingsHttp settings = AnySettingsHttpExtractor.httpSettings();

        return stream(settings.bodyTrigger())
                .anyMatch(url::contains);
    }

    /**
     * combines all available rules from HttpGlobalSettings.globalBodyMask and AnySettingsHttp.bodyMask
     * and replace match with ellipsis (...).
     * If rules not specified - no replacements performed
     * @param s string to mask
     * @return masked string
     */
    public static String maskBody(String s) {

        AnySettingsHttp settings = AnySettingsHttpExtractor.httpSettings();

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
