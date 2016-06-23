package de.reelos.stu.logic;

import java.awt.Color;

public class Bullet extends GameObject {
	
	private int power = 0;

	public Bullet(int x, int y, int power) {
		super(x, y, 4, 10, 1, 3, 0);
		this.power = power;
		super.speed = 0.01f;
	}
	
	@Override
	public void hit(GameObject obj) {
		if(checkPos(obj)) {
			obj.change(-power);
			isRemovable = true;
		}
	}
	
	public void setSpeed(float speed) {
		super.speed = speed;
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
		//Not Used
	}
	
	@Override
	public int getLife() {
		return 0;
	}
	
	@Override
	public GOType getType() {
		return GOType.BULLET;
	}
}
