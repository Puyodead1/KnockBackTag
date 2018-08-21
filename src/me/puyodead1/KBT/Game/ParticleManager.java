package me.puyodead1.KBT.Game;

import org.bukkit.Effect;
import org.bukkit.entity.Player;

public class ParticleManager {

	@SuppressWarnings("deprecation")
	public ParticleManager(Player player) {
		while(Game.isIT == player) {
			player.playEffect(player.getLocation(), Effect.CLOUD, 5);
		}
	}
}
