package de.reelos.stu.logic.objects;

import java.awt.Color;
import java.awt.Graphics;

import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Vector2D;

public abstract class GameObject {
	public enum GOType {
		METROID, BULLET, PLAYER, BOOST, ENEMY;
	}

	protected float accX = 0f, accY = 0f, ttm = 1f, objTick = 1f;
	protected Vector2D velocity = new Vector2D(0,0,0);
	protected int height, width, x, y, life, maxlife;
	protected boolean isRemovable = false;
	private float tick = 0f;
	private GameObject lastHitted = null;

	public GameObject(int x, int y, int height, int width, int life, Vector2D velocity) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
		this.life = life;
		this.maxlife = life;
		this.velocity = velocity;
	}
	
	public void drawMe(Graphics g) {
		g.setColor(getColor());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
	}
	
	public void initVelocity(float x, float y, float depletion, float limit) {
		velocity.initVelocity(x, y, depletion,limit);
	}

	public void hit(GameObject obj) {
		if (checkPos(obj)) {
			lastHitted = obj;
			int ouch = (int) (life * 0.1);
			life -= obj.getLife() * 0.1;
			obj.change(-ouch);
			if (life <= 0) {
				isRemovable = true;
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
	
	public float getObjectTick() {
		return objTick;
	}

	public void bounds() {
		if (x > GameWorld.WORLD_X || x + width < 0 || y > GameWorld.WORLD_Y || y + height < 0 || life <= 0) {
			isRemovable = true;
		}
	}

	public boolean checkPos(GameObject obj) {
		boolean ret = false;
		if (obj != null)
			if (x + width >= obj.getX() && x <= obj.getX() + obj.getWidth())
				if (y + height >= obj.getY() && y <= obj.getY() + obj.getHeight())
					ret = true;
		return ret;
	}

	public void move(float delta) {
		accX += delta;
		accY += delta;
		if (accX >= objTick - Math.abs(velocity.getXVelocity())) {
			x += (velocity.getXVelocity()>0?1:(velocity.getXVelocity()<0?-1:0));
			accX = 0;
		}
		if (accY >= objTick - Math.abs(velocity.getYVelocity())) {
			y += (velocity.getYVelocity()>0?1:(velocity.getYVelocity()<0?-1:0));
			accY = 0;
		}
	}

	public void update(float delta) {
		tick += delta;
		move(delta);
		if (tick >= ttm) {
			velocity.update(delta);
			bounds();
			tick = 0f;
		}
	}

	public boolean isRemovable() {
		return isRemovable;
	}
	
	public void isRemovable(boolean state) {
		isRemovable = state;
	}

	public void change(int val) {
		life += val;
		if (life <= 0) {
			isRemovable = true;
		}
	}

	public GOType getType() {
		return GOType.METROID;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
}
