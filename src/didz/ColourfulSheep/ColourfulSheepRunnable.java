package didz.ColourfulSheep;

import org.bukkit.DyeColor;
import org.bukkit.entity.Sheep;

public class ColourfulSheepRunnable implements Runnable {
	private Sheep theSheep;
	private DyeColor theColor;
	
	public ColourfulSheepRunnable(Sheep sheep, DyeColor color) {
		theSheep = sheep;
		theColor = color;
	}
	
	@Override
	public void run() {
		theSheep.setColor(theColor);
	}

}
