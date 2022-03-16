package com.WIZLights;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

@Slf4j
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

    public String messageBuilder(Method method) {
        return "{\"method\":\"" + method.getMethod() + "\"}";
    }

    public String messageBuilder(Method method, Map<String, Integer> params) {
        StringBuilder messageString = new StringBuilder("{");
        messageString.append("\"method\":\"").append(method.getMethod()).append("\",");

        messageString.append("\"params\":{");
        for (String key : params.keySet()) {
            messageString.append("\"").append(key).append("\":").append(params.get(key).toString()).append(",");
        }
        messageString.delete(messageString.length()-1, messageString.length()).append("}}");

        return messageString.toString();
    }

    //TODO figure out the exceptions
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

    public String receiveMessage() {
        byte[] buffer = new byte[1000];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try {
            socket.setSoTimeout(2000);
            socket.receive(packet);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return "";
        }
        return new String(packet.getData(), 0, packet.getLength());
    }

    public String convertGetToSetPilot(String message) {
        return message.replace("result", "params")
                .replace(Method.GETPILOT.getMethod(), Method.SETPILOT.getMethod())
                .replace("\"sceneId\":0,", "");
    }

    public void closeSocket() {
        socket.close();
    }
}
