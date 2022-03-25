package com.WIZLights.Drops;

import com.WIZLights.WIZLights;
import com.WIZLights.WIZLightsConfig;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.GameObjectSpawned;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class TheatreOfBlood {

    @Inject
    private Client client;

    @Inject
    private WIZLightsConfig config;

    @Inject
    private WIZLights wizLights;

    private static final List<Integer> rewardChestIds = Arrays.asList(33086, 33087, 33088, 33089, 33090);
    private static final Integer yourPurpleChestId = 32993;
    private static final Integer otherPurpleChestId = 32991;

    public void onGameObjectSpawned(GameObjectSpawned event) {
        int objId = event.getGameObject().getId();
        log.debug("obj in tob id: " + objId);
        if (rewardChestIds.contains(objId)) {
            int impostorId = client.getObjectDefinition(objId).getImpostor().getId();
            log.debug("impostorId: " + impostorId);

            if (impostorId == yourPurpleChestId) {
                log.debug("Tob purple in your name");
                if (config.enableRecolorTobYour()) {
                    wizLights.setAllLightsColor(config.yourPurpleColor());
                }

            }
            else if (impostorId == otherPurpleChestId) {
                log.debug("Tob purple in someone else's name");
                if (config.enableRecolorTobOther()) {
                    wizLights.setAllLightsColor(config.otherPurpleColor());
                }
            }
        }
    }

    public boolean isInTob() {
        int tobVar = client.getVar(Varbits.THEATRE_OF_BLOOD);
        return (tobVar == 2 || tobVar == 3);
    }
}
