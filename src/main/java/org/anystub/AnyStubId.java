package org.anystub;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies new stub
 * It could appear on test-class level or on a test method.
 * Method-annotation has priority over class annotation.
 * Method annotations do not inherit settings from class level.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface AnyStubId {
    /**
     * Specifies a file name for current stub.
     * If not specified - name of testMethod/testClass is used.
     * Automatically adds extension - .yml if missing
     * example:
     *
     * `@Test`
     * `@AnyStubId(filename = "testCaseFile")`
     * `public void testCaseFileTest()`
     * `     ...`
     *
     * It will create a file in: test/resources/anystub/testCaseFile.yml
     */
    String filename() default "";

    /**
     * specifies behaviour of the stub
     *
     * @return RequestMode
     */
    RequestMode requestMode() default RequestMode.rmNew;

    /**
     * to define regex expressions which will replace text in the keys with elapses "..."
     * example:
     *
     * ```@Test```
     * ```@AnyStubId(paramMasks = "password:.*,")```
     * ```void keyMask()```
     * ```   ...```
     *
     * this will cut out the word 'password' with the password from key
     *
     * @return String[]
     */
    String[] requestMasks() default {};
}
