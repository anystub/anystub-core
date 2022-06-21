package org.anystub;


import java.util.HashSet;
import java.util.Set;

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

        return currentBodyMask.stream()
                .reduce(s, (r, m) -> r.replaceAll(m, "..."));
    }

}
