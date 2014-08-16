package me.michaelkrauty.CarbonCore;

import me.michaelkrauty.CarbonCore.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created on 8/5/2014.
 *
 * @author michaelkrauty
 */
public class CarbonCore extends JavaPlugin {

	public static CarbonCore core;

	public static HashMap<Player, User> users = new HashMap<Player, User>();

	public static HashMap<UUID, Zombie> uuidZombieHashMap = new HashMap<UUID, Zombie>();
	public static HashMap<Zombie, Inventory> combatLoggers = new HashMap<Zombie, Inventory>();
	public static HashMap<Zombie, ItemStack[]> combatLoggersArmor = new HashMap<Zombie, ItemStack[]>();

	public void onEnable() {
		core = this;
		if (!getDataFolder().exists()) getDataFolder().mkdir();
		File userdata = new File(getDataFolder(), "users");
		if (!userdata.exists()) userdata.mkdir();
		for (Player player : getServer().getOnlinePlayers()) {
			users.put(player, new User(this, player));
		}
	}

	public void onDisable() {
		for (Player player : getServer().getOnlinePlayers()) {
			users.get(player).save();
		}
		users.clear();
	}
}
