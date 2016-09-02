package de.reelos.stu.logic;

public class Vector2D {
	private float baseX, baseY, force, modX, modY, depletion, limit, xChange, yChange,startX,startY;

	public Vector2D(float baseX, float baseY, float force) {
		this.baseX = baseX;
		this.baseY = baseY;
		this.force = force;
	}

	public void initVelocity(float modX, float modY, float depletion, float limit) {
		this.modX = modX;
		this.modY = modY;
		this.startX = modX;
		this.startY = modY;
		this.depletion = depletion;
		this.limit = limit;
	}
	
	public void reinitVelocity() {
		initVelocity(startX,startY,depletion,limit);
	}

	public float getXVelocity() {
		float ret = baseX * force;
		ret += modX;
		return ret;
	}

	public float getYVelocity() {
		float ret = baseY * force;
		ret += modY;
		return ret;
	}

	public void update(float delta) {
		if (Math.abs(modX) > 0 && xChange > 0) {
			if (Math.abs(modX) > depletion) {
				modX = modX > 0 ? modX - depletion : modX + depletion;
			} else {
				modX = 0;
			}
			xChange = 0;
		}
		if (Math.abs(modY) > 0 && yChange > 0) {
			if (Math.abs(modY) > depletion) {
				modY = modY > 0 ? modY - depletion : modY + depletion;
			} else {
				modY = 0;
			}
			yChange = 0;
		}
		xChange += delta;
		yChange += delta;
	}

	public void addToX(float toX) {
		if (Math.abs(toX + modX) < limit) {
			modX += (modX != 0 ? (toX < 0 ? -1 : 1) * modX * toX : (toX < 0 ? -1 : 1) * 0.025f);
		} else {
			modX = toX > 0 ? limit : limit * -1;
		}
	}

	public void addToY(float toY) {
		if (Math.abs(toY + modY) < limit) {
			modY += (modY != 0 ? (toY < 0 ? -1 : 1) * modY * toY : (toY < 0 ? -1 : 1) * 0.025f);
		} else {
			modY = toY > 0 ? limit : limit * -1;
		}
	}
}
