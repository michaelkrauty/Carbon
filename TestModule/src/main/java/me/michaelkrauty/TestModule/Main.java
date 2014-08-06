package me.michaelkrauty.TestModule;

import me.michaelkrauty.CarbonCore.CarbonCore;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created on 8/5/2014.
 *
 * @author michaelkrauty
 */
public class Main extends JavaPlugin {

	public void onEnable() {
		((CarbonCore) getServer().getPluginManager().getPlugin("CarbonCore")).testVoid();
	}
}
