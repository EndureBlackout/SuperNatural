
package me.endureblackout.sn;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SNMain extends JavaPlugin {

	public void onEnable() {
		getCommand("sn").setExecutor(new CommandHandler(this));

		Bukkit.getServer().getPluginManager().registerEvents(new Vampire(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Angel(this), this);
	}
}
