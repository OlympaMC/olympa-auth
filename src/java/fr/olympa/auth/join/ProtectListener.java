package fr.olympa.auth.join;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ProtectListener implements Listener {

	private boolean canI(Player player) {
		return player.getGameMode() == GameMode.CREATIVE;
	}

	@EventHandler(ignoreCancelled = false)
	private void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!this.canI(event.getPlayer())) {
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (!this.canI(event.getPlayer())) {
			player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onEntityDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player && event.getCause() != DamageCause.SUICIDE && event.getCause() != DamageCause.VOID) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {
			if (!(event.getEntity() instanceof Player)) {
				if (!this.canI((Player) event.getDamager())) {
					((Player) event.getDamager()).updateInventory();
					event.setCancelled(true);
				}
			}
		} else if (!(event.getEntity() instanceof Player)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (event.getEntity() instanceof Player) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onHangingBreak(HangingBreakByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) || !this.canI((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onHangingPlace(HangingPlaceEvent event) {
		if (!this.canI(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onPlayerDropItem(PlayerDropItemEvent event) {
		if (!this.canI(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onPlayerEggThrow(PlayerEggThrowEvent event) {
		if (!event.isHatching()) {
			return;
		}
		if (!this.canI(event.getPlayer())) {
			event.setHatching(false);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.PHYSICAL) && event.getClickedBlock().getType().equals(Material.FARMLAND)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		if (!this.canI(event.getPlayer())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	private void onPlayerPickupItem(EntityPickupItemEvent event) {
		if (!(event.getEntity() instanceof Player) || !this.canI((Player) event.getEntity())) {
			event.setCancelled(true);
		}
	}

	@EventHandler(ignoreCancelled = false)
	public void onWeatherChange(WeatherChangeEvent event) {
		World world = event.getWorld();
		if (world.hasStorm()) {
			world.setWeatherDuration(0);
		}
	}
}
