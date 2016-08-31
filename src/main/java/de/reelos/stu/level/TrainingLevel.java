package de.reelos.stu.level;

import java.util.Arrays;
import java.util.List;

import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Wave;
import de.reelos.stu.logic.objects.enemies.Carrier;
import de.reelos.stu.logic.objects.enemies.UFO;


public class TrainingLevel extends GameWorld {
	private List<Wave> waves = Arrays.asList(
			new Wave(new Carrier(this, GameWorld.WORLD_X - 1, GameWorld.WORLD_Y / 2 - 10)),
			new Wave(new UFO(this, GameWorld.WORLD_X - 1, 20),
					new UFO(this, GameWorld.WORLD_X - 1, 60),
					new UFO(this, GameWorld.WORLD_X - 1, 100),
					new UFO(this, GameWorld.WORLD_X - 1, 140),
					new UFO(this, GameWorld.WORLD_X - 1, 180)),
			new Wave(new UFO(this, GameWorld.WORLD_X - 1, 220),
					new UFO(this, GameWorld.WORLD_X - 1, 260),
					new UFO(this, GameWorld.WORLD_X - 1, 300),
					new UFO(this, GameWorld.WORLD_X - 1, 340),
					new UFO(this, GameWorld.WORLD_X - 1, 380)));

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
