package me.puyodead1.KBT.Game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import me.puyodead1.KBT.KnockBackTag;
import me.puyodead1.KBT.Utils.Utils;

public class Game {
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static Player isIT;
	public static int KBTStat2 = 0;


	public static ItemStack NetherStar() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.ChatColor("&7Leave Game &7(Right Click)"));
		item.setItemMeta(meta);

		return item;
	}

	public static ItemStack KnockBackStick() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Utils.ChatColor("&6Knock Back Stick"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Utils.ChatColor("&7Just a stick with knockback"));
		meta.setLore(lore);
		meta.addEnchant(Enchantment.KNOCKBACK, 5, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);

		return item;
	}

	public static void handleEnterGame(Player player) {
		if (players.size() == 1) {
			player.sendMessage(Utils.ChatColor("&7***&6You are the first player! You're &6&lIT&6!&7***"));
			
			isIT = player;
			new KBTStat2(player);
		} else {
			player.sendMessage(Utils.ChatColor("&7***&e" + isIT.getName() + " is IT! Avoid Them!&7***"));
			new KBTStat3(player);
		}
	}

	public static void gameInit(Player player) {
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + player.getName().toString() + ".inv.tmp.yml");
		FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);
		userconfig.set("Inventory", player.getInventory().getContents());
		try {
			userconfig.save(userdata);
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.getInventory().clear();
		player.getInventory().setItem(8, NetherStar());

		isIT.getInventory().setItem(0, KnockBackStick());
		isIT.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
		isIT.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
		isIT.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
		isIT.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
	}
}
