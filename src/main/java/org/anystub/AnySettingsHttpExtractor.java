package org.anystub;

import org.anystub.mgmt.MTCache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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
            try {
                Method method;
                method = aClass.getDeclaredMethod(s.getMethodName());
                id = method.getAnnotation(AnySettingsHttp.class);
            } catch (NoSuchMethodException ignored) {
            }
            if (id == null) {
                Method methodStream = stream(aClass.getDeclaredMethods())
                        .filter(method -> method.getName().equals(s.getMethodName()))
                        .filter(method -> method.getAnnotation(AnySettingsHttp.class) != null)
                        .findAny().orElse(null);
                if (methodStream != null) {
                    id = methodStream.getAnnotation(AnySettingsHttp.class);
                }
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
        Set<String> headers = new HashSet<>();
        Set<String> bodyTrigger = new HashSet<>();
        Set<String> bodyMask = new HashSet<>();


        AnySettingsHttp settings = AnySettingsHttpExtractor.discoverSettings();
        if(settings == null) {
            settings = MTCache.getFallbackHttpSettings();
        }
        if (settings != null) {
            overrideGlobal = settings.overrideGlobal();
            allHeaders = settings.allHeaders();
            headers.addAll(asList(settings.headers()));
            bodyTrigger.addAll(asList(settings.bodyTrigger()));
            bodyMask.addAll(asList(settings.bodyMask()));
        }

        if (settings==null || !settings.overrideGlobal()) {
            headers.addAll(asList(HttpGlobalSettings.globalHeaders));
            bodyTrigger.addAll(asList(HttpGlobalSettings.globalBodyTrigger));
            bodyMask.addAll(asList(HttpGlobalSettings.globalBodyMask));
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
                return headers.toArray(new String[0]);
            }

            @Override
            public String[] bodyTrigger() {
                return bodyTrigger.toArray(new String[0]);
            }

            @Override
            public String[] bodyMask() {
                return bodyMask.toArray(new String[0]);
            }
        };
    }
}
