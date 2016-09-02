package de.reelos.stu.logic.objects.player;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Carrier extends Player {
	public Carrier(GameWorld parent) {
		super(parent, 50, 20, 150, 350, 1F, 20);
		initVelocity(0f, 0f, 0.01f, 0.95f);
		try {
			image = ImageIO.read(Player.class.getClassLoader().getResourceAsStream("./player1.png"));
		} catch (IOException e) {
		}
	}
}
