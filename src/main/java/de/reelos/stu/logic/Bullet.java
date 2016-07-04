package de.reelos.stu.logic;

import java.awt.Color;

public class Bullet extends GameObject {

	public static final float SPEEDBASE = 0.1f;
	private int power = 0;
	private GameObject parent;

	public Bullet(GameObject parent, int power) {
		this(parent, power, 1, 0);
	}

	public Bullet(GameObject parent, int power, int dirX, int dirY) {
		this(parent, power, dirX, dirY, 0.1f);
	}

	public Bullet(GameObject parent, int power, int dirX, int dirY, float ym) {
		this(parent, power, dirX, dirY, ym, parent.getX() + parent.getWidth() + 3);
	}

	public Bullet(GameObject parent, int power, int dirX, int dirY, float ym, int xd) {
		super(xd, parent.getY() + parent.getHeight() / 2 - 1, 2, 2, 1, dirX, dirY, 0.01f, ym);
		super.speed = 0.01f;
		this.power = power;
		this.parent = parent;
	}

	@Override
	public void hit(GameObject obj) {
		if (checkPos(obj) && obj != parent) {
			if (obj.getType() == GOType.BULLET) {
				if (((Bullet) obj).parent == parent) {
					return;
				} else {
					obj.isRemovable = true;
					isRemovable = true;
				}

			}
			if (obj.getType() == GOType.BOOST)
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
