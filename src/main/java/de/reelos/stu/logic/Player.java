package de.reelos.stu.logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

	private int firePower = 10;
	private float shootSpeed = 0.01f;
	private float shootTimeOut = 0.5f;
	private boolean fireState = false;
	private float lastShoot;
	private GameWorld parent;

	private List<Boost> boostList = new ArrayList<>();

	public Player(GameWorld parent) {
		super(GameWorld.WORLD_X / 5 - 10, GameWorld.WORLD_Y / 2 - 20, 20, 40, 120, 0, 0, 0.01f, 0.01f);
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
		Bullet ret = new Bullet(getX() + getWidth() + 3, getY() + getHeight() / 2 - 1, firePower);
		ret.setSpeed(shootSpeed);
		return ret;
	}

	public int boostSpeed() {
		// TODO Calc SpeedBoost
		return 1;
	}

	public float getXSpeed() {
		return xm / boostSpeed();
	}

	public float getYSpeed() {
		return ym / boostSpeed();
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

	public void addBoost(Boost boost) {
		boostList.add(boost);
	}

	public void update(float delta) {
		super.update(delta);
		lastShoot += delta;
		for (int i = 0; i < boostList.size(); i++) {
			if (boostList.get(i).inActive()) {
				boostList.remove(i);
			}
			boostList.get(i).update(delta);
		}
		if (fireState) {
			if (lastShoot >= shootTimeOut) {
				parent.getObjects().add(fire());
				lastShoot = 0;
			}
		}
	}

}
