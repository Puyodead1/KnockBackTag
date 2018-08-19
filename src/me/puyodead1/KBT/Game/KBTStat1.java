package me.puyodead1.KBT.Game;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.puyodead1.KBT.KnockBackTag;

public class KBTStat1 {
private Player player;
	
	public KBTStat1(Player player) {
		this.player = player;
		
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);
		
		userconfig.set("Stats.KBTStat1", userconfig.getInt("Stats.KBTStat1") + 1);
	}
}
