package me.michaelkrauty.CarbonCore.listeners;

import me.michaelkrauty.CarbonCore.CarbonCore;
import me.michaelkrauty.CarbonCore.objects.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created on 8/15/2014.
 *
 * @author michaelkrauty
 */
public class PlayerListener implements Listener {

	private final CarbonCore core;

	public PlayerListener(CarbonCore core) {
		this.core = core;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (core.users.get(event.getPlayer()) == null)
			core.users.put(event.getPlayer(), new User(core, event.getPlayer()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		core.users.get(event.getPlayer()).asyncSave();
		core.users.remove(event.getPlayer());
	}
}
