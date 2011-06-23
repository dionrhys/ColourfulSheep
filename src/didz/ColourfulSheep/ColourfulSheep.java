package didz.ColourfulSheep;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.DyeColor;

public class ColourfulSheep extends JavaPlugin {
	public Logger log;
	private PluginManager pm;
	private EntityListener entityListener;
	public Configuration config;
		
	public void onLoad() {
		log = Logger.getLogger("Minecraft");
		pm = this.getServer().getPluginManager();
		entityListener = new ColourfulSheepEntityListener( this );
	}
	
	public void onEnable() {
		loadConfig();
		buildColourChances();
		
		pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Lowest, this);
		log.info("ColourfulSheep plugin enabled.");
	}
	
	public void onDisable() {
		saveConfig();
		log.info("ColourfulSheep plugin disabled.");
	}
	
    private void loadConfig() {
    	this.getDataFolder().mkdir();
    	
        File configFile = new File(this.getDataFolder(), "config.yml");
        
        if (configFile.exists()) {
            config = new Configuration(configFile);
            config.load();
            
            // Load all dye colour chances and default to no chance
            for (DyeColor color : DyeColor.values()) {
            	String propName = "colourchance." + color.toString().toLowerCase();
            	if (config.getProperty(propName) == null) {
            		config.setProperty(propName, 0);
            	}
            }
        } else {
            try {
                configFile.createNewFile();
                config = new Configuration(configFile);
                // Default all colours to equal chance
                for (DyeColor color : DyeColor.values()) {
                	String propName = "colourchance." + color.toString().toLowerCase();
                	if (config.getProperty(propName) == null) {
                		config.setProperty(propName, 1);
                	}
                }

                saveConfig();
            } catch (IOException e) {
                log.warning(e.toString());
            }
        }
    }
    
    private void saveConfig() {
        config.setHeader("# Configuration file for ColourfulSheep");
        config.save();
    }
    
    public int totalChance = 0;
    public final Map<DyeColor, Integer> colorChances = new HashMap<DyeColor, Integer>();
    private void buildColourChances() {
        // Iterate all dye colour chances
        for (DyeColor color : DyeColor.values()) {
        	String propName = "colourchance." + color.toString().toLowerCase();
        	int chance = (Integer)config.getProperty(propName);
        	if (chance > 0) {
            	colorChances.put(color, chance);
            	totalChance += chance;
        	}
        }
        //log.info("totalChance = " + totalChance);
    }
}
