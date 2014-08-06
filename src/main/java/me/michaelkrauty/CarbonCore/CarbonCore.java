package me.michaelkrauty.CarbonCore;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 8/5/2014.
 *
 * @author michaelkrauty
 */
public class CarbonCore extends JavaPlugin {

	private static CarbonCore carbonCore;

	public void onEnable() {
		carbonCore = this;
	}

	public static void testVoid() {
		carbonCore.getLogger().info("test void works.");
	}
}
