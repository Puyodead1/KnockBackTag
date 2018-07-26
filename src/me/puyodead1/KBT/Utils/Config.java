package me.puyodead1.KBT.Utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.puyodead1.KBT.KnockBackTag;

public class Config {
	private static FileConfiguration userconfig = null;
	
	public static FileConfiguration getPlayerConfig(Player player) {
		File userfile = new File(KnockBackTag.getInstance().getDataFolder()+File.separator+"userdata"+File.separator+player.getUniqueId()+".yml");
		if(userfile.exists()) {
			userconfig = YamlConfiguration.loadConfiguration(userfile);
			return userconfig;
		} else {
			return null;
		}
	}
}
