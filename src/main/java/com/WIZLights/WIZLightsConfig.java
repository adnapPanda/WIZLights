package com.WIZLights;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("WIZLights")
public interface WIZLightsConfig extends Config
{
	@ConfigSection(
			name = "Wiz Lights",
			description = "IP address and port number of the lights",
			position = 0,
			closedByDefault = false
	)
	String wizLightSection = "wizLights";

	@ConfigItem(
			keyName = "wizLightIPAddresses",
			name = "IP Address",
			description = "IP address of the wiz lights, comma separated",
			section = wizLightSection
	)
	default String wizLightIPAddresses()
	{
		return "";
	}

	@ConfigItem(
			keyName = "wizLightPort",
			name = "Port",
			description = "Port of the wiz lights",
			section = wizLightSection
	)
	default int wizLightPort()
	{
		return 38899;
	}
}
