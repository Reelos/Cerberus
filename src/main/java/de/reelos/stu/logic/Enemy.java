package de.reelos.stu.logic;

import java.awt.Color;

public class Enemy extends GameObject {

	public Enemy(int x, int y, int height, int width) {
		this(x, y, height, width, (height * width) / 2, 0, 0, 0.001f, 0.1f);
	}

	public Enemy(int x, int y, int height, int width, int life, int dirX, int dirY, float xm, float ym) {
		super(x, y, height, width, life, dirX, dirY, xm, ym);
	}
	
	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}

	@Override
	public GOType getType() {
		return GOType.ENEMY;
	}

}
