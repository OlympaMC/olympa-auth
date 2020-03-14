package fr.olympa.auth.login;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import fr.olympa.api.customevents.OlympaPlayerLoadEvent;
import fr.olympa.api.objects.OlympaPlayer;

public class LoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(OlympaPlayerLoadEvent event) {
		Player player = event.getPlayer();
		OlympaPlayer olympaPlayer = event.getOlympaPlayer();
		
		event.setJoinMessage(null);
	}
}
