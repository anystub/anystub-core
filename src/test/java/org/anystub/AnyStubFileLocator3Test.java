package org.anystub;

import org.anystub.mgmt.BaseManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AnyStubId(filename = "AnyStubFileLocatorTest3-x")
class AnyStubFileLocator3Test {

    {
        Base stub = BaseManagerFactory.getBaseManager().getStub();
        stub.put("aaa", "bbb");
    }

    static {
        Base stub = BaseManagerFactory.getBaseManager().getStub();
        stub.put("111", "222");
    }

    @Test
    void testDiscoverFile() {
        String aaa = BaseManagerFactory.getBaseManager().getBase("AnyStubFileLocatorTest3-x.yml")
                .getVals("aaa")
                .iterator()
                .next();
        assertEquals("bbb", aaa);

        String ones = BaseManagerFactory.getBaseManager().getBase("AnyStubFileLocatorTest3-x.yml")
                .getVals("111")
                .iterator()
                .next();
        assertEquals("222", ones);


    }
}