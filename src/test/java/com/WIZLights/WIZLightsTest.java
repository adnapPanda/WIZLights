package com.WIZLights;

import java.io.IOException;
import java.net.*;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WIZLightsTest extends TestCase
{
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    @Test
    public void WizLightTest() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("192.168.2.21");
        String msg = "{\"method\":\"setPilot\",\"params\":{\"state\":true,\"r\":255,\"g\":0,\"b\":230,\"w\":34}}";
        sendEcho(msg);
    }

    void sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 38899);
        socket.send(packet);
    }
}