package com.WIZLights;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class WIZLightsPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(WIZLightsPlugin.class);
		RuneLite.main(args);
	}
}