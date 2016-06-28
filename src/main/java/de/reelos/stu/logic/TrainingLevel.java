package de.reelos.stu.logic;

import java.util.Arrays;
import java.util.List;

import de.reelos.stu.gui.FieldPanel;

public class TrainingLevel extends GameWorld {
	private List<Wave> waves = Arrays.asList(
			new Wave(new Enemy(GameWorld.WORLD_X - 40, 20, 20, 20),
					new Enemy(GameWorld.WORLD_X - 40, 60, 20, 20),
					new Enemy(GameWorld.WORLD_X - 40, 100, 20, 20),
					new Enemy(GameWorld.WORLD_X - 40, 140, 20, 20),
					new Enemy(GameWorld.WORLD_X - 40, 180, 20, 20))
			);
	
	public TrainingLevel(FieldPanel parent) {
		super(parent);
	}
	
	@Override
	public void spawnWave() {
		if(waves.stream().anyMatch(p -> !p.isDefeated())) {
			waves.stream().filter(p -> !p.isDefeated()).findFirst().orElse(null).spawn(this);
		}else{
			parent.stop();
		}
		resetTime();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		waves.forEach(w -> w.update(delta));
	}
}
