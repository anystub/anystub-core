package org.anystub.mgmt;

import org.anystub.AnyStubFileLocator;
import org.anystub.AnyStubId;
import org.anystub.Base;

import java.util.logging.Logger;

public final class BaseManagerFactory {
    private static BaseManager baseManager = null;

    private BaseManagerFactory() {
    }

    public static BaseManager getBaseManager() {
        if (baseManager == null) {
            baseManager = BaseManagerImpl.instance();
        }
        return baseManager;
    }

    public static Base getStub() {
        return BaseManagerFactory.getBaseManager().getStub();
    }

    public static Base getStub(String suffix) {
        return BaseManagerFactory.getBaseManager().getStub(suffix);
    }


    private static Base fallbackBase = null;

    public static void resetMtFallback() {
        fallbackBase = null;
    }

    /**
     *
     * @return
     */
    public static AutoCloseable setMtFallback() {
        Base discover = discover();
        if (discover == null) {
            Logger log = Logger.getLogger(BaseManagerFactory.class.getName());
            log.warning("Anystub cannot discover a test stub. Default stub will be used in the test.");
            return () -> {
            };
        }
        BaseManagerFactory.fallbackBase = discover;
        return () -> BaseManagerFactory.fallbackBase = null;
    }


    public static Base locate() {
        return BaseManagerFactory.locate(null);
    }

    public static Base locate(String fallback) {
        Base discovered = discover();
        if(discovered != null) {
            return discovered;
        }

        if (fallbackBase != null) {
            return fallbackBase;
        }

        return BaseManagerFactory
                .getBaseManager()
                .getBase(fallback);
    }

    /**
     * analyzes stack-trace to find
     * @return
     */
    private static Base discover() {
        AnyStubId s = AnyStubFileLocator.discoverFile();
        if (s == null) {
            return null;
        }
        return BaseManagerFactory
                .getBaseManager()
                .getBase(s.filename(), base -> base.constrain(s.requestMode()));

    }
}
