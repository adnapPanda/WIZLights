package com.WIZLights;

import com.WIZLights.Drops.ChambersOfXeric;
import com.WIZLights.Drops.ValuableDrops;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import java.awt.Color;

@Slf4j
@PluginDescriptor(
	name = "WIZ Smart Lights"
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

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		valuableDrops.onChatMessage(event);
		cox.onChatMessage(event);

		String message = Text.sanitize(Text.removeTags(event.getMessage()));
		if (event.getName().equals(client.getLocalPlayer().getName()) && message.contains("Wiz")) {
			String[] msg = message.split(" ",4);
			Color color = new Color(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]));
			wizLights.setAllLightsColor(color);
		}
	}

	@Provides
	WIZLightsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WIZLightsConfig.class);
	}
}
