package com.WIZLights.Drops;

import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class TheatreOfBlood {

    @Inject
    private Client client;

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    private static final List<Integer> rewardChestIds = Arrays.asList(33086, 33087, 33088, 33089, 33090);
    private static final int yourPurpleChestId = 32993;
    private static final int otherPurpleChestId = 32991;

    public static final int TOB_RAIDERS_VARP = 330;
    public static final int TOB_MAX_SIZE = 5;

    private int teamSize = 0;
    private int chestCount = 0;
    private boolean uniqueReceived = false;

    public void onGameObjectSpawned(GameObjectSpawned event) {
        int objId = event.getGameObject().getId();
        if (rewardChestIds.contains(objId)) {
            chestCount++;
            int impostorId = client.getObjectDefinition(objId).getImpostor().getId();

            if (impostorId == yourPurpleChestId) {
                uniqueReceived = true;
                if (config.enableRecolorTobYour()) {
                    wizLights.setAllLightsColor(config.yourPurpleColor());
                }

            }
            else if (impostorId == otherPurpleChestId) {
                uniqueReceived = true;
                if (config.enableRecolorTobOther()) {
                    wizLights.setAllLightsColor(config.otherPurpleColor());
                }
            }

            if (chestCount == getTobTeamSize()) {
                if (!uniqueReceived) {
                    if (config.enableTobStandardLoot()) {
                        wizLights.setAllLightsColor(config.tobStandardLootColor());
                    }
                }
                reset();
            }
        }
    }

    public boolean isInTob() {
        int tobVar = client.getVarbitValue(Varbits.THEATRE_OF_BLOOD);
        return (tobVar == 2 || tobVar == 3);
    }

    public void reset() {
        teamSize = 0;
        chestCount = 0;
        uniqueReceived = false;
    }

    private int getTobTeamSize()
    {
        if (teamSize == 0) {
            Map<Integer, Object> varcmap = client.getVarcMap();
            for (int i = 0; i < TOB_MAX_SIZE; i++) {
                Integer playervarp = TOB_RAIDERS_VARP + i;
                if (varcmap.containsKey(playervarp)) {
                    String playerName = Text.sanitize(varcmap.get(playervarp).toString());
                    if (playerName != null && !playerName.equals("")) {
                        teamSize++;
                    }
                }
            }
        }

        return teamSize;
    }
}
