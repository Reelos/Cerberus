package de.reelos.stu.logic;

import java.awt.Color;

public class Enemy extends GameObject {

	private float fireDelay = 1f;
	private float fireTimer = 0f;
	private GameWorld parent;

	public Enemy(GameWorld parent, int x, int y, int height, int width) {
		this(parent, x, y, height, width, (height * width) / 2, 0, 0, 0.001f, 0.1f);
	}

	public Enemy(GameWorld parent, int x, int y, int height, int width, int life, int dirX, int dirY, float xm,
			float ym) {
		super(x, y, height, width, life, dirX, dirY, xm, ym);
		this.parent = parent;
	}

	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}

	@Override
	public GOType getType() {
		return GOType.ENEMY;
	}

	public void update(float delta) {
		super.update(delta);
		fireTimer += delta;
		if (fireTimer >= fireDelay) {
			parent.getObjects().add(new Bullet(this, 5, -1, 0, Bullet.SPEEDBASE, getX() - 3));
			fireTimer = 0;
		}
	}
}
