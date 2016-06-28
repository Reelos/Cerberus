package de.reelos.stu.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.reelos.stu.logic.GameObject.GOType;

public class Wave {

	private List<GameObject> wave = new ArrayList<>();
	private boolean defeated = false; 
	
	public Wave(GameObject... wave) {
		this.wave.addAll(Arrays.asList(wave));
	}
	
	public void addToWave(GameObject member) {
		wave.add(member);
	}
	
	public void spawn(GameWorld world) {
		List<GameObject> list = world.getObjects();
		if(list.stream().filter(g -> g.getType() == GOType.ENEMY).count() == 0) {
			list.addAll(wave);
		}
	}
	
	public boolean isDefeated() {
		return defeated;
	}
	
	public void update(float delta) {
		if(wave.stream().filter(p -> !p.isRemovable()).count() == 0) {
			defeated = true;
		}
	}
}
