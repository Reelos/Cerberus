package de.reelos.stu.logic;

import java.awt.Color;

public class Player extends GameObject {

	private int firePower = 10;
	
	public Player() {
		super(GameWorld.WORLD_X/5-10, GameWorld.WORLD_Y/2-20, 20, 40, 120, 0, 0);
	}
	
	@Override
	public Color getColor() {
		return Color.BLUE.brighter();
	}
	
	@Override
	public GOType getType() {
		return GOType.PLAYER;
	}
	
	public Bullet fire() {
		return new Bullet(getX()+getWidth()+2,getY()+getHeight()/2-2,firePower);
	}
	
	public int getXSpeed() {
		return 0;
	}
	
	public int getYSpeed() {
		return 0;
	}

}
