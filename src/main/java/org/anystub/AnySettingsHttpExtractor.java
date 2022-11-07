package org.anystub;

import org.anystub.mgmt.MTCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

/**
 * extracts http settings from @AnySettingsHttp
 */
public class AnySettingsHttpExtractor {

    private AnySettingsHttpExtractor() {

    }

    /**
     * fetches settings from annotations in a test
     * @return settings based on annotations
     */
    public static AnySettingsHttp discoverSettings() {
        AnySettingsHttp id = null;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            if (s.getMethodName().startsWith("lambda$")) {
                continue;
            }
            Class<?> aClass;
            try {
                aClass = Class.forName(s.getClassName());
            } catch (ClassNotFoundException ignored) {
                aClass = null;
            }
            if (aClass == null) {
                continue;
            }
            Method methodStream = stream(aClass.getDeclaredMethods())
                    .filter(method -> method.getName().equals(s.getMethodName()))
                    .filter(method -> method.getAnnotation(AnySettingsHttp.class) != null)
                    .findAny().orElse(null);
            if (methodStream != null) {
                id = methodStream.getAnnotation(AnySettingsHttp.class);
            }
            if (id != null) {
                break;
            }
            id = aClass.getDeclaredAnnotation(AnySettingsHttp.class);
            if (id != null) {
                break;
            }
        }
        return id;
    }


    /**
     * fetches settings from annotations in a test
     * (*tries MtCache if not found)
     * enrich with setting file if allowed
     * @return settings based on annotations
     */
    public static AnySettingsHttp httpSettings() {
        boolean overrideGlobal = false;
        boolean allHeaders = false;
        final List<String> headers = new ArrayList<>();
        final List<String> bodyTrigger = new ArrayList<>();
        final List<String> bodyMethods = new ArrayList<>();


        AnySettingsHttp settings = AnySettingsHttpExtractor.discoverSettings();
        if(settings == null) {
            settings = MTCache.getFallbackHttpSettings();
        }
        if (settings != null) {
            overrideGlobal = settings.overrideGlobal();
            allHeaders = settings.allHeaders();
            headers.addAll(asList(settings.headers()));
            bodyTrigger.addAll(asList(settings.bodyTrigger()));
            bodyMethods.addAll(asList(settings.bodyMethods()));
        } else {
            bodyMethods.addAll(asList("POST", "PUT", "DELETE"));
        }

        if (settings==null || !settings.overrideGlobal()) {
            headers.addAll(asList(GlobalSettings.globalHeaders));
            bodyTrigger.addAll(asList(GlobalSettings.globalBodyTrigger));
            bodyMethods.addAll(asList(GlobalSettings.globalBodyMethods));
        }

        boolean overrideGlobalF = overrideGlobal;
        boolean allHeadersF = allHeaders;


        return new AnySettingsHttp(){

            @Override
            public Class<? extends Annotation> annotationType() {
                return AnySettingsHttp.class;
            }

            @Override
            public boolean overrideGlobal() {
                return overrideGlobalF;
            }

            @Override
            public boolean allHeaders() {
                return allHeadersF;
            }

            @Override
            public String[] headers() {
                return headers.stream().distinct().toArray(String[]::new);
            }

            @Override
            public String[] bodyTrigger() {
                return bodyTrigger.stream().distinct().toArray(String[]::new);
            }

            @Override
            public String[] bodyMethods() {
                return bodyMethods.stream().distinct().toArray(String[]::new);
            }
        };
    }
}
