package org.anystub.mgmt;

import org.anystub.AnyStubFileLocator;
import org.anystub.AnyStubId;
import org.anystub.Base;

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



    public static Base locate() {
        return BaseManagerFactory.locate(null);
    }

    public static Base locate(String fallback) {
        AnyStubId s = AnyStubFileLocator.discoverFile();
        if (s != null) {
            return BaseManagerFactory
                    .getBaseManager()
                    .getBase(s.filename())
                    .constrain(s.requestMode());
        }

        return BaseManagerFactory
                .getBaseManager()
                .getBase(fallback);
    }
}
