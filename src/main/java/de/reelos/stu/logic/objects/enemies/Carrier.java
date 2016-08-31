package de.reelos.stu.logic.objects.enemies;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Carrier extends Enemy {
	public Carrier(GameWorld parent, int x, int y) {
		super(parent, x, y, 20, 50);
	}
	
	public void drawMe(Graphics g) {
		try {
			g.drawImage(ImageIO.read(Carrier.class.getClassLoader().getResourceAsStream("./carrier.png")), x, y, width, height, null);
		} catch (IOException e) {
			super.drawMe(g);
		}
		drawHealth(g);
	}
}
