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
				player.left = true;
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = true;
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.up = true;
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = true;
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(true);
			}
		}

		@Override
		public void keyReleased(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				player.left = false;
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.right = false;
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.up = false;
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.down = false;
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(false);
			}
		}
	}

	public final KeyAdapter control = new PlayerControl(this);
	private int firePower = 20, shoots = 5, shield;
	private float shootSpeed = 0.99f, shootTimeOut = 0.5f, lastShoot, lastHit, rechargeDelay = 1f;
	private boolean fireState = false, up = false, down = false, left = false, right = false;
	private GameWorld parent;

	private List<Boost> boostList = new ArrayList<>();

	public Player(GameWorld parent, int shield) {
		super(GameWorld.WORLD_X / 5 - 10, GameWorld.WORLD_Y / 2 - 20, 20, 40, 120, new Vector2D(0l, 0l, 1l));
		super.objTick = 1f;
		super.ttm = 0.01f;
		initVelocity(0.9f, 0f, 0.001f, objTick-objTick/1000);
		this.parent = parent;
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
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), shootSpeed, -0.9f);
			parent.getObjects().add(ret);
		case 4:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), shootSpeed, 0.9f);
			parent.getObjects().add(ret);
		case 3:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), shootSpeed, -0.95f);
			parent.getObjects().add(ret);
		case 2:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), shootSpeed, 0.95f);
			parent.getObjects().add(ret);
		case 1:
			ret = new Bullet(this, (int) (firePower * (1 + getDmgBoost())), shootSpeed, 0f);
			parent.getObjects().add(ret);
		}
	}

	public float getXSpeed() {
		return 100 / (velocity.getXVelocity() * (1 + getAccBoost()));
	}

	public float getYSpeed() {
		return 100 / (velocity.getYVelocity() * (1 + getAccBoost()));
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
	
	@Override
	public void move(float delta) {
		accX += delta;
		accY += delta;
		if (left) {
			getVelocity().addToX(-0.03f);
		}else if (right) {
			getVelocity().addToX(0.03f);
		}
		if (up) {
			getVelocity().addToY(-0.03f);
		} else if (down) {
			getVelocity().addToY(0.03f);
		}
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

	public long getShield() {
		return shield;
	}

	public void recharge(float delta) {
		if (lastHit >= rechargeDelay) {

		}
	}

	@Override
	public void hit(GameObject obj) {
		lastHit = 0f;
	}

}
