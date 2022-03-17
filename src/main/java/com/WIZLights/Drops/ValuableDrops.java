package com.WIZLights.Drops;

import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.util.Text;

import javax.inject.Inject;

@Slf4j
public class ValuableDrops {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    public void onChatMessage(ChatMessage event) {
        String message = Text.sanitize(Text.removeTags(event.getMessage()));
        if (event.getType() != ChatMessageType.GAMEMESSAGE) return;
        String[] parts = message.split("(?<=:)");
        if (parts[0].equals("Valuable drop:")) {
            String input = parts[1], extracted;
            extracted = input.substring(input.indexOf('('),input.lastIndexOf(')'));
            String GEValue = extracted.replaceAll("\\D+", "");
            log.debug("Drop Value " + GEValue);
            int valueOfDrop = Integer.parseInt(GEValue);
            matchLootValue(valueOfDrop);
        }
    }

    private void matchLootValue(int value) {
        if (config.lowValuePrice() <= value && config.mediumValuePrice() > value) {
            wizLights.setAllLightsColor(config.lowValueColor());
        } else if (config.mediumValuePrice() <= value && config.highValuePrice() > value) {
            wizLights.setAllLightsColor(config.mediumValueColor());
        } else if (config.highValuePrice() <= value && config.insaneValuePrice() > value) {
            wizLights.setAllLightsColor(config.highValueColor());
        } else if (config.insaneValuePrice() <= value) {
            wizLights.setAllLightsColor(config.insaneValueColor());
        }
    }
}
