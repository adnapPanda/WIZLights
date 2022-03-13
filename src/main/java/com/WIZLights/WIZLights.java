package com.WIZLights;

import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WIZLights {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private UDP udp;

    public void setAllLightsColor(Color color) {
        List<String> ipAddresses = Text.fromCSV(config.wizLightIPAddresses());

        String message = udp.messageBuilder(UDP.Method.SETPILOT, getParamsFromColor(color));
        for (String ip : ipAddresses) {
            udp.sendMessage(message, ip, config.wizLightPort());
        }
    }

    private Map<String, Integer> getParamsFromColor(Color color) {
        Map<String, Integer> params = new HashMap<>();
        params.put("r", color.getRed());
        params.put("g", color.getGreen());
        params.put("b", color.getBlue());

        //TODO 100% brightness by default
        params.put("dimming", 100);

        return params;
    }
}
