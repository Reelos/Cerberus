package de.reelos.stu.logic;

import java.awt.Color;

public class GameObject {
	public enum GOType {
		METROID,BULLET,PLAYER;
	}
	
	protected float speed = 0.1f;
	private int height, width, x, y, life, maxlife, xm, ym;
	protected boolean isRemovable = false;
	private float tick = 0f;
	private GameObject lastHitted = null;

	public GameObject(int x, int y, int height, int width, int life, int xm, int ym) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.life = life;
		this.maxlife = life;
		this.xm = xm;
		this.ym = ym;
	}

	public void hit(GameObject obj) {
		if (checkPos(obj)) {
			lastHitted = obj;
			if(obj.getType() != GOType.BULLET) {
				int ouch = (int)(life * 0.1);
				life -= obj.getLife() * 0.1;
				obj.change(-ouch);
				if (life <= 0) {
					isRemovable = true;
				}
			}
		}
	}

	public int getLife() {
		return life;
	}

	public int getMaxLife() {
		return maxlife;
	}
	
	public GameObject lastHit() {
		return lastHitted;
	}

	private Color colorAddition(Color base, Color toAdd, int power) {
		int r = base.getRed() + toAdd.getRed() * (power / 100);
		int g = base.getGreen() + toAdd.getGreen() * (power / 100);
		int b = base.getBlue() + toAdd.getBlue() * (power / 100);

		while (r > 255) {
			r -= 255;
		}
		while (g > 255) {
			g -= 255;
		}
		while (b > 255) {
			b -= 255;
		}

		return new Color(r, g, b);
	}

	public Color getColor() {
		Color ret = Color.GRAY;
		double pro = life * 100 / maxlife;
		if (pro >= 90) {
			ret = colorAddition(ret, Color.GREEN, 50);
		} else if (pro >= 50) {
			ret = colorAddition(ret, Color.YELLOW, 50);
		} else if (pro >= 25) {
			ret = colorAddition(ret, Color.ORANGE, 50);
		} else if (pro < 25) {
			ret = colorAddition(ret, Color.RED, 50);
		}
		return ret;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void motion(int x, int y) {
		xm = x;
		ym = y;
	}

	public int getXMotion() {
		return xm;
	}
	
	public void setXMotion(int xm) {
		this.xm = xm;
	}

	public int getYMotion() {
		return ym;
	}
	
	public void setYMotion(int ym) {
		this.ym = ym;
	}

	public void bounds() {
		if (x > GameWorld.WORLD_X || x + width < 0 || y > GameWorld.WORLD_Y || y + height < 0 || life <= 0) {
			isRemovable = true;
		}
	}

	public boolean checkPos(GameObject obj) {
		boolean ret = false;
		if(obj != null)
		if (x + width >= obj.getX() && x <= obj.getX() + obj.getWidth())
			if (y + height >= obj.getY() && y <= obj.getY() +  obj.getHeight())
				ret = true;
		return ret;
	}
	
	public void move() {
		x += xm;
		y += ym;
	}

	public void update(float delta) {
		tick += delta;
		if (tick >= speed) {
			move();
			bounds();
			tick = 0f;
		}
	}

	public boolean isRemovable() {
		return isRemovable;
	}

	public void change(int val) {
		life += val;
		if(life <= 0) {
			isRemovable = true;
		}
	}
	
	public GOType getType() {
		return GOType.METROID;
	}
}
