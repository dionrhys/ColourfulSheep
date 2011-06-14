package didz.ColourfulSheep;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.DyeColor;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.scheduler.BukkitScheduler;

public class ColourfulSheepEntityListener extends EntityListener {
	private ColourfulSheep plugin;
	private Logger log;
	private BukkitScheduler scheduler;
	static private Random rand = new Random();
	
	public ColourfulSheepEntityListener(ColourfulSheep colourfulSheep) {
		plugin = colourfulSheep;
		log = plugin.log;
		scheduler = plugin.getServer().getScheduler();
	}

	public void onCreatureSpawn(CreatureSpawnEvent event) {
		//log.info("Creature spawned");
		if ( event.getCreatureType() == CreatureType.SHEEP && event.getEntity() instanceof Sheep )
		{
			//log.info("Sheep spawned!");
			
			int randomChance = rand.nextInt(plugin.totalChance);
			int currentChance = 0;
			DyeColor theColor = DyeColor.WHITE;
			for (DyeColor color : plugin.colorChances.keySet()) {
				currentChance += plugin.colorChances.get(color);
				if (randomChance < currentChance) {
					theColor = color;
					break;
				}
			}
			
			ColourfulSheepRunnable task = new ColourfulSheepRunnable( (Sheep)event.getEntity(), theColor );
			int taskID = scheduler.scheduleSyncDelayedTask(plugin, task, 1);
			if (taskID == -1) {
				log.warning("Failed to create task to colour Sheep");
			}
		}
	}
}
