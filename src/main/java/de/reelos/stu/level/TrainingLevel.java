package de.reelos.stu.level;

import java.util.Arrays;
import java.util.List;

import de.reelos.stu.gui.FieldPanel;
import de.reelos.stu.logic.Enemy;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Wave;

public class TrainingLevel extends GameWorld {
	private List<Wave> waves = Arrays.asList(new Wave(new Enemy(this, GameWorld.WORLD_X - 40, 20, 20, 20),
			new Enemy(this, GameWorld.WORLD_X - 40, 60, 20, 20), new Enemy(this, GameWorld.WORLD_X - 40, 100, 20, 20),
			new Enemy(this, GameWorld.WORLD_X - 40, 140, 20, 20),
			new Enemy(this, GameWorld.WORLD_X - 40, 180, 20, 20)));

	@Override
	public void spawnWave() {
		if (waves.stream().anyMatch(p -> !p.isDefeated())) {
			waves.stream().filter(p -> !p.isDefeated()).findFirst().orElse(null).spawn(this);
		} else {
			levelClear = true;
		}
		resetTime();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		waves.forEach(w -> w.update(delta));
	}
}
