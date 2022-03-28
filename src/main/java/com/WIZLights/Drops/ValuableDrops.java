package com.WIZLights.Drops;

import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ValuableDrops {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    @Inject
    private ItemManager itemManager;

    public void onChatMessage(ChatMessage event) {
        String message = Text.sanitize(Text.removeTags(event.getMessage()));
        if (event.getType() != ChatMessageType.GAMEMESSAGE) return;
        //Valuable Drop Chat message
        if (message.contains("Your treasure is worth around")) {
            String clueValue = message.replaceAll("\\D+", "");
            log.debug("Clue Value: " + clueValue);
            long valueOfClue = Integer.parseInt(clueValue);
            matchLootValue(valueOfClue);
        }
    }

    public void processLoot(String name, Collection<ItemStack> items) {
        long totalValue = 0;
        for (ItemStack item : stack(items)) {
            int itemId = item.getId();
            int qty = item.getQuantity();

            int price = itemManager.getItemPrice(itemId);
            long total = (long) price * qty;

            totalValue += total;
        }
        log.debug("Loot Received from " + name + ": " + totalValue);
        matchLootValue(totalValue);
    }

    private void matchLootValue(long value) {
        if (config.lowValuePrice() <= value && config.mediumValuePrice() > value && config.enableRecolorLowValue()) {
            wizLights.setAllLightsColor(config.lowValueColor());
        } else if (config.mediumValuePrice() <= value && config.highValuePrice() > value && config.enableRecolorMediumValue()) {
            wizLights.setAllLightsColor(config.mediumValueColor());
        } else if (config.highValuePrice() <= value && config.insaneValuePrice() > value && config.enableRecolorHighValue()) {
            wizLights.setAllLightsColor(config.highValueColor());
        } else if (config.insaneValuePrice() <= value && config.enableRecolorInsaneValue()) {
            wizLights.setAllLightsColor(config.insaneValueColor());
        }
    }

    private static Collection<ItemStack> stack(Collection<ItemStack> items)
    {
        final List<ItemStack> list = new ArrayList<>();

        for (final ItemStack item : items)
        {
            int quantity = 0;
            for (final ItemStack i : list)
            {
                if (i.getId() == item.getId())
                {
                    quantity = i.getQuantity();
                    list.remove(i);
                    break;
                }
            }
            if (quantity > 0)
            {
                list.add(new ItemStack(item.getId(), item.getQuantity() + quantity, item.getLocation()));
            }
            else
            {
                list.add(item);
            }
        }

        return list;
    }
}
