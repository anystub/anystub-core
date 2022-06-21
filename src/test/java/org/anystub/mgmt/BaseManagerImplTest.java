package org.anystub.mgmt;

import org.anystub.Base;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseManagerImplTest {

    @Test
    public void testScenario() {

        BaseManagerImpl.instance().getBase("test.yml");


        BaseManagerImpl.instance().getBase("test.yml");
        BaseManagerImpl.instance().getBase("test.yml");
        BaseManagerImpl.instance().getBase("test1.yml");

        new Base( "test2.yml");
        BaseManagerImpl.instance().getBase("test2.yml");

    }

    @Test
    public void testGetNamesTest() {
        assertTrue(BaseManagerImpl.getFilePath("test3.yml").endsWith(File.separator + "test3.yml"));
        assertTrue(BaseManagerImpl.getFilePath("src/test3.yml").endsWith(File.separator + "test3.yml"));
        assertTrue(BaseManagerImpl.getFilePath("./test3.yml").endsWith(File.separator + "test3.yml"));
    }

}