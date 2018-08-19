package me.puyodead1.KBT.Game;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.puyodead1.KBT.KnockBackTag;

public class KBTStat3 {
	
private Player player;
	
	public KBTStat3(Player player) {
		this.player = player;
		
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if((Game.isIT != player) && (Game.players.contains(player))) {
					//Increment
					userconfig.set("Stats.KBTStat3", userconfig.getInt("Stats.KBTStat3") + 1);
					try {
						userconfig.save(userdata);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(KnockBackTag.getInstance().getConfig().getBoolean("Debug")) {
						player.sendMessage("KBTStat3 + 1");
					}
				} else if(Game.isIT == player || Game.isIT == null || !Game.players.contains(player)){
					if(KnockBackTag.getInstance().getConfig().getBoolean("Debug")) {
						player.sendMessage("KBTStat3 Task Canceled");
					}
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(KnockBackTag.getInstance(), 1200L, 1200L);
	}
}
