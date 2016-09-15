package de.reelos.lwjgltest.objects;

import de.reelos.lwjgltest.logic.Texture;
import de.reelos.lwjgltest.logic.Texture.TextureLoader;

public class Sprite {
	private Texture texture;
	private int width, height;

	public Sprite(TextureLoader loader, String ref) {
		texture = loader.getTexture();
	}
}
