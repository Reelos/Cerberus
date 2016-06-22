package de.reelos.stu.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.reelos.stu.logic.GameObject.GOType;

public class GameWorld {
	public static final int WORLD_X = 600;
	public static final int WORLD_Y = 450;
	
	private int score = 0;
	private float spawnTime = 0f;
	private float spawnPause = 1.0f;
	private Random posRandom = new Random();
	private List<GameObject> objects = new ArrayList<>();
	private int oc = 0;
	
	public void update(float delta){
		spawnTime += delta;
		if(spawnTime >= spawnPause) {
			GameObject tmp = spawnObject();
			if(tmp != null && oc < 20) {
				objects.add(tmp);
				oc++;
			}
		}
		
		for(int i=0; i< objects.size();i++) {
			GameObject go = objects.get(i);
			go.update(delta);
			for (int j = 0; j < objects.size(); j++) {
				GameObject c = objects.get(j);
				if(c != go) {
					go.hit(c);
				}
			}
			if(go.isRemovable()) {
				objects.remove(go);
				if(go.lastHit() != null)
					if(go.getType() == GOType.METROID && (go.lastHit().getType() == GOType.BULLET || go.lastHit().getType() == GOType.PLAYER) && go.getLife() <= 0) {
						score++;
					}
				oc--;
			}
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public List<GameObject> getObjects() {
		return objects;
	}
	
	private GameObject spawnObject() {
		GameObject ret = null;
		if(objects.size() < 20) {
			int width = 20;
			int height = 20;
			int x = posRandom.nextInt(WORLD_X);
			int y = posRandom.nextInt(WORLD_Y);
			int life = posRandom.nextInt(height+width)+1;
			int xm = posRandom.nextInt(10) - 5;
			int ym = posRandom.nextInt(10) - 5;
			GameObject temp = new GameObject(x, y, height, width, life, xm, ym);
			for(GameObject go : objects) {
				if (temp.checkPos(go)) {
					ret = spawnObject();
				}
			}
			ret = temp;
		}
		spawnTime = 0f;
		return ret;
	}
}