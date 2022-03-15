package com.WIZLights;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.inject.Inject;

@RunWith(MockitoJUnitRunner.class)
public class WIZLightsTest extends TestCase
{
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;
    private byte[] bufRecieved = new byte[1000];

    @Inject
    UDP udp;

    @Before
    public void setUp()
    {
        Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
    }

    @Test
    public void WizLightTest() throws IOException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("192.168.2.21");

        String msg = "{\"method\":\"setPilot\",\"env\":\"pro\",\"params\":{\"mac\":\"d8a011312d50\",\"rssi\":-58,\"state\":true,\"dimming\":100,\"speed\":40,\"r\":255,\"g\":50,\"b\":50}}";

        //sendMessage(msg);

        udp.sendMessage(msg,"192.168.2.21", 38899);
        String received = udp.receiveMessage();
        System.out.println(received);
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