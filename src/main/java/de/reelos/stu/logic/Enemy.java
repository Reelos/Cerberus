package de.reelos.stu.logic;

import java.awt.Color;

public class Enemy extends GameObject {

	private float fireDelay = 3f;
	private float fireTimer = 0f;
	private GameWorld parent;

	public Enemy(GameWorld parent, int x, int y, int height, int width) {
		this(parent, x, y, height, width, (height * width) / 2, -0.89f, 0f);
	}

	public Enemy(GameWorld parent, int x, int y, int height, int width, int life, float xm, float ym) {
		super(x, y, height, width, life, new Vector2D(-0.1f, 0, 1f));
		super.initVelocity(xm, ym, 0.01f, 20);
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
			parent.getObjects().add(new Bullet(this, 5, 1, -0.99f, 0, x - 5));
			fireTimer = 0;
		}
	}
	
	public void hit(GameObject obj) {
		if(obj.getType() != GOType.BOOST) {
			super.hit(obj);
		}
	}
}
