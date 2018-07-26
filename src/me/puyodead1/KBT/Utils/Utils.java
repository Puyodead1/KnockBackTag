package me.puyodead1.KBT.Utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class Utils {
	public static String ChatColor(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
	public static boolean isWithinCuboid(Location loc, Location pos1, Location pos2) {
		final Vector primVector = pos1.toVector(), secVector = pos2.toVector();
		
		final double locX = loc.getX();
		final double locY = loc.getY();
		final double locZ = loc.getZ();
		
		final int x = primVector.getBlockX();
		final int y = primVector.getBlockY();
		final int z = primVector.getBlockZ();
		
		final int x1 = secVector.getBlockX();
		final int y1 = secVector.getBlockY();
		final int z1 = secVector.getBlockZ();
		
		if((locX >= x && locX <= x1) || (locX <= x && locX >= x1))
			if((locZ >= z && locZ <= z1) || (locZ <= z && locZ >= z1))
				if((locY >= y && locY <= y1) || (locY <= y && locZ >= y1))
					return true;
		return false;
	}
}
