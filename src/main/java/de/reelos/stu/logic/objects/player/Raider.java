package de.reelos.stu.logic.objects.player;


import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Raider extends Player {

	
	public Raider(GameWorld parent) {
		super(parent, 50, 20, 100, 100, 1F, 70);
		initVelocity(0, 0, 0.003f, 0.98F);
		try {
			image = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("./player0.png"));
		} catch(IOException e) {
			
		}
	}
}
