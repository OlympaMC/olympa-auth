package fr.olympa.auth.login;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import fr.olympa.api.customevents.OlympaPlayerLoadEvent;
import fr.olympa.api.objects.OlympaPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class LoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoin(OlympaPlayerLoadEvent event) {
		Player player = event.getPlayer();
		OlympaPlayer olympaPlayer = event.getOlympaPlayer();

		TextComponent textComponent = new TextComponent();
		TextComponent textComponent2 = new TextComponent("----------------");
		textComponent2.setColor(ChatColor.DARK_RED);
		textComponent2.setBold(true);
		textComponent2.setObfuscated(true);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent(" [");
		textComponent2.setColor(ChatColor.GRAY);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("Login");
		textComponent2.setColor(ChatColor.RED);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("] ");
		textComponent2.setColor(ChatColor.GRAY);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("----------------\n");
		textComponent2.setColor(ChatColor.DARK_RED);
		textComponent2.setBold(true);
		textComponent2.setObfuscated(true);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("Bienvenue sur ");
		textComponent2.setColor(ChatColor.AQUA);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("Olympa");
		textComponent2.setColor(ChatColor.GOLD);
		textComponent2.setBold(true);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent(". Afin de sécuriser la connexion à notre service tu dois enter un mot de passe. Il sera le même sur le site et sur le forum.\n");
		textComponent2.setColor(ChatColor.AQUA);
		textComponent.addExtra(textComponent2);

		textComponent2 = new TextComponent("Fait ");
		textComponent2.setColor(ChatColor.GREEN);

		TextComponent textComponent3 = new TextComponent("/register <mot de passe>");
		textComponent3.setColor(ChatColor.DARK_GREEN);
		textComponent2.addExtra(textComponent3);

		textComponent3 = new TextComponent(" ou ");
		textComponent3.setColor(ChatColor.GREEN);
		textComponent2.addExtra(textComponent3);

		textComponent3 = new TextComponent("clique-ici");
		textComponent3.setColor(ChatColor.DARK_GREEN);
		textComponent2.addExtra(textComponent3);

		textComponent3 = new TextComponent("et écrit ton mot de passe.\n");
		textComponent3.setColor(ChatColor.GREEN);
		textComponent2.addExtra(textComponent3);

		textComponent2.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("Clique pour avoir directement la commande").create()));
		textComponent2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/register "));
		textComponent.addExtra(textComponent2);

		textComponent3 = new TextComponent("\n\n");
		textComponent2.addExtra(textComponent3);

		textComponent2 = new TextComponent("---------------------------------------");
		textComponent2.setColor(ChatColor.DARK_RED);
		textComponent2.setBold(true);
		textComponent2.setObfuscated(true);
		textComponent.addExtra(textComponent2);

		player.spigot().sendMessage(textComponent);
		event.setJoinMessage(null);
	}
}
