package de.reelos.stu.logic;

import java.awt.Color;

public class Bullet extends GameObject {

	private int power = 0;
	private GameObject parent;

	public Bullet(GameObject parent, int power) {
		this(parent, power, 1, 0);
	}

	public Bullet(GameObject parent, int power, int dirX, int dirY) {
		this(parent, power, dirX, dirY, 0.1f);
	}

	public Bullet(GameObject parent, int power, int dirX, int dirY, float ym) {
		super(parent.getX() + parent.getWidth() + 3, parent.getY() + parent.getHeight() / 2 - 1, 2, 2, 1, dirX, dirY,
				0.001f, ym);
		super.speed = 0.01f;
		this.power = power;
		this.parent = parent;
	}

	@Override
	public void hit(GameObject obj) {
		if (checkPos(obj) && obj != parent) {
			if (obj.getType() == GOType.BULLET)
				if (((Bullet) obj).parent == parent)
					return;
			if(obj.getType() == GOType.BOOST)
				return;
			obj.change(-power);
			isRemovable = true;
		}
	}

	public void setSpeed(float speed) {
		super.xm = speed;
	}

	public float getSpeed() {
		return super.speed;
	}

	@Override
	public Color getColor() {
		return Color.RED;
	}

	@Override
	public void change(int change) {
		// IMMORTAL OBJECT
	}

	@Override
	public int getLife() {
		return power;
	}

	@Override
	public GOType getType() {
		return GOType.BULLET;
	}
}
