package de.reelos.stu.logic.objects.enemies;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class UFO extends Enemy {
	public UFO(GameWorld parent, int x, int y) {
		super(parent, x, y, 20, 20);
		try {
			image = ImageIO.read(UFO.class.getClassLoader().getResourceAsStream("./ufo.png"));
		} catch (IOException e) {
		}
	}
}
