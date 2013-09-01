package uk.co.didz.colourfulsheep;

import java.util.EnumMap;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ColourfulSheep extends JavaPlugin implements Listener {
	protected Logger log;
	protected PluginManager pm;

	private Random random;
	private int totalChance;
	private EnumMap<DyeColor, Integer> colorChances;

	@Override
	public void onEnable() {
		log = getLogger();
		pm = getServer().getPluginManager();
		random = new Random(System.currentTimeMillis());

		loadConfig();

		pm.registerEvents(this, this);

		log.info("Enabled!");
	}

	@Override
	public void onDisable() {
		// Release all our handles now. This is helpful for the garbage
		// collector if the plugin object is kept after being disabled.
		log = null;
		pm = null;
		random = null;
		colorChances = null;

		log.info("Disabled!");
	}

	private void loadConfig() {
		FileConfiguration config = getConfig();
		config.options().header("Configuration file for ColourfulSheep");

		colorChances = new EnumMap<DyeColor, Integer>(DyeColor.class);
		totalChance = 0;

		// Load all dye colour chances and default to 0 chance
		for (DyeColor color : DyeColor.values()) {
			String propName = "colourchance." + color.toString().toLowerCase();
			if (!config.isSet(propName)) {
				// Add the colour to the config if it doesn't already exist
				config.set(propName, 0);
			}

			int chance = config.getInt(propName);
			if (chance > 0) {
				colorChances.put(color, chance);
				totalChance += chance;
			}
		}

		saveConfig();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntityType() == EntityType.SHEEP) {
			int randomChance = random.nextInt(totalChance);
			int currentChance = 0;

			DyeColor theColor = DyeColor.WHITE;
			for (DyeColor color : colorChances.keySet()) {
				currentChance += colorChances.get(color);
				if (randomChance < currentChance) {
					theColor = color;
					break;
				}
			}

			new ColourSheepTask((Sheep) event.getEntity(), theColor).runTaskLater(this, 1);
		}
	}

	class ColourSheepTask extends BukkitRunnable {

		private Sheep sheep;
		private DyeColor color;

		public ColourSheepTask(Sheep sheep, DyeColor color) {
			this.sheep = sheep;
			this.color = color;
		}

		@Override
		public void run() {
			sheep.setColor(color);
		}

	}
}
