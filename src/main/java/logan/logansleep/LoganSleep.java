package main.java.logan.logansleep;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class LoganSleep extends PluginBase {
	public static Map<UUID, Boolean> sleeping = Maps.newHashMap();
	public static int sleepersRequired = 0;
	public static double percentageRequired = 25.0;
	
	@Override
	public void onLoad() {
		this.getLogger().info("Loaded!");
	}
	
	@Override
	public void onEnable() {
		this.getLogger().info("Enabled!");
		
		this.getLogger().info(String.valueOf(this.getDataFolder().mkdirs()));
		
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
		
		@SuppressWarnings({ "deprecation", "serial" })
		Config config = new Config(
				new File(this.getDataFolder(), "config.yml"),
				Config.YAML,
				new LinkedHashMap<String, Object>() {
					{
						put("percentageRequired", 25.0);
					}
				});
		this.getLogger().info(String.valueOf(config.get("percentageRequired", 25.0)));
		config.save();
		
		percentageRequired = config.getDouble("percentageRequired");
		
		sleepersRequired = (int) Math.floor(getServer().getOnlinePlayers().size() / 100.0 * percentageRequired);
		
		if (sleepersRequired < 1) sleepersRequired = 1;
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("Disabled!");
	}
}
