package de.reelos.stu.logic.objects.enemies;

import java.awt.Color;
import java.awt.Graphics;

import de.reelos.stu.logic.Bullet;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Vector2D;
import de.reelos.stu.logic.objects.GameObject;

public abstract class Enemy extends GameObject {

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
	
	public void drawHealth(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y + height + 5, width, 5);
		g.setColor(Color.GREEN);
		g.fillRect(x, y + height + 5, (int)(width * ((life * 1.0d) / maxlife)), 5);
		g.drawRect(x, y + height + 5, width, 5);
	}

	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}

	@Override
	public GOType getType() {
		return GOType.ENEMY;
	}
	
	public void strategie(float delta) {
		fireTimer += delta;
		if (fireTimer >= fireDelay) {
			parent.getObjects().add(new Bullet(this, 5, 1, -0.99f, 0, x - 5));
			fireTimer = 0;
		}
	}

	public void update(float delta) {
		super.update(delta);
		strategie(delta);
	}
	
	public void hit(GameObject obj) {
		if(obj.getType() != GOType.BOOST) {
			super.hit(obj);
		}
	}
}
