package de.reelos.stu.logic.objects.enemies;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Carrier extends Enemy {
	public Carrier(GameWorld parent, int x, int y) {
		super(parent, x, y, 20, 50);
		try {
			image = ImageIO.read(Carrier.class.getClassLoader().getResourceAsStream("./carrier.png"));
		} catch (IOException e) {
		}
	}
}
