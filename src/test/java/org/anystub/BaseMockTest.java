package org.anystub;

import org.anystub.mgmt.BaseManagerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BaseMockTest {

    public interface MockSupplier {
        String get() throws Exception;
    }

    @Test
    public void testPassThroughTest() throws Exception {
        MockSupplier upstream = mock(MockSupplier.class);


        Base base = BaseManagerFactory.getBaseManager()
                .getBase("passthrough.yml")
                .constrain(RequestMode.rmPassThrough);

        for (int i = 0; i < 4; i++) {

            base.request(new Supplier<String, Exception>() {
                @Override
                public String get() throws Exception {
                    return upstream.get();
                }
            }, "test");
        }

        verify(upstream, times(4)).get();
        long count = base.history().count();
        assertEquals(0, count);
    }
}