
package me.endureblackout.sn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.endureblackout.sn.utils.Title;

public class CommandHandler implements CommandExecutor {

	SNMain							core;
	Title							title	= new Title();
	public static Map<UUID, String>	roles	= new HashMap<UUID, String>();

	public CommandHandler(SNMain instance) {
		this.core = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("sn")) {
				if (args.length == 1) {
					if (roles.containsKey(p.getUniqueId())) {
						p.sendMessage(ChatColor.RED + "[SN] You have already selected your role! If you want a new role contact and admin!");
					}

					if (args[0].equalsIgnoreCase("vampire")) {
						title.setSubtitle(ChatColor.RED + "" + ChatColor.BOLD + "You are now a vampire!");
						title.send(p);
						roles.put(p.getUniqueId(), "vampire");
						p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
					}

					if (args[0].equalsIgnoreCase("angel")) {
						title.setSubtitle(ChatColor.BOLD + "You are now an angel!");
						title.send(p);
						roles.put(p.getUniqueId(), "angel");

						ItemStack angelBlade = new ItemStack(Material.IRON_SWORD);
						ItemMeta bladeMeta = angelBlade.getItemMeta();

						ItemStack wings = new ItemStack(Material.FEATHER);
						ItemMeta wingsMeta = wings.getItemMeta();

						List<String> bladeLore = new ArrayList<>();
						List<String> wingLore = new ArrayList<>();

						bladeMeta.setDisplayName("Angel Blade");
						bladeLore.add(ChatColor.AQUA + "Can kill anything holy or unholy.");
						bladeMeta.setLore(bladeLore);
						angelBlade.setItemMeta(bladeMeta);
						wingsMeta.setDisplayName(ChatColor.AQUA + "Wings");
						wingLore.add(ChatColor.AQUA + "Spread your wings angel and fly away");
						wingsMeta.setLore(wingLore);
						wings.setItemMeta(wingsMeta);
						p.getInventory().addItem(wings);
						p.getInventory().addItem(angelBlade);
					}

					if (args[0].equalsIgnoreCase("demon")) {
						title.setSubtitle(ChatColor.BLACK + "You are now a demon!");
						title.send(p);
						roles.put(p.getUniqueId(), "demon");
					}

					if (args[0].equalsIgnoreCase("shapeshifter")) {
						title.setSubtitle(ChatColor.GRAY + "You are now a shapeshifter");
						title.send(p);
						roles.put(p.getUniqueId(), "shapeshifter");
					}

					if (args[0].equalsIgnoreCase("reaper")) {
						title.setSubtitle(ChatColor.BLACK + "You are now a reaper!");
						title.send(p);
						roles.put(p.getUniqueId(), "reaper");
					}

					if (args[0].equalsIgnoreCase("hunter")) {
						title.setSubtitle(ChatColor.RED + "You are now a hunter!");
						title.send(p);
						roles.put(p.getUniqueId(), "hunter");
					}
				}
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("angel")) {
						for (Entry<UUID, String> k : roles.entrySet()) {
							if (k.getKey().equals(p.getUniqueId()) && k.getValue().equalsIgnoreCase("angel")) {
								if (args[1].equalsIgnoreCase("teleport")) {

								}

								if (args[1].equalsIgnoreCase("fly")) {
									p.setAllowFlight(true);
									p.setFlying(true);
								}
							}

						}
					}
				}

			}
		}

		return true;
	}

}
