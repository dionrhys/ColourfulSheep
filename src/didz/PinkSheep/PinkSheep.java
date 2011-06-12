package didz.PinkSheep;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityListener;

public class PinkSheep extends JavaPlugin {
	public Logger log;
	private PluginManager pm;
	private EntityListener entityListener;
	
	public void onLoad() {
		log = Logger.getLogger("Minecraft");
		pm = this.getServer().getPluginManager();
		entityListener = new PinkSheepEntityListener( this );
	}
	
	public void onEnable() {
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Lowest, this);
		log.info("PinkSheep plugin enabled.");
	}
	public void onDisable() {
		log.info("PinkSheep plugin disabled.");
 
	}
}
