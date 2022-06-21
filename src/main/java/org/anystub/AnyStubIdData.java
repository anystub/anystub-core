package org.anystub;

import java.lang.annotation.Annotation;

public class AnyStubIdData implements AnyStubId {

    private final String filename;
    private final RequestMode requestMode;
    private final String[] paramMasks;

    public AnyStubIdData(String filename, RequestMode requestMode, String[] paramMasks) {
        this.filename = filename;
        this.requestMode = requestMode;
        this.paramMasks = paramMasks;
    }

    @Override
    public String filename() {
        return filename;
    }

    @Override
    public RequestMode requestMode() {
        return requestMode;
    }

    @Override
    public String[] requestMasks() {
        return paramMasks;
    }

    public Class<? extends Annotation> annotationType() {
        return AnyStubIdData.class;
    }
}
