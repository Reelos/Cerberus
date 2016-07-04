package de.reelos.stu.logic;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.reelos.stu.logic.Boost.BoostType;

public class Player extends GameObject {

	private class PlayerControl extends KeyAdapter {
		private Player player;

		public PlayerControl(Player player) {
			this.player = player;
		}

		@Override
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setXMotion(-1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setXMotion(1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.setYMotion(-1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setYMotion(1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(true);
			}
		}

		@Override
		public void keyReleased(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setXMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setXMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.setYMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setYMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(false);
			}
		}
	}

	public final KeyAdapter control = new PlayerControl(this);
	private int firePower = 20, shoots = 5, shield, maxShield;
	private float shootSpeed = 0.01f, shootTimeOut = 0.5f, lastShoot, lastHit, rechargeDelay = 1f;
	private boolean fireState = false;
	private GameWorld parent;

	private List<Boost> boostList = new ArrayList<>();

	public Player(GameWorld parent, int shield) {
		super(GameWorld.WORLD_X / 5 - 10, GameWorld.WORLD_Y / 2 - 20, 20, 40, 120, 0, 0, 0.01f, 0.01f);
		this.parent = parent;
		this.maxShield = shield;
		this.shield = shield;
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
		this.parent = world;
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
	
	public void recharge(float delta) {
		if(lastHit >= rechargeDelay) {
			
		}
	}

	@Override
	public void hit(GameObject obj) {
		lastHit = 0f;
	}

}
