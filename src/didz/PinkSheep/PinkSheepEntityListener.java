package didz.PinkSheep;

import java.util.logging.Logger;

//import org.bukkit.DyeColor;
//import org.bukkit.World;
import org.bukkit.entity.CreatureType;
//import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.scheduler.BukkitScheduler;

public class PinkSheepEntityListener extends EntityListener {
	private PinkSheep plugin;
	private Logger log;
	private BukkitScheduler scheduler;
	
	public PinkSheepEntityListener(PinkSheep pinkSheep) {
		plugin = pinkSheep;
		log = Logger.getLogger("Minecraft");
		log.info("PinkSheepEntityListener constructor called");

		scheduler = plugin.getServer().getScheduler();
	}

	public void onCreatureSpawn(CreatureSpawnEvent event) {
		//log.info("Creature spawned");
		if ( event.getCreatureType() == CreatureType.SHEEP && event.getEntity() instanceof Sheep )
		{
			//log.info("Sheep spawned!");
			PinkSheepRunnable task = new PinkSheepRunnable( (Sheep)event.getEntity() );
			int taskID = scheduler.scheduleSyncDelayedTask(plugin, task, 1);
			if (taskID == -1) {
				log.warning("Failed to create task to colour Sheep");
			}
		}
	}
}
