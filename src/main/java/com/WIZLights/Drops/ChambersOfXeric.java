package com.WIZLights.Drops;

import com.WIZLights.ItemGroup;
import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Varbits;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.awt.Color;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ChambersOfXeric {

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    @Inject
    private Client client;

    private static final Pattern SPECIAL_DROP_MESSAGE = Pattern.compile("(.+) - (.+)");
    private static final Pattern POINTS_MESSAGE = Pattern.compile("Total points: .*, Personal points: ");
    private static final Set<String> uniques = ImmutableSet.of("Dexterous prayer scroll", "Arcane prayer scroll", "Twisted buckler",
            "Dragon hunter crossbow", "Dinh's bulwark", "Ancestral hat", "Ancestral robe top", "Ancestral robe bottom",
            "Dragon claws", "Elder maul", "Kodai insignia", "Twisted bow");

    private boolean uniqueReceived = false;

    //TODO dust and kit need to be based on chat message
    //TODO look at how to do disco lights with dust/kit
    public void onChatMessage(ChatMessage event) {
        if (client.getLocalPlayer() == null || client.getLocalPlayer().getName() == null)
            return;

        if (event.getType() == ChatMessageType.FRIENDSCHATNOTIFICATION)
        {
            String message = Text.removeTags(event.getMessage());

            if (message.contains("your raid is complete!"))
            {
                return;
            }

            Matcher matcher;
            matcher = SPECIAL_DROP_MESSAGE.matcher(message);

            if (matcher.find())
            {
                uniqueReceived = true;

                final String dropReceiver = Text.sanitize(matcher.group(1)).trim();
                final String dropName = matcher.group(2).trim();

                if (uniques.contains(dropName))
                {
                    if (dropReceiver.equals(Text.sanitize(client.getLocalPlayer().getName())))
                    {
                        Color color = getUniqueGroupColor(dropName);
                        if (color != null) {
                            wizLights.setAllLightsColor(color);
                        }
                    }
                }
            }

            matcher = POINTS_MESSAGE.matcher(message);
            if (matcher.find()) {
                if (uniqueReceived) {
                    uniqueReceived = false;
                }
                else {
                    if (config.enableCoxStandardLoot()) {
                        wizLights.setAllLightsColor(config.coxStandardLootColor());
                    }
                }
            }
        }
    }

    private Color getUniqueGroupColor(String uniqueName)
    {
        switch (uniqueName.toLowerCase().trim())
        {
            case "twisted bow":
                return getGroupColor(config.groupTwistedBow());
            case "kodai insignia":
                return getGroupColor(config.groupKodai());
            case "elder maul":
                return getGroupColor(config.groupElderMaul());
            case "dragon claws":
                return getGroupColor(config.groupClaws());
            case "ancestral hat":
                return getGroupColor(config.groupAncestralHat());
            case "ancestral robe top":
                return getGroupColor(config.groupAncestralTop());
            case "ancestral robe bottom":
                return getGroupColor(config.groupAncestralBottom());
            case "dinh's bulwark":
                return getGroupColor(config.groupDinhs());
            case "dragon hunter crossbow":
                return getGroupColor(config.groupDHCB());
            case "twisted buckler":
                return getGroupColor(config.groupBuckler());
            case "arcane prayer scroll":
                return getGroupColor(config.groupArcane());
            case "dexterous prayer scroll":
                return getGroupColor(config.groupDex());
            default:
                log.info("Unique received did not match a known item from CoX: {}", uniqueName);
                return null;
        }
    }

    private Color getGroupColor(ItemGroup group)
    {
        switch (group)
        {
            case ONE:
                return (config.enableGroupOne() ? config.groupOneColor() : null);
            case TWO:
                return (config.enableGroupTwo() ? config.groupTwoColor() : null);
            case THREE:
                return (config.enableGroupThree() ? config.groupThreeColor() : null);
            case FOUR:
                return (config.enableGroupFour() ? config.groupFourColor() : null);
            default:
                return null;
        }
    }

    public boolean isInCox() {
        return (client.getGameState() == GameState.LOGGED_IN && client.getVar(Varbits.IN_RAID) == 1);
    }
}
