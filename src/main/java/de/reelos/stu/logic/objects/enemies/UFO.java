package de.reelos.stu.logic.objects.enemies;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class UFO extends Enemy {
	public UFO(GameWorld parent, int x, int y) {
		super(parent, x, y, 20, 20);
	}
	
	@Override
	public void drawMe(Graphics g) {
		try {
			g.drawImage(ImageIO.read(UFO.class.getClassLoader().getResourceAsStream("./ufo.png")), x, y, width, height, null);
		} catch (IOException e) {
			super.drawMe(g);
		}
		drawHealth(g);
	}
}
