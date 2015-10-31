package me.tehkitti.AdminMode;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static String v = "1.0.0";
	public List<Player> hiddenFrom = new ArrayList<Player>();
	public static List<Player> invisiblepeople = new ArrayList<Player>();

	@Override
	public void onDisable() {
		pluginInfo("of AdminMode Disabled!");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable() {
		pluginInfo("of AdminMode Enabled!");
		getServer().getPluginManager()
				.registerEvents(new LoginListener(), this);
	}

	public static void pluginInfo(String message) {

		System.out.println("[AdminMode] Version " + v + " " + message);
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandlabel, String[] args) {
		ConsoleCommandSender console = getServer().getConsoleSender();
		if (commandlabel.equalsIgnoreCase("admin")) {
			if (sender instanceof ConsoleCommandSender) {
				console.sendMessage(ChatColor.RED
						+ "This command cannot be executed from the console!");
			}
			if (sender instanceof Player) {
				if (sender.hasPermission("adminmode.admin")) {
					GameMode gm = ((HumanEntity) sender).getGameMode();
					if (gm == GameMode.SURVIVAL) {
						sender.sendMessage(ChatColor.RED
								+ ""
								+ ChatColor.BOLD
								+ "You have been put in admin mode, and are now invisible to all players online.");
						((Player) sender).setGameMode(GameMode.CREATIVE);
						Bukkit.broadcastMessage(ChatColor.YELLOW + "" + sender.getName()
								+ " left the game");
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							p.hidePlayer((Player) sender);
							hiddenFrom.add(p);
							invisiblepeople.add((Player) sender);
						}
					}
					if (gm == GameMode.CREATIVE) {
						sender.sendMessage(ChatColor.RED
								+ ""
								+ ChatColor.BOLD
								+ "You have been put out of admin mode, and are now visible to all players online.");
						((Player) sender).setGameMode(GameMode.SURVIVAL);
						Bukkit.broadcastMessage(ChatColor.YELLOW + "" + sender.getName()
								+ " joined the game");
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							p.showPlayer((Player) sender);
							hiddenFrom.add(p);
							invisiblepeople.remove(sender);
						}
					}
				}

			}

			if (!(sender.hasPermission("adminmode.admin"))) {
				sender.sendMessage(ChatColor.RED + "No permission.");
			}

		}

		return true;
	}
}
