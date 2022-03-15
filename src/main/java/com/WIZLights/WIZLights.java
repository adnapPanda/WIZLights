package com.WIZLights;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class WIZLights {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private UDP udp;

    private List<String> previousStates = new ArrayList<>();

    //TODO look at saving the light state and restoring color after leaving raid
    public void setAllLightsColor(Color color) {
        log.debug("Setting lights to " + color.toString());
        List<String> ipAddresses = Text.fromCSV(config.wizLightIPAddresses());

        String getMessage = udp.messageBuilder(UDP.Method.GETPILOT);
        for (String ip : ipAddresses) {
            udp.sendMessage(getMessage, ip, config.wizLightPort());
            String responseMessage = udp.receiveMessage();
            previousStates.add(udp.convertGetToSetPilot(responseMessage));
        }

        String setMessage = udp.messageBuilder(UDP.Method.SETPILOT, getParamsFromColor(color));
        for (String ip : ipAddresses) {
            udp.sendMessage(setMessage, ip, config.wizLightPort());
            String responseMessage = udp.receiveMessage();
            log.debug("Response message: " + responseMessage);
        }
    }

    private Map<String, Integer> getParamsFromColor(Color color) {
        Map<String, Integer> params = new HashMap<>();
        params.put("r", color.getRed());
        params.put("g", color.getGreen());
        params.put("b", color.getBlue());

        params.put("dimming", config.brightness());

        return params;
    }
}
