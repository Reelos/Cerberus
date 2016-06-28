package de.reelos.stu.logic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import de.reelos.stu.logic.Boost.BoostType;

public class Player extends GameObject {

	private int firePower = 20;
	private int shoots = 5;
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
	
	public void setParent(GameWorld world) {
		
	}

	public void fire() {
		Bullet ret = null;
		switch (shoots) {
		case 5:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), 1, -1, 0.05f);
			ret.setSpeed(shootSpeed);
			parent.getObjects().add(ret);
		case 4:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), 1, 1, 0.05f);
			ret.setSpeed(shootSpeed);
			parent.getObjects().add(ret);
		case 3:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), 1, -1);
			ret.setSpeed(shootSpeed);
			parent.getObjects().add(ret);
		case 2:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), 1, 1);
			ret.setSpeed(shootSpeed);
			parent.getObjects().add(ret);
		case 1:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())));
			ret.setSpeed(shootSpeed);
			parent.getObjects().add(ret);
		}
	}

	public float getXSpeed() {
		return xm / (1 + getAccBoost());
	}

	public float getYSpeed() {
		return ym / (1 + getAccBoost());
	}

	public float getShootTimeOut() {
		return shootTimeOut / (1 + getFireRateBoost());
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
			Boost boost = boostList.get(i);
			if (boost.inActive()) {
				boostList.remove(boost);
			}
			boost.update(delta);
		}
		if (fireState) {
			if (lastShoot >= getShootTimeOut()) {
				fire();
				lastShoot = 0;
			}
		}
	}

	public long getAccBoost() {
		return boostList.stream().filter(b -> b.getBoostType() == BoostType.ACCELARATION).count();
	}

	public long getDmgBoost() {
		return boostList.stream().filter(b -> b.getBoostType() == BoostType.SHOOTDAMAGE).count();
	}

	public long getFireRateBoost() {
		return boostList.stream().filter(b -> b.getBoostType() == BoostType.FIRERATE).count();
	}

	public long getShootAccBoost() {
		return boostList.stream().filter(b -> b.getBoostType() == BoostType.SHOOTACCELARATION).count();
	}

	@Override
	public void move(float delta) {
		accX += delta;
		accY += delta;
		if (accX >= getXSpeed()) {
			x += dirX;
			accX = 0;
		}
		if (accY >= getYSpeed()) {
			y += dirY;
			accY = 0;
		}
	}

	@Override
	public void hit(GameObject obj) {

	}

}
