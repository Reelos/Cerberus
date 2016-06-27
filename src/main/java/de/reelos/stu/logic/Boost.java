package de.reelos.stu.logic;

import java.awt.Color;

public class Boost extends GameObject {

	private float tio = 0f, ta = 0f, tiom = 1f, tam = 1f;
	private boolean overState = false;
	private boolean inActive = false;

	public enum BoostType {
		ACCELARATION(Color.GREEN), SHOOTSPEED(Color.CYAN), SHOOTDAMAGE(Color.RED);
		
		private Color color = Color.WHITE;
		
		private BoostType(Color color) {
			this.color = color;
		}
		
		public Color color() {
			return color;
		}
	}

	private Color color = Color.WHITE;
	private BoostType type = null;

	public Boost(int x, int y, int height, int width, BoostType type) {
		super(x, y, height, width, 1, 0, 0, 0, 0);
		this.type = type;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void hit(GameObject obj) {
		if (obj.getType() == GOType.PLAYER) {
			((Player) obj).addBoost(this);
			isRemovable = true;
		}
	}

	public boolean inActive() {
		return inActive;
	}
	
	public BoostType getBoostType() {
		return type;
	}

	@Override
	public void update(float delta) {
		if (overState) {
			ta += delta;
		} else {
			tio += delta;
		}
		if (tio >= tiom)
			isRemovable = true;
		if (ta >= tam)
			inActive = true;
	}
}