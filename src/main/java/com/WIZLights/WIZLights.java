package com.WIZLights;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class WIZLights {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private UDP udp;

    @Inject
    private ScheduledExecutorService executorService;

    private List<String> previousStates = new ArrayList<>();

    public void setAllLightsColorExecute(Color color) {
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

        executorService.schedule(this::restoreLights, config.duration(), TimeUnit.SECONDS);
    }

    public void setAllLightsColor(Color color) {
        executorService.execute(()->setAllLightsColorExecute(color));
    }

    private void restoreLights() {
        log.debug("Restoring lights");
        List<String> ipAddresses = Text.fromCSV(config.wizLightIPAddresses());

        for (int i = 0; i < ipAddresses.size(); i++) {
            String setMessage = previousStates.get(i);
            udp.sendMessage(setMessage, ipAddresses.get(i), config.wizLightPort());
            String responseMessage = udp.receiveMessage();
            log.debug("Response message: " + responseMessage);
        }

        previousStates.clear();
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
