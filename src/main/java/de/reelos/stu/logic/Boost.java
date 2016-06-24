package de.reelos.stu.logic;

import java.awt.Color;

public class Boost extends GameObject {
	
	private Color color = Color.WHITE;

	public Boost(int x, int y, int height, int width, Color color) {
		super(x, y, height, width, 1, 0, 0, 0, 0);
		
	}

	
	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void hit(GameObject obj) {
		
	}
}
