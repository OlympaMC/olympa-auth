package fr.olympa.auth.join;

import java.util.Arrays;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.olympa.api.firework.FireWorkUtils;
import fr.olympa.api.localdata.PlayerLocalData;
import fr.olympa.api.utils.SpigotUtils;
import fr.olympa.auth.OlympaAuth;
import fr.olympa.core.spigot.OlympaCore;

public class JoinListener implements Listener {

	public int clear(Player player) {
		final PlayerInventory inventory = player.getInventory();
		final int size = (int) (Arrays.stream(inventory.getContents()).filter(item -> item != null).count() + Arrays.stream(inventory.getArmorContents()).filter(item -> item != null).count());
		inventory.clear();
		inventory.setArmorContents(new ItemStack[inventory.getArmorContents().length]);
		return size;
	}

	public void init(Player player) {
		this.clear(player);
		player.setGameMode(GameMode.ADVENTURE);
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
		player.setHealthScale(2);
		player.setHealth(player.getHealthScale());
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setFlySpeed(0.1f);
		player.setFoodLevel(20);
		player.setExp(0);
		player.setFlying(false);
		player.setCanPickupItems(false);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setWalkSpeed(0);
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null) {
			player.teleport(spawn);
		}
		this.init(player);
		player.sendTitle(SpigotUtils.color("&3⬣ &e&lOlympa &3 ⬣"), SpigotUtils.color("&dBienvenue " + player.getName() + "!"), 0, 40, 0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, -10, false, false), true);
		OlympaCore.getInstance().getTask().runTaskLater(() -> FireWorkUtils.spawnWelcomeFireworks(player.getLocation()), 2 * 20);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		player.setWalkSpeed(0.2f);
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null) {
			player.teleport(spawn);
		}
		this.init(player);
		PlayerLocalData.delete(player);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		player.setWalkSpeed(0);
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null) {
			event.setRespawnLocation(spawn);
		}
		this.init(player);
		player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, Integer.MAX_VALUE, -10, false, false), true);
	}
}
