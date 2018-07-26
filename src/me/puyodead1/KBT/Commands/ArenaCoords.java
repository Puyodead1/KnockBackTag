package me.puyodead1.KBT.Commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.puyodead1.KBT.KnockBackTag;
import me.puyodead1.KBT.Utils.Utils;

public class ArenaCoords {
	private static Location pos1;
	private static Location pos2;

	public static void setArenaCoord1(Player player) {
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos1.World", player.getLocation().getWorld().getName());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos1.X", player.getLocation().getBlockX());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos1.Y", player.getLocation().getBlockY());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos1.Z", player.getLocation().getBlockZ());
		KnockBackTag.getInstance().getConfig().set("ArenaCoordinate1Set", true);
		KnockBackTag.getInstance().saveConfig();

		pos1 = player.getLocation();

		if (pos1 == pos2) {
			player.sendMessage(Utils.ChatColor("&cYou cannot set Pos2 and Pos1 at the same location!"));
		}
		player.sendMessage(Utils.ChatColor("&eArenaCoord 1 Set!"));
		// TODO:
	}

	public static void setArenaCoord2(Player player) {
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos2.World", player.getLocation().getWorld().getName());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos2.X", player.getLocation().getBlockX());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos2.Y", player.getLocation().getBlockY());
		KnockBackTag.getInstance().getConfig().set("ArenaLocation.Pos2.Z", player.getLocation().getBlockZ());
		KnockBackTag.getInstance().getConfig().set("ArenaCoordinate2Set", true);
		KnockBackTag.getInstance().saveConfig();

		pos2 = player.getLocation();

		if (pos2 == pos1) {
			player.sendMessage(Utils.ChatColor("&cYou cannot set Pos1 and Pos2 at the same location!"));
		}
		player.sendMessage(Utils.ChatColor("&eArenaCoord 2 Set!"));
		// TODO:
	}
}
