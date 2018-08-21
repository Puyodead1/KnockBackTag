package me.puyodead1.KBT.Utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.puyodead1.KBT.KnockBackTag;
import me.puyodead1.KBT.Game.Game;
import me.puyodead1.KBT.Game.KBTStat2;
import me.puyodead1.KBT.Game.KBTStat3;
import me.puyodead1.KBT.Game.ParticleManager;
import me.puyodead1.KBT.Game.StatsHologram;

public class Events implements Listener {
	FileConfiguration userconfig = null;

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerJoins(PlayerJoinEvent e) throws IOException {
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + e.getPlayer().getUniqueId().toString() + ".yml");
		File userdatadir = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata");

		if (!userdatadir.exists()) {
			userdatadir.mkdirs();
			if (!userdata.exists()) {
				userdata.createNewFile();
				userconfig = YamlConfiguration.loadConfiguration(userdata);
				userconfig.set("Username", e.getPlayer().getName());
				userconfig.set("UUID", e.getPlayer().getUniqueId().toString());
				userconfig.set("Stats.KBTStat1", 0);
				userconfig.set("Stats.KBTStat2", 0);
				userconfig.set("Stats.KBTStat3", 0);
				userconfig.set("Stats.KBTStat4", 0);
				userconfig.save(userdata);
			}
		} else {
			if (!userdata.exists()) {
				userdata.createNewFile();
				userconfig = YamlConfiguration.loadConfiguration(userdata);
				userconfig.set("Username", e.getPlayer().getName());
				userconfig.set("UUID", e.getPlayer().getUniqueId().toString());
				userconfig.set("Stats.KBTStat1", 0);
				userconfig.set("Stats.KBTStat2", 0);
				userconfig.set("Stats.KBTStat3", 0);
				userconfig.set("Stats.KBTStat4", 0);
				userconfig.save(userdata);
			}
		}
		System.out.println("User data loaded for " + e.getPlayer().getName());

		// Hologram
		Player player = e.getPlayer();
		KnockBackTag kbt = KnockBackTag.getInstance();
		Location location = new Location(kbt.getServer().getWorld(kbt.getConfig().getString("HologramLocation.world")),
				kbt.getConfig().getDouble("HologramLocation.x"), kbt.getConfig().getDouble("HologramLocation.y"),
				kbt.getConfig().getDouble("HologramLocation.z"));
		String[] array = KnockBackTag.getInstance().getConfig().getStringList("StatsHologram").toArray(new String[0]);
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].replace("{PLAYER_NAME}", player.getName());
			array[i] = array[i].replace("{KBTSTAT_1}", "" + Config.getPlayerConfig(player).getInt("Stats.KBTStat1"));
			array[i] = array[i].replace("{KBTSTAT_2}", "" + Config.getPlayerConfig(player).getInt("Stats.KBTStat2"));
			array[i] = array[i].replace("{KBTSTAT_3}", "" + Config.getPlayerConfig(player).getInt("Stats.KBTStat3"));
			array[i] = array[i].replace("{KBTSTAT_4}", "" + Config.getPlayerConfig(player).getInt("Stats.KBTStat4"));
		}
		StatsHologram holo = new StatsHologram(Utils.ChatColor(array), location);
		holo.showPlayer(player);
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void invClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (p == Game.isIT) {
			if (e.getSlotType() == SlotType.ARMOR) {
				e.setCancelled(true);
				return;
			}
		}
	}

	@EventHandler
	public void clickItems(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (p.getInventory().getItemInHand().getType() == Material.NETHER_STAR) {
			KnockBackTag.LeaveGame(p);
		}
	}

	@EventHandler
	public void tagPlayer(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

			Player d = (Player) e.getDamager();
			Player a = (Player) e.getEntity();
			new KBTStat2(a);
			new KBTStat3(d);
			
			if (d.getItemInHand().getItemMeta().getDisplayName().equals(Utils.ChatColor("&6Knock Back Stick"))
					&& (d.getItemInHand().getType() == Material.STICK)) {
				d.sendMessage(Utils.ChatColor("&eYou Tagged " + a.getName()));
				Game.isIT = a;
				new ParticleManager(a);

				a.getInventory().clear();
				a.getInventory().setContents(d.getInventory().getContents());

				d.getInventory().clear();
				d.getInventory().setItem(8, Game.NetherStar());
				a.sendMessage(Utils.ChatColor("&eYou were Tagged by " + d.getName()));
				Bukkit.getServer().broadcastMessage(Utils.ChatColor("&6&l" + d.getName() + " &ehas Tagged &6&l"
						+ a.getName() + "&e. &6&l" + a.getName() + " &eis now IT, run for your lives!!"));

			} else {
				return;
			}
			if (Game.players.contains(d) || Game.players.contains(a)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void itemDrop(PlayerDropItemEvent e) {
		if (Game.players.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
}
