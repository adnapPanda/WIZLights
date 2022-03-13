package com.WIZLights;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

public class UDP {
    private DatagramSocket socket;

    @Getter
    @RequiredArgsConstructor
    public enum Method {
        GETPILOT ("getPilot"),
        SETPILOT("setPilot");

        private final String method;
    }

    public UDP() throws SocketException {
        socket = new DatagramSocket();
    }

    //TODO are all params values integers?
    public String messageBuilder(Method method, Map<String, Integer> params) {
        StringBuilder messageString = new StringBuilder("{");
        messageString.append("\"method\":\"").append(method.getMethod()).append("\",");

        messageString.append("\"params\":{");
        for (String key : params.keySet()) {
            messageString.append("\"" + key + "\":" + params.get(key).toString() + ",");
        }
        messageString.delete(messageString.length()-1, messageString.length()).append("}}");

        return messageString.toString();
    }

    //TODO figure out the exceptions, try getting response from the lights?
    public void sendMessage(String msg, String ipAddress, int port) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ipAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
