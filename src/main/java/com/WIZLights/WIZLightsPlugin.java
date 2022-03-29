package com.WIZLights;

import com.WIZLights.Drops.ChambersOfXeric;
import com.WIZLights.Drops.TheatreOfBlood;
import com.WIZLights.Drops.ValuableDrops;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.loottracker.LootReceived;
import net.runelite.client.util.Text;
import net.runelite.client.util.WildcardMatcher;
import net.runelite.http.api.loottracker.LootRecordType;

import java.awt.Color;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@PluginDescriptor(
		name = "WIZ Smart Lights",
		description = "A plugin to control the WIZ Smart Lights"
)
public class WIZLightsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private WIZLightsConfig config;

	@Inject
	private WIZLights wizLights;

	@Inject
	private ValuableDrops valuableDrops;

	@Inject
	private ChambersOfXeric cox;

	@Inject
	private UDP udp;

	@Inject
	private TheatreOfBlood tob;

	private List<String> lootNpcs;

	@Override
	protected void startUp() throws Exception
	{
		tob.reset();
		lootNpcs = Collections.emptyList();
	}

	@Override
	protected void shutDown() throws Exception
	{
		udp.closeSocket();
	}

	@Subscribe
	public void onNpcLootReceived(NpcLootReceived npcLootReceived)
	{
		NPC npc = npcLootReceived.getNpc();
		Collection<ItemStack> items = npcLootReceived.getItems();

		if (!lootNpcs.isEmpty())
		{
			for (String npcName : lootNpcs)
			{
				if (WildcardMatcher.matches(npcName, npc.getName()))
				{
					valuableDrops.processLoot(npc.getName(), items);
					return;
				}
			}
		}
		else
		{
			if (!tob.isInTob() && !cox.isInCox()) valuableDrops.processLoot(npc.getName(), items);
		}
	}

	@Subscribe
	public void onLootReceived(LootReceived lootReceived)
	{
		if (lootReceived.getType() != LootRecordType.EVENT && lootReceived.getType() != LootRecordType.PICKPOCKET)
		{
			return;
		}

		if (!tob.isInTob() && !cox.isInCox()) valuableDrops.processLoot(lootReceived.getName(), lootReceived.getItems());
	}


	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		valuableDrops.onChatMessage(event);
		if (cox.isInCox()) cox.onChatMessage(event);

		String message = Text.sanitize(Text.removeTags(event.getMessage()));
		//To test if your lights work
		if (message.contains("Wiztest") && event.getName().contains(client.getLocalPlayer().getName())) {
			String[] msg = message.split(" ",4);
			Color color = new Color(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]));
			wizLights.setAllLightsColor(color);
		}
	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		if(tob.isInTob()) {
			tob.onGameObjectSpawned(event);
		}
	}

	@Provides
	WIZLightsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WIZLightsConfig.class);
	}
}
