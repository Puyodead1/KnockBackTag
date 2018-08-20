package me.puyodead1.KBT.Game;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.puyodead1.KBT.KnockBackTag;
import net.minecraft.server.v1_12_R1.EntityArmorStand;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;

@SuppressWarnings("deprecation")
public class StatsHologram {

	/*public static Hologram hologram1;
	
	public HolographicDisplaysAPI hologram = new HolographicDisplaysAPI();

	public static void CreateHologram(Player player) {
		List<String> cnfg = KnockBackTag.getInstance().getConfig().getStringList("StatsHologram");
		
		File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator + "userdata"
				+ File.separator + player.getUniqueId().toString() + ".yml");
		FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);
		
		KnockBackTag kbt = KnockBackTag.getInstance();
		String[] array = KnockBackTag.getInstance().getConfig().getStringList("StatsHologram").toArray(new String[0]);
		for(int i = 0; i < array.length; i++) {
			array[i] = array[i].replace("{PLAYER_NAME}", player.getName());
			array[i] = array[i].replace("{KBTSTAT_1}", ""+Config.getPlayerConfig(player).getInt("Stats.KBTStat1"));
			array[i] = array[i].replace("{KBTSTAT_2}", ""+Config.getPlayerConfig(player).getInt("Stats.KBTStat2"));
			array[i] = array[i].replace("{KBTSTAT_3}", ""+Config.getPlayerConfig(player).getInt("Stats.KBTStat3"));
			array[i] = array[i].replace("{KBTSTAT_4}", ""+Config.getPlayerConfig(player).getInt("Stats.KBTStat4"));
		}
		hologram1 = HolographicDisplaysAPI.createIndividualHologram(
				KnockBackTag.getInstance(),
				player.getLocation().add(0, 2, 0),
				player,
				Utils.ChatColor(array)
				);
		player.sendMessage("Created Hologram at current location!");
	}
	public static void DeleteHologram(Player player) {
		hologram1.delete();
		player.sendMessage("Deleted");
	}*/
	
	
	
	
	
	
	
	
	
	
	private List<EntityArmorStand> entitylist = new ArrayList<EntityArmorStand>();
    private String[] Text;
    private Location location;
    private double DISTANCE = 0.25D;
    int count;

    public StatsHologram(String[] Text, Location location) {
            this.Text = Text;
            this.location = location;
            create();
    }

   
    public void showPlayerTemp(final Player p,int Time){
        showPlayer(p);
            Bukkit.getScheduler().runTaskLater(KnockBackTag.getInstance(), new Runnable() {
                    public void run() {
                    hidePlayer(p);
                    }
            }, Time);
    }
   
   
    public void showAllTemp(final Player p,int Time){
            showAll();
            Bukkit.getScheduler().runTaskLater(KnockBackTag.getInstance(), new Runnable() {
                    public void run() {
                    hideAll();
                    }
            }, Time);
    }
   
    public void showPlayer(Player p) {
            for (EntityArmorStand armor : entitylist) {
                    PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
    }

    public void hidePlayer(Player p) {
            for (EntityArmorStand armor : entitylist) {
                    PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

            }
    }

    public void showAll() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                    for (EntityArmorStand armor : entitylist) {
                            PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(armor);
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }
            }
    }

    public void hideAll() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                    for (EntityArmorStand armor : entitylist) {
                            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(armor.getId());
                            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }
            }
    }

    private void create() {
            for (String Text : this.Text) {
                    EntityArmorStand entity = new EntityArmorStand(((CraftWorld) this.location.getWorld()).getHandle(),this.location.getX(), this.location.getY(),this.location.getZ());
                    entity.setCustomName(Text);
                    entity.setCustomNameVisible(true);
                    entity.setInvisible(true);
                    entity.setNoGravity(true);
                    entitylist.add(entity);
                    this.location.subtract(0, this.DISTANCE, 0);
                    count++;
            }

            for (int i = 0; i < count; i++) {
                    this.location.add(0, this.DISTANCE, 0);
            }
            this.count = 0;
    }
	
}
