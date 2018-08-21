package me.puyodead1.KBT.Game;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.puyodead1.KBT.KnockBackTag;

public class ParticleManager {

	@SuppressWarnings("deprecation")
	public ParticleManager(Player player) {
		new BukkitRunnable() {

			@Override
			public void run() {
				if (Game.isIT == player) {
					player.playEffect(player.getLocation(), Effect.CLOUD, 2);
				} else {
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(KnockBackTag.getInstance(), 0L, 2L);
	}
}
