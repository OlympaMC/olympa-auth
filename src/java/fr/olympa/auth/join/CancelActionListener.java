package fr.olympa.auth.join;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.olympa.api.utils.ColorUtils;
import fr.olympa.api.utils.Prefix;

public class CancelActionListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			player.sendMessage(Prefix.DEFAULT_BAD + ColorUtils.color("Impossible de discuter ici."));
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE) {
			player.sendMessage(Prefix.DEFAULT + ColorUtils.color("Pour te connecter, fait /login <mot de passe>"));
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location playerLoc = player.getLocation();
		Location toLoc = event.getTo();
		if (player.getGameMode() != GameMode.CREATIVE && (playerLoc.getX() != toLoc.getX() || playerLoc.getZ() != toLoc.getZ()))
			event.setTo(event.getFrom());
	}
}
