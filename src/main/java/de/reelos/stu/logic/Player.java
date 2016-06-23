package de.reelos.stu.logic;

import java.awt.Color;

public class Player extends GameObject {

	private int firePower = 10;
	private float shootSpeed = 0.01f;
	private float shootTimeOut = 0.5f;
	private boolean fireState = false;
	private float lastShoot;
	private GameWorld parent;
	
	public Player(GameWorld parent) {
		super(GameWorld.WORLD_X/5-10, GameWorld.WORLD_Y/2-20, 20, 40, 120, 0, 0, 0, 0);
		this.parent = parent;
	}
	
	@Override
	public Color getColor() {
		return Color.BLUE.brighter();
	}
	
	@Override
	public GOType getType() {
		return GOType.PLAYER;
	}
	
	public Bullet fire() {
		Bullet ret = new Bullet(getX()+getWidth()+2,getY()+getHeight()/2-2,firePower);
		ret.setSpeed(shootSpeed);
		return ret;
	}
	
	public int getXSpeed() {
		return 0;
	}
	
	public int getYSpeed() {
		return 0;
	}

	public float getShootTimeOut() {
		return shootTimeOut;
	}

	public void setShootTimeOut(float shootTimeOut) {
		this.shootTimeOut = shootTimeOut;
	}
	
	public void setFire(boolean state) {
		fireState = state;
	}
	
	public void update(float delta) {
		super.update(delta);
		lastShoot += delta;
		if(fireState) {
			if (lastShoot >= shootTimeOut) {
				parent.getObjects().add(fire());
				lastShoot = 0;
			}
		}
	}

}
