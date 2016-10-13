
package me.endureblackout.sn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.endureblackout.sn.utils.ParticleEffect;

public class Angel implements Listener {

	SNMain core;

	public Angel(SNMain instance) {
		this.core = instance;
	}

	@EventHandler
	public void onInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getAction().toString().startsWith("RIGHT_CLICK")) {
			if (p.getInventory().getItemInMainHand().equals(Material.FEATHER)) {
				ItemStack wings = p.getInventory().getItemInMainHand();
				ItemMeta wingMeta = wings.getItemMeta();

				if (ChatColor.stripColor(wingMeta.getDisplayName()).equalsIgnoreCase("wings")) {
					if (!p.getAllowFlight() == true) {
						p.setAllowFlight(true);
						p.setFlying(true);
					} else {
						p.setAllowFlight(false);
						p.setFlying(false);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		if (e.getEntity().getKiller() instanceof Player) {
			Player k = e.getEntity().getKiller();

			for (Entry<UUID, String> t : CommandHandler.roles.entrySet()) {
				if (t.getKey().equals(p.getUniqueId())) {
					if (t.getValue().equalsIgnoreCase("angel")) {
						if (k.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("Angel Blade")) {
							e.setDeathMessage("");
							new BukkitRunnable() {

								public void run() {
									if (!(p.isDead())) {
										p.sendMessage(ChatColor.RED + "[SN]" + ChatColor.AQUA + " You were killed by an angel blade!");
										cancel();
									}
								}
							}.runTaskTimer(core, 0, 1);
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerDamageEvent(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();

			ItemStack blade = d.getInventory().getItemInMainHand();
			ItemMeta bladeMeta = blade.getItemMeta();

			if (e.getCause().equals(DamageCause.ENTITY_ATTACK)) {
				if (ChatColor.stripColor(bladeMeta.getDisplayName()).equalsIgnoreCase("Angel Blade")) {
					for (Entry<UUID, String> k : CommandHandler.roles.entrySet()) {
						if (k.getKey().equals(p.getUniqueId())) {
							if (k.getValue().equalsIgnoreCase("hunter") || k.getValue().equalsIgnoreCase("vampire") || k.getValue().equalsIgnoreCase("shapeshifter")) {
								System.out.println("Doing this");
								e.setDamage(e.getDamage() + 1.5);
							}

							if (k.getValue().equalsIgnoreCase("angel")) {
								List<Player> players = new ArrayList<>();
								players.addAll(Bukkit.getOnlinePlayers());
								p.setHealth(0);
								ParticleEffect.FIREWORKS_SPARK.display(0.0f, 0.0f, 0.0f, 0.0f, 30, p.getLocation(), players);
								Bukkit.broadcastMessage(ChatColor.RED + "[SN]" + ChatColor.AQUA + " An angel has just been killed!");
							}
						}
					}
				}
			}
		}
	}
}
