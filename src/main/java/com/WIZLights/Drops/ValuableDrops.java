package com.WIZLights.Drops;

import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ValuableDrops {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    final Pattern valuableDropPattern = Pattern.compile("\\(([0-9,]*) coins\\)");


    public void onChatMessage(ChatMessage event) {
        String message = Text.sanitize(Text.removeTags(event.getMessage()));
        System.out.println(event.getType());
        if (event.getType() != ChatMessageType.GAMEMESSAGE) return;
        //Valuable Drop Chat message
        if (message.contains("Valuable drop:")) {
            Matcher match = valuableDropPattern.matcher(message);
            if (match.find()) {
                String GEValue = match.group(1).replaceAll(",", "");
                log.debug("Drop Value: " + GEValue);
                int valueOfDrop = Integer.parseInt(GEValue);
                matchLootValue(valueOfDrop);
            }
        //Treasure Trail Value Chat message
        } else if (message.contains("Your treasure is worth around")) {
            String clueValue = message.replaceAll("\\D+", "");
            log.debug("Clue Value: " + clueValue);
            int valueOfClue = Integer.parseInt(clueValue);
            matchLootValue(valueOfClue);
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
