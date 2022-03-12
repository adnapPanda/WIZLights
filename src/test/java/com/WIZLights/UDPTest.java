package com.WIZLights;

import com.google.inject.Guice;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.google.inject.testing.fieldbinder.BoundFieldModule;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class UDPTest extends TestCase {

    @Inject
    private UDP udp;

    @Before
    public void setUp()
    {
        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
    }

    @Test
    public void messageBuilderTest() throws IOException {
        Map<String, Integer> params = new HashMap<>();
        params.put("r", 255);
        params.put("g", 0);
        params.put("b", 230);

        String actualMessage = udp.messageBuilder(UDP.Method.SETPILOT, params);
        String expectedMessage = "{\"method\":\"setPilot\",\"params\":{\"r\":255,\"b\":230,\"g\":0}}";

        assertEquals(expectedMessage, actualMessage);
    }
}
