package de.reelos.stu.logic.objects.player;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.reelos.stu.logic.GameWorld;

public class Carrier extends Player {
	public Carrier(GameWorld parent) {
		super(parent, 50, 20, 150, 350, 1F, 20);
		initVelocity(0f, 0f, 0.01f, 0.5f);
	}

	@Override
	public void drawMe(Graphics g) {
		try {
			g.drawImage(ImageIO.read(Player.class.getClassLoader().getResourceAsStream("./player1.png")), x, y, width, height, null);
		} catch (IOException e) {
			super.drawMe(g);
		}
	}
}
