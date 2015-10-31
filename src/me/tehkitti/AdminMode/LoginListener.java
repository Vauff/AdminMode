package me.tehkitti.AdminMode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {
	Main main;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		for (Player p : Main.invisiblepeople) {
			player.hidePlayer(p);
		}
		if ((player.getGameMode() == GameMode.CREATIVE)) {
			event.setJoinMessage("");
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD
					+ "You were in admin mode when you left the game and therefore still are, to come out of admin mode type /admin");
			List<Player> hiddenFrom = new ArrayList<Player>();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				p.hidePlayer(player);
				hiddenFrom.add(p);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE) {
			event.setQuitMessage("");
		}
		Main.invisiblepeople.remove(player);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE) {
			event.setLeaveMessage("");
		}
		Main.invisiblepeople.remove(player);
	}
}