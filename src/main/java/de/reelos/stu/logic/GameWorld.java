package de.reelos.stu.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.reelos.stu.gui.FieldPanel;
import de.reelos.stu.logic.Boost.BoostType;
import de.reelos.stu.logic.GameObject.GOType;

public class GameWorld {
	public static final int WORLD_X = 600;
	public static final int WORLD_Y = 450;

	protected float spawnTime = 0f, spawnPause = 1.0f;
	protected FieldPanel parent;
	private int score = 0, boostChance = 1;
	private Random posRandom = new Random(), boostRandom = new Random();
	private List<GameObject> objects = new ArrayList<>();
	private int oc = 0;
	
	
	public GameWorld(FieldPanel parent) {
		this.parent = parent;
	}

	public void update(float delta) {
		spawnTime += delta;
		if (spawnTime >= spawnPause) {
			spawnWave();
			if (boostRandom.nextInt(10) <= boostChance) {
				Boost boost = spawnBoost();
				if (boost != null) {
					objects.add(boost);
				}
			}
		}

		for (int i = 0; i < objects.size(); i++) {
			GameObject go = objects.get(i);
			if (go == null) {
				continue;
			}
			go.update(delta);
			for (int j = 0; j < objects.size(); j++) {
				GameObject c = objects.get(j);
				if (c != go) {
					go.hit(c);
				}
			}
			if (go.isRemovable()) {
				objects.remove(go);
				if (go.lastHit() != null)
					if (go.getType() == GOType.METROID
							&& (go.lastHit().getType() == GOType.BULLET || go.lastHit().getType() == GOType.PLAYER)
							&& go.getLife() <= 0) {
						score++;
					}
				if (go.getType() == GOType.METROID) {
					oc--;
				}
			}
		}
	}

	public int getScore() {
		return score;
	}

	public List<GameObject> getObjects() {
		return objects;
	}
	
	protected void spawnWave() {
		GameObject tmp = spawnObject();
		if (tmp != null && oc < 20) {
			objects.add(tmp);
			oc++;
		}
		resetTime();
	}

	private GameObject spawnObject() {
		GameObject ret = null;
		if (objects.size() < 20) {
			int width = 20;
			int height = 20;
			int x = posRandom.nextInt(WORLD_X);
			int y = posRandom.nextInt(WORLD_Y);
			int life = posRandom.nextInt(height + width) + 1;
			int dirX = posRandom.nextInt(3) - 2;
			int dirY = posRandom.nextInt(3) - 2;
			float xm = posRandom.nextFloat() - 0.8f;
			float ym = posRandom.nextFloat() - 0.8f;
			GameObject temp = new GameObject(x, y, height, width, life, dirX, dirY, xm, ym);
			for (GameObject go : objects) {
				if (temp.checkPos(go)) {
					ret = spawnObject();
					return ret;
				}
			}
			ret = temp;
		}
		return ret;
	}
	
	public void resetTime() {
		spawnTime = 0f;
	}

	public Boost boost() {
		return spawnBoost();
	}

	private Boost spawnBoost() {
		Boost ret = null;
		int x = posRandom.nextInt(WORLD_X);
		int y = posRandom.nextInt(WORLD_Y);
		ret = new Boost(x, y, 10, 10, BoostType.values()[posRandom.nextInt(BoostType.values().length)]);
		return ret;
	}
}
