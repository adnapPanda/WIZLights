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
    public void messageBuilderSetPilotTest() {
        Map<String, Integer> params = new HashMap<>();
        params.put("r", 255);
        params.put("g", 0);
        params.put("b", 230);

        String actualMessage = udp.messageBuilder(UDP.Method.SETPILOT, params);
        String expectedMessage = "{\"method\":\"setPilot\",\"params\":{\"r\":255,\"b\":230,\"g\":0}}";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void messageBuilderGetPilotTest() {
        String actualMessage = udp.messageBuilder(UDP.Method.GETPILOT);
        String expectedMessage = "{\"method\":\"getPilot\"}";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void convertGetToSetPilot() {
        String getPilot = "{\"method\":\"getPilot\",\"env\":\"pro\",\"params\":{\"mac\":\"d8a011312d50\",\"rssi\":-58,\"state\":true,\"sceneId\":0,\"r\":255,\"g\":150,\"b\":0,\"c\":0,\"w\":0,\"dimming\":100}}";
        String setPilot = udp.convertGetToSetPilot(getPilot);
        String expectedMessage = "{\"method\":\"setPilot\",\"env\":\"pro\",\"params\":{\"mac\":\"d8a011312d50\",\"rssi\":-58,\"state\":true,\"r\":255,\"g\":150,\"b\":0,\"c\":0,\"w\":0,\"dimming\":100}}";

        assertEquals(expectedMessage, setPilot);
    }
}
