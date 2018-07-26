package me.puyodead1.KBT;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.puyodead1.KBT.AdvancedLicense.LogType;
import me.puyodead1.KBT.Commands.ArenaCoords;
import me.puyodead1.KBT.Game.Game;
import me.puyodead1.KBT.Utils.Events;
import me.puyodead1.KBT.Utils.Utils;

public class KnockBackTag extends JavaPlugin implements CommandExecutor {
	private static ArrayList<String> help;
	private static KnockBackTag instance;
	private Random r = new Random();
	private LogType logType = LogType.NORMAL;

	public KnockBackTag() {
		instance = this;
	}

	public static KnockBackTag getInstance() {
		return instance;
	}
	private void log(int type, String message){
		if(logType == LogType.NONE || ( logType == LogType.LOW && type == 0 )) return;
		System.out.println(message);
	}

	@Override
	public void onEnable() {
		if (!new AdvancedLicense(getConfig().getString("LicenseKey"), "http://licenceserverpuyodead1.000webhostapp.com/verify.php", this)
				.setSecurityKey("YecoF0I6M05thxLeokoHuW8iUhTdIUInjkfF").register()) {
			return;
		}

		File userdatadir = new File(getDataFolder() + File.separator + "userdata");
		if (!userdatadir.exists()) {
			System.out.println("userdata directory doesn't exist, it will be created.");
			userdatadir.mkdirs();
		}

		getConfig().options().copyDefaults(true);
		saveConfig();

		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(new Events(), this);

	}

	@Override
	public void onDisable() {

	}

	@SuppressWarnings("serial")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("EnterGame")) {
				if (!getConfig().getBoolean("ArenaCoordinate1Set") && (!getConfig().getBoolean("ArenaCoordinate2Set"))
						&& (!getConfig().getBoolean("ExitLocationSet"))) {
					sender.sendMessage(
							Utils.ChatColor("&7[&cERROR&7] &cThe Arena Location and Exit Location has not been set!"));
					return false;
				}
				if (!getConfig().getBoolean("ArenaCoordinate1Set")
						&& (!getConfig().getBoolean("ArenaCoordinate2Set"))) {
					sender.sendMessage(Utils.ChatColor("&7[&cERROR&7] &cThe Arena Location has not been set!"));
					return false;
				}
				if (!getConfig().getBoolean("ExitLocationSet")) {
					sender.sendMessage(Utils.ChatColor("&7[&cERROR&7] &cThe Exit Location has not been set!"));
					return false;
				} else {
					if (!Game.players.contains(player)) {
						Game.players.add(player);

						player.sendMessage(Utils.ChatColor("&eYou have been added to the Game!"));

						Location pos1 = new Location(
								Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.Pos1.World")),
								getConfig().getDouble("ArenaLocation.Pos1.X"),
								getConfig().getDouble("ArenaLocation.Pos1.Y"),
								getConfig().getDouble("ArenaLocation.Pos1.Z"));
						Location pos2 = new Location(
								Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.Pos2.World")),
								getConfig().getDouble("ArenaLocation.Pos2.X"),
								getConfig().getDouble("ArenaLocation.Pos2.Y"),
								getConfig().getDouble("ArenaLocation.Pos2.Z"));

						double randomX = pos1.getBlockX()
								+ new Random().nextDouble() * (pos2.getBlockX() - pos1.getBlockX());
						double randomY = pos1.getBlockY()
								+ new Random().nextDouble() * (pos2.getBlockY() - pos1.getBlockY());
						double randomZ = pos1.getBlockZ()
								+ new Random().nextDouble() * (pos2.getBlockZ() - pos1.getBlockZ());

						player.teleport(new Location(
								Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.Pos1.World")),
								randomX + 0.5, randomY + 0.5, randomZ + 0.5));
						Game.handleEnterGame(player);
						// Debug
						if (getConfig().getBoolean("Debug")) {
							player.sendMessage("X: " + randomX + 0.5 + " Y: " + randomY + 0.5 + " Z: " + randomZ + 0.5);
						}
						Game.gameInit(player);
						return true;
					} else {
						player.sendMessage(Utils.ChatColor("&cYou are already in the game!"));
						return false;
					}
				}
			}
			if (cmd.getName().equalsIgnoreCase("Leave")) {
				if (Game.players.contains(player)) {
					Game.players.remove(player);
					player.sendMessage(Utils.ChatColor("&eYou have left the game!"));
					if (Game.players.size() != 0) {
						Random rand = new Random();
						Player newIT = Game.players.get(rand.nextInt(Game.players.size()));
						Game.isIT = newIT;

						Bukkit.getServer().broadcastMessage(Utils.ChatColor("&c&l" + player.getName()
								+ " &e&lhas bailed out. &6&l" + newIT + " &eis now IT. Run Away!!"));
					} else {
						Bukkit.getServer().broadcastMessage(
								Utils.ChatColor("&cThe last Tag player has left, There is nobody else playing Tag!"));

					}
					if (!getConfig().getBoolean("ExitLocationSet")) {
						sender.sendMessage(Utils.ChatColor("&7[&cERROR&7] &cThe ExitLocation has not been set!"));
					} else {
						if (Game.isIT == player) {
							// Handles if player is it when leaving

							File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator
									+ "userdata" + File.separator + player.getUniqueId().toString() + ".yml");
							FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);

							userconfig.set("Stats.KBStat4", userconfig.getInt("Stats.KBStat4") + 1);
							try {
								userconfig.save(userdata);
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
						// Standard leave process
						Location exitLocation = new Location(
								Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.ExitLocation.World")),
								getConfig().getDouble("ArenaLocation.ExitLocation.X") + 0.5,
								getConfig().getDouble("ArenaLocation.ExitLocation.Y") + 0.5,
								getConfig().getDouble("ArenaLocation.ExitLocation.Z") + 0.5);
						player.teleport(exitLocation);

						player.getInventory().clear();
						File userdata = new File(KnockBackTag.getInstance().getDataFolder() + File.separator
								+ "userdata" + File.separator + player.getUniqueId().toString() + ".yml");
						FileConfiguration userconfig = YamlConfiguration.loadConfiguration(userdata);

						List<ItemStack> items = (List<ItemStack>) userconfig.getList("Inventory");
						player.getInventory().setContents(items.toArray(new ItemStack[items.size()]));
						userconfig.set("Inventory", null);
						try {
							userconfig.save(userdata);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Game.isIT = null;
						return true;
					}
					return false;
				} else {
					player.sendMessage(Utils.ChatColor("&cYou are not in the game!"));
					return false;
				}
			}
			if (cmd.getName().equalsIgnoreCase("mg-kbt")) {
				if (args.length == 0) {
					if (sender.isOp()) {
						help = new ArrayList<String>() {
							{
								add("&7-=-=-=-=-=-=KnockBackTag Minigame Help=-=-=-=-=-=-");
								add("&7          -=-=-=-=OP Commands=-=-=-=-");
								add(" ");
								add("&e/mg-kbt ArenaCoordinate1 - Sets the First Arena Coord from current X, Y Position.");
								add("&e/mg-kbt ArenaCoordinate2 - Sets the Second Arena Coord from current X, Y Position.");
								add("&e/mg-kbt ExitCoordinate - Sets the location where players are teleported when they leave the game. Can't be inside the Arena.");
								add("&e/mg-kbt <PlayerName> EnterGame - Transports the player to a random point	within the Arena.");
								add("&e/mg-kbt <PlayerName> LeaveGame - Teleports the player to Exit Location if defined.");
								add("&e/mg-kbt <PlayerName> IsIt - Selects the player to be 'IT'");
								add(" ");
								add("&7          -=-=-=-=Player Commands=-=-=-=-");
								add("&e/Playing - Lists players who are currently playing.");
								add("&e/Stats - Shows your stats!");
								add("&e/Stats <PlayerName> - List stats of specific player, online or offline.");
								add("&e/Leave - Teleports player to Exit Location.");
								add("&e/EnterGame - Transports the player to a random point within the Arena.");
							}
						};
						for (int i = 0; i < help.size(); i++) {
							sender.sendMessage(Utils.ChatColor(help.get(i)));
						}
						return true;
					} else {
						help = new ArrayList<String>() {
							{
								add("&7-=-=-=-=-=-=KnockBackTag Minigame Help=-=-=-=-=-=-");
								add(" ");
								add("&e/playing - Lists players who are currently playing.");
								add("&e/stats - Shows your stats!");
								add("&e/stats <PlayerName> - List stats of specific player, online or offline.");
								add("&e/Leave - Teleports player to Exit Location.");
							}
						};
						for (int i = 0; i < help.size(); i++) {
							sender.sendMessage(Utils.ChatColor(help.get(i)));
						}
						return true;
					}
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("ArenaCoordinate1")) {
						if (getConfig().getBoolean("ArenaCoordinate1Set")) {
							sender.sendMessage(
									Utils.ChatColor("&7[&cERROR&7] &cThe Arena Position 1 has already been set!"));
							return false;
						}
						ArenaCoords.setArenaCoord1((Player) sender);
						return true;
					} else if (args[0].equalsIgnoreCase("ArenaCoordinate2")) {
						if (getConfig().getBoolean("ArenaCoordinate2Set")) {
							sender.sendMessage(
									Utils.ChatColor("&7[&cERROR&7] &cThe Arena Position 2 has already been set!"));
							return false;
						}
						ArenaCoords.setArenaCoord2((Player) sender);
						return true;
					} else if (args[0].equalsIgnoreCase("ExitCoordinate")) {
						if (getConfig().getBoolean("ExitLocationSet")) {
							sender.sendMessage(
									Utils.ChatColor("&7[&cERROR&7] &cThe Exit Location has already been set!"));
							return false;
						}
						if (Utils.isWithinCuboid(player.getLocation(),
								new Location(
										Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.Pos1.World")),
										getConfig().getDouble("ArenaLocation.Pos1.X"),
										getConfig().getDouble("ArenaLocation.Pos1.Y"),
										getConfig().getDouble("ArenaLocation.Pos1.Z")),
								new Location(
										Bukkit.getServer().getWorld(getConfig().getString("ArenaLocation.Pos2.World")),
										getConfig().getDouble("ArenaLocation.Pos2.X"),
										getConfig().getDouble("ArenaLocation.Pos2.Y"),
										getConfig().getDouble("ArenaLocation.Pos2.Z")))) {
							sender.sendMessage(Utils.ChatColor(
									"&7[&cERROR&7] &cYou cannot set the Exit Location inside of the Arena!"));
							return false;
						}

						getConfig().set("ArenaLocation.ExitLocation.World", player.getLocation().getWorld().getName());
						getConfig().set("ArenaLocation.ExitLocation.X", player.getLocation().getBlockX());
						getConfig().set("ArenaLocation.ExitLocation.Y", player.getLocation().getBlockY());
						getConfig().set("ArenaLocation.ExitLocation.Z", player.getLocation().getBlockZ());
						getConfig().set("ExitLocationSet", true);
						saveConfig();

						player.sendMessage(Utils.ChatColor("&eExit Location Set!"));
						return true;
					}
				}
			}
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage(Utils.ChatColor("&7[&cERROR&7] &cYou must be a player!"));
			return false;
		}
		return false;
	}
}
