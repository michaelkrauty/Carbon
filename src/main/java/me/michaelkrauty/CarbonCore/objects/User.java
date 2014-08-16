package me.michaelkrauty.CarbonCore.objects;

import me.michaelkrauty.CarbonCore.CarbonCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import java.io.File;

/**
 * Created on 7/20/2014.
 *
 * @author michaelkrauty
 */
public class User {

	private final CarbonCore core;
	private static Player player;
	private boolean pvp;
	private boolean combat;
	private boolean deadLogin;
	private long combatTime;
	private File file;

	public User(CarbonCore core, Player player) {
		this.core = core;
		file = new File(core.getDataFolder() + "/users/" + player.getUniqueId().toString() + ".yml");
		if (!file.exists()) {
			pvp = false;
			combat = false;
			combatTime = 0;
			save();
		}
		core.users.put(player, this);
		this.player = player;
		load();
		core.getLogger().info("loaded user: " + player.getName());
		if (core.uuidZombieHashMap.get(player.getUniqueId()) instanceof Zombie) {
			Zombie zombie = core.uuidZombieHashMap.get(player.getUniqueId());
			core.combatLoggers.remove(zombie);
			core.combatLoggersArmor.remove(zombie);
			zombie.remove();
		}
		if (deadLogin) {
			player.getInventory().setArmorContents(null);
			player.getInventory().setContents(new ItemStack[]{null});
			player.setHealth(0);
			core.getServer().broadcastMessage(ChatColor.RED + player.getName() + " logged out in combat, and was killed!");
		}
		final Player playerFinal = player;
		core.getServer().getScheduler().scheduleSyncRepeatingTask(core, new Runnable() {
			public void run() {
				boolean start = combat;
				if (combatTime > 0)
					combatTime = combatTime - 1;
				else
					combat = false;
				if (start != combat)
					playerFinal.sendMessage(ChatColor.LIGHT_PURPLE + "You may now safely log out.");

			}
		}, 1, 1);
	}

	public void setPVP(boolean pvp) {
		this.pvp = pvp;
	}

	public boolean pvpEnabled() {
		return pvp;
	}

	public boolean inCombat() {
		return combat;
	}

	public long getCombatTime() {
		return combatTime;
	}

	public void pvpEvent() {
		combatTime = 200;
		combat = true;
		player.setFlying(false);
	}

	public void save() {
		YamlConfiguration yml = new YamlConfiguration();
		yml.set("pvp", pvp);
		try {
			if (!file.exists()) file.createNewFile();
			yml.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void asyncSave() {
		Bukkit.getScheduler().scheduleAsyncDelayedTask(core, new Runnable() {
			public void run() {
				save();
			}
		});
	}

	public void load() {
		YamlConfiguration yml = new YamlConfiguration();
		try {
			yml.load(file);
			pvp = yml.getBoolean("pvp");
			deadLogin = yml.getBoolean("deadlogin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void outOfCombat() {
		combat = false;
		combatTime = 0;
	}
}
