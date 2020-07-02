package fr.olympa.auth.join;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.olympa.api.utils.ColorUtils;
import fr.olympa.api.utils.spigot.FireWorkUtils;
import fr.olympa.api.utils.spigot.SpigotUtils;
import fr.olympa.auth.OlympaAuth;
import fr.olympa.core.spigot.OlympaCore;

@SuppressWarnings("deprecation")
public class JoinListener implements Listener {

	public int clear(Player player) {
		final PlayerInventory inventory = player.getInventory();
		final int size = (int) (Arrays.stream(inventory.getContents()).filter(item -> item != null).count() + Arrays.stream(inventory.getArmorContents()).filter(item -> item != null).count());
		inventory.clear();
		inventory.setArmorContents(new ItemStack[inventory.getArmorContents().length]);
		return size;
	}

	public void init(Player player) {
		clear(player);
		player.setGameMode(GameMode.ADVENTURE);
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
		player.setMaxHealth(2);
		player.setHealth(player.getMaxHealth());
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setWalkSpeed(0.2f);
		player.setFlySpeed(0.1f);
		player.setFoodLevel(20);
		player.setExp(0);
		player.setLevel(0);
		player.setFlying(false);
		player.setCanPickupItems(false);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null)
			player.teleport(spawn);
		init(player);
		player.sendTitle(ColorUtils.color("&3⬣ &e&lOlympa &3 ⬣"), ColorUtils.color("&dBienvenue " + player.getName() + "!"), 0, 60, 0);
		player.setWalkSpeed(0);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false));
		OlympaCore.getInstance().getTask().runTaskLater(() -> FireWorkUtils.spawnWelcomeFireworks(player.getLocation()), 4 * 20);
		Bukkit.getOnlinePlayers().stream().forEach(p -> {
			player.hidePlayer(OlympaAuth.getInstance(), p);
			p.hidePlayer(OlympaAuth.getInstance(), player);
		});
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin2(PlayerJoinEvent event) {
		event.setJoinMessage(null);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null)
			player.teleport(spawn);
		init(player);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit2(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		// TODO TEST
		OlympaCore.getInstance().getTask().runTaskLater(() -> SpigotUtils.deletePlayerLocalData(player), 1);
		event.setQuitMessage(null);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		player.setWalkSpeed(0);
		Location spawn = OlympaAuth.getInstance().getSpawn();
		if (spawn != null)
			event.setRespawnLocation(spawn);
		init(player);
		player.setWalkSpeed(0.2f);
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128, false, false), true);
	}
}
