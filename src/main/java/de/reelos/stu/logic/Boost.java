package de.reelos.stu.logic;

import java.awt.Color;

import de.reelos.stu.logic.objects.GameObject;
import de.reelos.stu.logic.objects.player.Player;


public class Boost extends GameObject {

	private float tio = 0f, ta = 0f, tiom = 5.0f, tam = 5.0f;
	private boolean overState = false;
	private boolean inActive = false;

	public enum BoostType {
		ACCELARATION(Color.GREEN), FIRERATE(Color.CYAN), SHOOTDAMAGE(Color.RED), SHOOTACCELARATION(Color.ORANGE), EXTRASHOOT(Color.YELLOW);
		
		private Color color = Color.WHITE;
		
		private BoostType(Color color) {
			this.color = color;
		}
		
		public Color color() {
			return color;
		}
	}

	private BoostType type = null;

	public Boost(int x, int y, int height, int width, BoostType type) {
		super(x, y, height, width, 1, new Vector2D(0,0,0));
		this.type = type;
	}

	@Override
	public Color getColor() {
		return type.color();
	}

	@Override
	public void hit(GameObject obj) {
		if(checkPos(obj)) {
			if (obj.getType() == GOType.PLAYER) {
				((Player) obj).addBoost(this);
				isRemovable = true;
				overState = true;
			}
		}
	}

	public boolean inActive() {
		return inActive;
	}
	
	public BoostType getBoostType() {
		return type;
	}
	
	@Override
	public GOType getType() {
		return GOType.BOOST;
	}
	
	@Override
	public void change(int change){
		// IMMORTAL OBJECT
	}

	@Override
	public void update(float delta) {
		if (overState) {
			ta += delta;
		} else {
			tio += delta;
		}
		if (tio >= tiom) {
			isRemovable = true;
			tio = 0;
		}
		if (ta >= tam) {
			inActive = true;
			ta = 0;
		}
	}
}
