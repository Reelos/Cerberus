package de.reelos.stu.logic.objects;

import java.awt.Color;

import de.reelos.stu.logic.Vector2D;

public class Bullet extends GameObject {

	public static final float SPEEDBASE = 0.1f;
	private int power = 0;
	private GameObject parent;

	public Bullet(GameObject parent, int power) {
		this(parent, power, 0.1f);
	}

	public Bullet(GameObject parent, int power, float xm) {
		this(parent, power, xm, 0.1f);
	}

	public Bullet(GameObject parent, int power, float xm, float ym) {
		this(parent, power, 1f, xm, ym);
	}

	public Bullet(GameObject parent, int power, float force, float xm, float ym) {
		this(parent, power, force, xm, ym, parent.getX() + parent.getWidth() + 3);
	}

	public Bullet(GameObject parent, int power, float speed, float xm, float ym, int xd) {
		super(xd, parent.getY() + parent.getHeight() / 2 - 1, 2, 2, 1, new Vector2D(xm, ym, speed));
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
					obj.isRemovable(true);
					isRemovable = true;
				}

			}
			if (obj.getType() == GOType.BOOST)
				return;
			obj.change(-power);
			isRemovable = true;
		}
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
