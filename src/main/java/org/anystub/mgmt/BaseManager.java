package org.anystub.mgmt;

import org.anystub.Base;

public interface BaseManager {

    /**
     * returns stub by given file
     *
     * @param filename stun file
     * @return
     */
    Base getBase(String filename);

    /**
     * returns default stub
     *
     * @return
     */
    Base getBase();

    /**
     * returns stub depends on context
     *
     * @return
     */
    Base getStub();
    Base getStub(String suffix);
}
