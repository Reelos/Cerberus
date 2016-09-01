package de.reelos.stu.logic.objects.player;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Vector2D;
import de.reelos.stu.logic.objects.Boost;
import de.reelos.stu.logic.objects.Bullet;
import de.reelos.stu.logic.objects.GameObject;
import de.reelos.stu.logic.objects.Boost.BoostType;

public abstract class Player extends GameObject {

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
	private int firePower = 20, shield = 0, shieldMax = 0;
	private float shootSpeed = 0.99f, shootTimeOut = 0.5f, lastShoot, lastHit, rechargeDelay = 1f;
	private boolean fireState = false, up = false, down = false, left = false, right = false;
	private GameWorld parent;

	private List<Boost> boostList = new ArrayList<>();

	public Player(GameWorld parent, int width, int height, int life, int shield, float force, int firepower) {
		super(-25, GameWorld.WORLD_Y / 2 - height / 2, height, width, life, new Vector2D(0l, 0l, force));
		super.objTick = force;
		super.ttm = 0.01f;
		initVelocity(0.9f, 0f, force / 1000, objTick - objTick / 1000);
		this.parent = parent;
		this.shield = shield;
		this.shieldMax = shield;
		this.firePower = firepower;
	}

	@Override
	public Color getColor() {
		return Color.BLUE.brighter();
	}

	@Override
	public GOType getType() {
		return GOType.PLAYER;
	}

	public void reset() {
		x = -25;
		y = GameWorld.WORLD_Y / 2 - 10;
		getVelocity().reinitVelocity();
		shield = shieldMax;
		life = maxlife;
	}

	public void setParent(GameWorld world) {
		this.parent = world;
	}

	public void fire() {
		Bullet ret = null;
		switch ((int) getShoots()) {
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
		float ret = (velocity.getXVelocity() * (1 + getAccBoost()));
		return ret >= objTick ? objTick - objTick / 1000 : ret;
	}

	public float getYSpeed() {
		float ret = (velocity.getYVelocity() * (1 + getAccBoost()));
		return ret >= objTick ? objTick - objTick / 1000 : ret;
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
			getVelocity().addToX(-0.008f);
		} else if (right) {
			getVelocity().addToX(0.008f);
		}
		if (up) {
			getVelocity().addToY(-0.008f);
		} else if (down) {
			getVelocity().addToY(0.008f);
		}
		if (accX >= objTick - Math.abs(getXSpeed())) {
			x += (velocity.getXVelocity() > 0 ? 1 : (velocity.getXVelocity() < 0 ? -1 : 0));
			accX = 0;
		}
		if (accY >= objTick - Math.abs(getYSpeed())) {
			y += (velocity.getYVelocity() > 0 ? 1 : (velocity.getYVelocity() < 0 ? -1 : 0));
			accY = 0;
		}
	}

	@Override
	public void bounds() {
		if (x > GameWorld.WORLD_X - width) {
			x = GameWorld.WORLD_X - width;
		}
		if (x < 0) {
			x = 0;
		}
		if (y > GameWorld.WORLD_Y - height - 45) {
			y = GameWorld.WORLD_Y - height - 45;
		}
		if (y < 0) {
			y = 0;
		}
		if (life <= 0) {
			isRemovable = true;
		}
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		lastShoot += delta;
		recharge(delta);
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

	public long getShoots() {
		return 1 + boostList.stream().filter(b -> b.getBoostType() == BoostType.EXTRASHOOT).count();
	}

	public long getShield() {
		return shield;
	}

	public long getMaxShield() {
		return shieldMax;
	}

	public void recharge(float delta) {
		lastHit += delta;
		if (lastHit >= rechargeDelay) {
			if (shield < shieldMax) {
				shield++;
				lastHit /= 1.5f;
			}
		}
	}

	@Override
	public void hit(GameObject obj) {

	}

	@Override
	public void change(int dmg) {
		int swap = dmg;
		if (shield > 0) {
			swap += shield;
			shield = swap < 0 ? 0 : shield + dmg;
		}
		if (swap < 0) {
			life += swap;
		}
		lastHit = 0f;
	}

}
