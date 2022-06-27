package org.anystub;


import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.anystub.HttpGlobalSettings.globalBodyMask;
import static org.anystub.HttpGlobalSettings.globalBodyTrigger;

public class SettingsUtil {

    public static boolean matchBodyRule(String url) {
        Set<String> currentBodyTriggers = new HashSet<>();

        AnySettingsHttp settings = AnySettingsHttpExtractor.discoverSettings();

        if (settings != null) {
            currentBodyTriggers.addAll(asList(settings.bodyTrigger()));
        }

        if ((settings == null || !settings.overrideGlobal()) && globalBodyTrigger != null) {
            currentBodyTriggers.addAll(asList(globalBodyTrigger));
        }


        return currentBodyTriggers.stream()
                .anyMatch(url::contains);
    }

    /**
     * combines all available rules from HttpGlobalSettings.globalBodyMask and AnySettingsHttp.bodyMask
     * and replace match with ellipsis (...). if not rules specified no replacements performed
     * @param s string to mask
     * @return masked string
     */
    public static String maskBody(String s) {
        Set<String> currentBodyMask = new HashSet<>();

        AnySettingsHttp settings = AnySettingsHttpExtractor.discoverSettings();
        // add bodyMask from settings
        if (settings != null) {
            currentBodyMask.addAll(asList(settings.bodyMask()));
        }

        // add global-bodyMasks if test-settings does not override global settings
        if ((settings == null || !settings.overrideGlobal()) && globalBodyMask != null) {
            currentBodyMask.addAll(asList(globalBodyMask));
        }
        if (currentBodyMask.isEmpty()) {
            return s;
        }

        String combinedRule;
        if (currentBodyMask.size()>1) {
            combinedRule = currentBodyMask.stream().
                    map(r->String.format("(%s)", r))
                    .collect(Collectors.joining("|"));
        } else {
            combinedRule = String.join("", currentBodyMask);
        }

        return s.replaceAll(combinedRule, "...");
    }

}
