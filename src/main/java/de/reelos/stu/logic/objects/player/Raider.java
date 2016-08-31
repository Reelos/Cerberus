package de.reelos.stu.logic.objects.player;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Raider extends Player {

	
	public Raider(GameWorld parent) {
		super(parent, 50, 20, 100, 100, 2F, 70);
	}

	@Override
	public void drawMe(Graphics g) {
		try {
			g.drawImage(ImageIO.read(Player.class.getClassLoader().getResourceAsStream("./player0.png")), x, y, width, height, null);
		} catch (IOException e) {
			super.drawMe(g);
		}
	}
}
