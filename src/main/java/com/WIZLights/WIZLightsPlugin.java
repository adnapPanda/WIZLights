package com.WIZLights;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.ColorUtil;
import net.runelite.client.util.Text;

import java.awt.Color;

@Slf4j
@PluginDescriptor(
	name = "WIZLights"
)
public class WIZLightsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private WIZLightsConfig config;

	@Inject
	private WIZLights wizLights;

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
		String message = Text.sanitize(Text.removeTags(event.getMessage()));
		log.info(message);
		if (message.contains("Wiz")) {
			String[] msg = message.split(" ",4);
			Color color = new Color(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]));
			log.info("Setting lights to " + color.toString());
			wizLights.setAllLightsColor(color);
		}
		String[] parts = message.split("(?<=:)");
		if (parts[0].equals("Valuable drop:")) {
			String GEValue = parts[1].replaceAll("\\D+","");
			log.info("Drop Value " + GEValue);
			int valueOfDrop = Integer.parseInt(GEValue);
			matchLootValue(valueOfDrop);
		}
	}

	void matchLootValue(int value) {
		if (config.lowValuePrice() <= value && config.mediumValuePrice() > value) {
			log.info("Setting lights to " + config.lowValueColor().toString());
			wizLights.setAllLightsColor(config.lowValueColor());
		} else if (config.mediumValuePrice() <= value && config.highValuePrice() > value) {
			log.info("Setting lights to " + config.lowValueColor().toString());
			wizLights.setAllLightsColor(config.mediumValueColor());
		} else if (config.highValuePrice() <= value && config.insaneValuePrice() > value) {
			log.info("Setting lights to " + config.lowValueColor().toString());
			wizLights.setAllLightsColor(config.highValueColor());
		} else if (config.insaneValuePrice() <= value) {
			log.info("Setting lights to " + config.lowValueColor().toString());
			wizLights.setAllLightsColor(config.insaneValueColor());
		}
	}

	@Provides
	WIZLightsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WIZLightsConfig.class);
	}
}
