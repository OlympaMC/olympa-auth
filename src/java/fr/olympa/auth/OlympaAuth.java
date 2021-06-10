package fr.olympa.auth;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;

import fr.olympa.api.common.plugin.OlympaAPIPlugin;
import fr.olympa.api.common.server.OlympaServer;
import fr.olympa.auth.join.CancelActionListener;
import fr.olympa.auth.join.JoinListener;
import fr.olympa.auth.join.ProtectMapListener;
import fr.olympa.core.spigot.OlympaCore;

public class OlympaAuth extends OlympaAPIPlugin {

	private static OlympaAuth instance;

	public static OlympaAuth getInstance() {
		return instance;
	}

	private Location spawn;

	public Location getSpawn() {
		return spawn;
	}

	@Override
	public void onDisable() {
		sendMessage("§4" + getDescription().getName() + "§c (" + getDescription().getVersion() + ") est désativer.");
	}

	@Override
	public void onEnable() {
		instance = this;
		OlympaCore.getInstance().setOlympaServer(OlympaServer.AUTH);
		spawn = new Location(getServer().getWorlds().get(0), 0.5, 69.2, 0.5, -90, 0);

		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new JoinListener(), this);
		pluginManager.registerEvents(new ProtectMapListener(), this);
		pluginManager.registerEvents(new CancelActionListener(), this);

		sendMessage("§2" + getDescription().getName() + "§a (" + getDescription().getVersion() + ") est activé.");
	}
}
