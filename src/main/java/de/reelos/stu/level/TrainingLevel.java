package de.reelos.stu.level;

import java.util.Arrays;
import java.util.List;

import de.reelos.stu.logic.Enemy;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Wave;

public class TrainingLevel extends GameWorld {
	private List<Wave> waves = Arrays.asList(
			new Wave(new Enemy(this, GameWorld.WORLD_X - 1, GameWorld.WORLD_Y / 2 - 10, 20, 50)),
			new Wave(new Enemy(this, GameWorld.WORLD_X - 1, 20, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 60, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 100, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 140, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 180, 20, 20)),
			new Wave(new Enemy(this, GameWorld.WORLD_X - 1, 220, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 260, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 300, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 340, 20, 20),
					new Enemy(this, GameWorld.WORLD_X - 1, 380, 20, 20)));

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
