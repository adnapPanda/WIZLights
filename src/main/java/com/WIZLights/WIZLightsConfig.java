package com.WIZLights;

import java.awt.Color;

import net.runelite.client.config.*;

import static net.runelite.client.config.Units.PERCENT;
import static net.runelite.client.config.Units.SECONDS;

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
			name = "Lights Options",
			description = "Several options for lights",
			position = 1,
			closedByDefault = false
	)
	String lightsOptionsSection = "lightsOptionsSection";

	@Units(SECONDS)
	@Range()
	@ConfigItem(
			keyName = "duration",
			name = "Duration",
			description = "Duration the lights stay recolored",
			position = 0,
			section = lightsOptionsSection
	)
	default int duration()
	{
		return 30;
	}

	@Units(PERCENT)
	@Range(max=100)
	@ConfigItem(
			keyName = "brightness",
			name = "Brightness",
			description = "The brightness of the lights in percentage",
			position = 1,
			section = lightsOptionsSection
	)
	default int brightness()
	{
		return 100;
	}

	@ConfigSection(
			name = "Valuable drops",
			description = "The color of the lights when receiving a valuable drop",
			position = 2,
			closedByDefault = false
	)
	String valuableDropsColorSection = "dropsColor";

	@ConfigItem(
			keyName = "enableRecolorLowValue",
			name = "Recolor low value loot",
			description = "Enable recoloring the lights when Insane Value loot is received",
			section = valuableDropsColorSection,
			position = 0
	)
	default boolean enableRecolorLowValue()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
			keyName = "lowValueColor",
			name = "Low value items",
			description = "Configures the color for low value items",
			section = valuableDropsColorSection,
			position = 1
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
			position = 2
	)
	default int lowValuePrice()
	{
		return 20000;
	}

	@ConfigItem(
			keyName = "enableRecolorMediumValue",
			name = "Recolor medium value loot",
			description = "Enable recoloring the lights when Insane Value loot is received",
			section = valuableDropsColorSection,
			position = 3
	)
	default boolean enableRecolorMediumValue()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
			keyName = "mediumValueColor",
			name = "Medium value items",
			description = "Configures the color for medium value items",
			section = valuableDropsColorSection,
			position = 4
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
			position = 5
	)
	default int mediumValuePrice()
	{
		return 100000;
	}

	@ConfigItem(
			keyName = "enableRecolorHighValue",
			name = "Recolor high value loot",
			description = "Enable recoloring the lights when Insane Value loot is received",
			section = valuableDropsColorSection,
			position = 6
	)
	default boolean enableRecolorHighValue()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
			keyName = "highValueColor",
			name = "High value items",
			description = "Configures the color for high value items",
			section = valuableDropsColorSection,
			position = 7
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
			position = 8
	)
	default int highValuePrice()
	{
		return 1000000;
	}

	@ConfigItem(
			keyName = "enableRecolorInsaneValue",
			name = "Recolor insane value loot",
			description = "Enable recoloring the lights when Insane Value loot is received",
			section = valuableDropsColorSection,
			position = 9
	)
	default boolean enableRecolorInsaneValue()
	{
		return true;
	}

	@Alpha
	@ConfigItem(
			keyName = "insaneValueColor",
			name = "Insane value items",
			description = "Configures the color for insane value items",
			section = valuableDropsColorSection,
			position = 10
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
			position = 11
	)
	default int insaneValuePrice()
	{
		return 10000000;
	}

	@ConfigSection(
			name = "Chamber of Xeric",
			description = "The color of the lights when receiving a drop at CoX",
			position = 3,
			closedByDefault = false
	)
	String coxDropsColorSection = "coxDropsColor";

	@ConfigItem(
			keyName = "enableStandardLoot",
			name = "Recolor standard loot",
			description = "Enable recoloring the light of the chest when no unique is obtained",
			position = 0,
			section = coxDropsColorSection
	)
	default boolean enableStandardLoot()
	{
		return true;
	}

	@ConfigItem(
			keyName = "standardLoot",
			name = "Standard loot",
			description = "Color of light when no unique item is obtained",
			position = 1,
			section = coxDropsColorSection
	)
	default Color standardLoot()
	{
		return Color.WHITE;
	}

	@ConfigItem(
			keyName = "enableDust",
			name = "Recolor dust",
			description = "Enable recoloring the light of the chest when metamorphic dust is obtained",
			position = 2,
			section = coxDropsColorSection
	)
	default boolean enableDust()
	{
		return true;
	}

	@ConfigItem(
			keyName = "dust",
			name = "Metamorphic Dust",
			description = "Color of light when metamorphic dust is obtained",
			position = 3,
			section = coxDropsColorSection
	)
	default Color dust()
	{
		return Color.CYAN;
	}

	@ConfigItem(
			keyName = "enableKit",
			name = "Recolor Twisted kit",
			description = "Enable recoloring the light of the chest when a twisted kit is obtained",
			position = 4,
			section = coxDropsColorSection
	)
	default boolean enableKit()
	{
		return true;
	}

	@ConfigItem(
			keyName = "twistedKit",
			name = "Twisted Kit",
			description = "Color of light when a twisted kit is obtained",
			position = 5,
			section = coxDropsColorSection
	)
	default Color twistedKit()
	{
		return Color.GREEN;
	}

	@ConfigItem(
			keyName = "enableGroupOne",
			name = "Recolor group 1",
			description = "Enable recoloring the light of the chest when a unique from group 1 is obtained",
			position = 6,
			section = coxDropsColorSection
	)
	default boolean enableGroupOne()
	{
		return true;
	}

	@ConfigItem(
			keyName = "groupOneColor",
			name = "Group 1",
			description = "Color of the light when an item from group 1 is obtained",
			position = 7,
			section = coxDropsColorSection
	)
	default Color groupOneColor()
	{
		return Color.RED;
	}

	@ConfigItem(
			keyName = "enableGroupTwo",
			name = "Recolor group 2",
			description = "Enable recoloring the light of the chest when a unique from group 2 is obtained",
			position = 8,
			section = coxDropsColorSection
	)
	default boolean enableGroupTwo()
	{
		return true;
	}

	@ConfigItem(
			keyName = "groupTwoColor",
			name = "Group 2",
			description = "Color of the light when an item from group 2 is obtained",
			position = 9,
			section = coxDropsColorSection
	)
	default Color groupTwoColor()
	{
		return Color.BLUE;
	}

	@ConfigItem(
			keyName = "enableGroupThree",
			name = "Recolor group 3",
			description = "Enable recoloring the light of the chest when a unique from group 3 is obtained",
			position = 10,
			section = coxDropsColorSection
	)
	default boolean enableGroupThree()
	{
		return true;
	}

	@ConfigItem(
			keyName = "groupThreeColor",
			name = "Group 3",
			description = "Color of the light when an item from group 3 is obtained",
			position = 11,
			section = coxDropsColorSection
	)
	default Color groupThreeColor()
	{
		return Color.YELLOW;
	}

	@ConfigItem(
			keyName = "enableGroupFour",
			name = "Recolor group 4",
			description = "Enable recoloring the light of the chest when a unique from group 4 is obtained",
			position = 12,
			section = coxDropsColorSection
	)
	default boolean enableGroupFour()
	{
		return true;
	}

	@ConfigItem(
			keyName = "groupFourColor",
			name = "Group 4",
			description = "Color of the light when an item from group 4 is obtained",
			position = 13,
			section = coxDropsColorSection
	)
	default Color groupFourColor()
	{
		return new Color(120, 86, 46);
	}

	@ConfigItem(
			keyName = "groupTwistedBow",
			name = "Twisted bow",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 14,
			section = coxDropsColorSection
	)
	default ItemGroup groupTwistedBow()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupKodai",
			name = "Kodai insignia",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 15,
			section = coxDropsColorSection
	)
	default ItemGroup groupKodai()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupElderMaul",
			name = "Elder maul",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 16,
			section = coxDropsColorSection
	)
	default ItemGroup groupElderMaul()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupClaws",
			name = "Dragon claws",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 17,
			section = coxDropsColorSection
	)
	default ItemGroup groupClaws()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupAncestralHat",
			name = "Ancestral hat",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 18,
			section = coxDropsColorSection
	)
	default ItemGroup groupAncestralHat()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupAncestralTop",
			name = "Ancestral robe top",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 19,
			section = coxDropsColorSection
	)
	default ItemGroup groupAncestralTop()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupAncestralBottom",
			name = "Ancestral robe bottom",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 20,
			section = coxDropsColorSection
	)
	default ItemGroup groupAncestralBottom()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupDinhs",
			name = "Dinh's bulwark",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 21,
			section = coxDropsColorSection
	)
	default ItemGroup groupDinhs()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupDHCB",
			name = "Dragon hunter crossbow",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 22,
			section = coxDropsColorSection
	)
	default ItemGroup groupDHCB()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupBuckler",
			name = "Twisted buckler",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 23,
			section = coxDropsColorSection
	)
	default ItemGroup groupBuckler()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupArcane",
			name = "Arcane prayer scroll",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 24,
			section = coxDropsColorSection
	)
	default ItemGroup groupArcane()
	{
		return ItemGroup.NONE;
	}

	@ConfigItem(
			keyName = "groupDex",
			name = "Dexterous prayer scroll",
			description = "Group color to use when this item is obtained. If no group is specified, the 'unique' color will be used",
			position = 25,
			section = coxDropsColorSection
	)
	default ItemGroup groupDex()
	{
		return ItemGroup.NONE;
	}
}
