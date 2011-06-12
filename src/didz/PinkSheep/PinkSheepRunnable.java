package didz.PinkSheep;

import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;

public class PinkSheepRunnable implements Runnable {
	private Sheep theSheep;
	
	public PinkSheepRunnable(Sheep sheep) {
		theSheep = sheep;
	}
	
	@Override
	public void run() {
		theSheep.setColor(DyeColor.PINK);
	}

}
