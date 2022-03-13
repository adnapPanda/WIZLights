package com.WIZLights;

import java.awt.Color;
import net.runelite.client.config.Alpha;
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
			description = "IP addresses of the wiz lights, comma separated. Example:192.1.1.1, 192.1.1.2",
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

	@ConfigSection(
			name = "Lights Color Picker",
			description = "The color of the lights when receiving a drop",
			position = 1,
			closedByDefault = false
	)
	String valuableDropsColorSection = "dropsColor";

	@Alpha
	@ConfigItem(
			keyName = "lowValueColor",
			name = "Low value items",
			description = "Configures the color for low value items",
			section = valuableDropsColorSection,
			position = 0
	)
	default Color lowValueColor()
	{
		return Color.decode("#66B2FF");
	}

	@ConfigItem(
			keyName = "lowValuePrice",
			name = "Low value price",
			description = "Configures the start price for low value items",
			section = valuableDropsColorSection,
			position = 1
	)
	default int lowValuePrice()
	{
		return 20000;
	}

	@Alpha
	@ConfigItem(
			keyName = "mediumValueColor",
			name = "Medium value items",
			description = "Configures the color for medium value items",
			section = valuableDropsColorSection,
			position = 2
	)
	default Color mediumValueColor()
	{
		return Color.decode("#99FF99");
	}

	@ConfigItem(
			keyName = "mediumValuePrice",
			name = "Medium value price",
			description = "Configures the start price for medium value items",
			section = valuableDropsColorSection,
			position = 3
	)
	default int mediumValuePrice()
	{
		return 100000;
	}

	@Alpha
	@ConfigItem(
			keyName = "highValueColor",
			name = "High value items",
			description = "Configures the color for high value items",
			section = valuableDropsColorSection,
			position = 4
	)
	default Color highValueColor()
	{
		return Color.decode("#FF9600");
	}

	@ConfigItem(
			keyName = "highValuePrice",
			name = "High value price",
			description = "Configures the start price for high value items",
			section = valuableDropsColorSection,
			position = 5
	)
	default int highValuePrice()
	{
		return 1000000;
	}

	@Alpha
	@ConfigItem(
			keyName = "insaneValueColor",
			name = "Insane value items",
			description = "Configures the color for insane value items",
			section = valuableDropsColorSection,
			position = 6
	)
	default Color insaneValueColor()
	{
		return Color.decode("#FF66B2");
	}

	@ConfigItem(
			keyName = "insaneValuePrice",
			name = "Insane value price",
			description = "Configures the start price for insane value items",
			section = valuableDropsColorSection,
			position = 7
	)
	default int insaneValuePrice()
	{
		return 10000000;
	}
}
