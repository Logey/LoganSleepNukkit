package main.java.logan.logansleep;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerBedEnterEvent;
import cn.nukkit.event.player.PlayerBedLeaveEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {
	private final LoganSleep plugin;
	
	public EventListener(LoganSleep plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {
		Player player = event.getPlayer();
		LoganSleep.sleeping.put(player.getUniqueId(), true);
		plugin.getServer().broadcastMessage(TextFormat.GREEN + player.getName() + TextFormat.GRAY + " has entered a bed. (" + TextFormat.YELLOW + LoganSleep.sleeping.size()
				+ "/" + LoganSleep.sleepersRequired + TextFormat.GRAY + ")");
		if (LoganSleep.sleeping.size() >= LoganSleep.sleepersRequired) {
			LoganSleep.sleeping.clear();
			if (player.getLevel().isThundering()) player.getLevel().setThundering(false);
			player.getLevel().setTime(6000);
			plugin.getServer().broadcastMessage(TextFormat.GREEN + "Good morning!");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
		Player player = event.getPlayer();
		LoganSleep.sleeping.remove(player.getUniqueId());
		plugin.getServer().broadcastMessage(TextFormat.RED + player.getName() + TextFormat.GRAY + " is no longer sleeping. (" + TextFormat.YELLOW + LoganSleep.sleeping.size()
				+ "/" + LoganSleep.sleepersRequired + TextFormat.GRAY + ")");
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		LoganSleep.sleepersRequired = (int) Math.floor(plugin.getServer().getOnlinePlayers().size() / 100.0 * LoganSleep.percentageRequired);
		if (LoganSleep.sleepersRequired < 1) LoganSleep.sleepersRequired = 1;
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerKickEvent(PlayerKickEvent event) {
		LoganSleep.sleepersRequired = (int) Math.floor(plugin.getServer().getOnlinePlayers().size() / 100.0 * LoganSleep.percentageRequired);
		if (LoganSleep.sleepersRequired < 1) LoganSleep.sleepersRequired = 1;
	}
}
