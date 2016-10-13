
package me.endureblackout.sn;

import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Vampire implements Listener {

	SNMain core;

	public Vampire(SNMain instance) {
		this.core = instance;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		for (Entry<UUID, String> k : CommandHandler.roles.entrySet()) {
			Player p = e.getPlayer();

			if (k.getKey().equals(p.getUniqueId()) && k.getValue().equalsIgnoreCase("vampire")) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			if (e.getHand().equals(EquipmentSlot.HAND)) {
				for (Entry<UUID, String> k : CommandHandler.roles.entrySet()) {
					final Player vic = (Player) e.getRightClicked();
					Player p = e.getPlayer();

					if (k.getKey().equals(p.getUniqueId())) {
						p.sendMessage(ChatColor.RED + "[SN] You sucked all the blood out of " + vic.getName() + "!");
						vic.setHealth(0);

						new BukkitRunnable() {

							public void run() {
								if (!vic.isDead()) {
									vic.sendMessage(ChatColor.RED + "[SN] You were killed by a vampire!");
									cancel();
								}
							}
						}.runTaskTimer(this.core, 0, 1);
					}
				}

			}
		}

	}
}
