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
    private byte[] bufRecieved = new byte[1000];

    @Test
    public void WizLightTest() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("192.168.2.17");
        //String msg = "{\"method\":\"setPilot\",\"params\":{\"state\":true,\"r\":255,\"g\":0,\"b\":230,\"w\":34}}";
        //String msg = "{\"method\":\"getSystemConfig\"}";
        String msg = "{\"method\":\"getPilot\"}";
        sendMessage(msg);
    }

    void sendMessage(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet
                = new DatagramPacket(buf, buf.length, address, 38899);
        socket.send(packet);
        packet = new DatagramPacket(bufRecieved, bufRecieved.length);
        socket.receive(packet);
        String received = new String(
                packet.getData(), 0, packet.getLength());
        System.out.println(received);
    }
}