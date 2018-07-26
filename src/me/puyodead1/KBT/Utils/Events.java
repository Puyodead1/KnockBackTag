package me.puyodead1.KBT.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerJoinEvent;

import me.puyodead1.KBT.KnockBackTag;
import me.puyodead1.KBT.Game.Game;

public class Events implements Listener{
	FileConfiguration userconfig = null;
	 
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerJoins(PlayerJoinEvent e) throws IOException {
    	File userdata = new File(KnockBackTag.getInstance().getDataFolder()+File.separator+"userdata"+File.separator+e.getPlayer().getUniqueId().toString()+".yml");
    	File userdatadir = new File(KnockBackTag.getInstance().getDataFolder()+File.separator+"userdata");
    	
    	if(!userdatadir.exists()) {
    		userdatadir.mkdirs();
    		if(!userdata.exists()) {
        		userdata.createNewFile();
        		userconfig = YamlConfiguration.loadConfiguration(userdata);
        		userconfig.set("Username", e.getPlayer().getName());
        		userconfig.set("UUID", e.getPlayer().getUniqueId().toString());
        		userconfig.set("Stats.KBStat1", 0);
        		userconfig.set("Stats.KBStat2", 0);
        		userconfig.set("Stats.KBStat3", 0);
        		userconfig.set("Stats.KBStat4", 0);
        		userconfig.save(userdata);
        	}
    	} else {
    		if(!userdata.exists()) {
        		userdata.createNewFile();
        		userconfig = YamlConfiguration.loadConfiguration(userdata);
        		userconfig.set("Username", e.getPlayer().getName());
        		userconfig.set("UUID", e.getPlayer().getUniqueId().toString());
        		userconfig.set("Stats.KBStat1", 0);
        		userconfig.set("Stats.KBStat2", 0);
        		userconfig.set("Stats.KBStat3", 0);
        		userconfig.set("Stats.KBStat4", 0);
        		userconfig.save(userdata);
        	}
    	}
    	System.out.println("User data loaded for " + e.getPlayer().getName());
    }
    @EventHandler
    public void onITArmorRemove(InventoryClickEvent e) {
    	Player p = (Player)e.getWhoClicked();
    	if(p == Game.isIT) {
    		if(e.getSlotType() == SlotType.ARMOR) {
    			e.setCancelled(true);
    		}
    	}
    }
}
