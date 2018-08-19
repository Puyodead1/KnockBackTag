package me.puyodead1.KBT.Game;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.puyodead1.KBT.KnockBackTag;

public class KBTStat2 {

	private Player player;
	
	public KBTStat2(Player player) {
		this.player = player;
		
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				/***
				 * This cancels the counter if the player is not it
				 */
				if(Game.isIT == null || Game.isIT.getUniqueId() != player.getUniqueId()){
					if(KnockBackTag.getInstance().getConfig().getBoolean("Debug")) {
						player.sendMessage("KBTStat2 Task Canceled");
					}
					this.cancel();
				}
				
				/***
				 * Increments the counter
				 */
				if(Game.isIT.getUniqueId() == player.getUniqueId()) {
					//Increment
					userconfig.set("Stats.KBTStat2", userconfig.getInt("Stats.KBTStat2") + 1);
					try {
						userconfig.save(userdata);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(KnockBackTag.getInstance().getConfig().getBoolean("Debug")) {
						player.sendMessage("KBTStat2 + 1");
					}
				}
			}
		}.runTaskTimerAsynchronously(KnockBackTag.getInstance(), 1200L, 1200L);
	}
}
