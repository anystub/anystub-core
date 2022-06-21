package org.anystub;

import org.anystub.mgmt.BaseManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AnyStubId(filename = "AnyStubFileLocatorTest3-x")
public class AnyStubFileLocator3Test {

    {
        Base stub = BaseManagerFactory.getBaseManager().getStub();
        stub.put("aaa", "bbb");
    }

    static {
        Base stub = BaseManagerFactory.getBaseManager().getStub();
        stub.put("111", "222");
    }

    @Test
    public void testDiscoverFile() {
        String aaa = BaseManagerFactory.getBaseManager().getBase("AnyStubFileLocatorTest3-x.yml").get("aaa");
        assertEquals("bbb", aaa);

        String ones = BaseManagerFactory.getBaseManager().getBase("AnyStubFileLocatorTest3-x.yml").get("111");
        assertEquals("222", ones);


    }
}