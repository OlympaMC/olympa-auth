package fr.olympa.auth;

import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;

import fr.olympa.api.plugin.OlympaAPIPlugin;
import fr.olympa.auth.join.JoinListener;
import fr.olympa.auth.join.ProtectListener;
import fr.olympa.auth.login.LoginListener;

public class OlympaAuth extends OlympaAPIPlugin {

	private static OlympaAuth instance;

	public static OlympaAuth getInstance() {
		return instance;
	}

	private Location spawn;

	public Location getSpawn() {
		return this.spawn;
	}

	@Override
	public void onDisable() {
		this.sendMessage("§4" + this.getDescription().getName() + "§c (" + this.getDescription().getVersion() + ") est désativer.");
	}

	@Override
	public void onEnable() {
		instance = this;
		this.spawn = new Location(this.getServer().getWorlds().get(0), 0.5, 69.2, 0.5, -90, 0);

		PluginManager pluginManager = this.getServer().getPluginManager();
		pluginManager.registerEvents(new JoinListener(), this);
		pluginManager.registerEvents(new ProtectListener(), this);
		pluginManager.registerEvents(new LoginListener(), this);

		this.sendMessage("§2" + this.getDescription().getName() + "§a (" + this.getDescription().getVersion() + ") est activé.");
	}
}
