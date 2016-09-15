package de.reelos.lwjgltest.logic;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

public class Texture {
	private int height, width;
	private ByteBuffer image;
	
	private Texture() {
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public ByteBuffer getImage() {
		return image;
	}
	
	public static class TextureLoader {
		private Texture texture = new Texture();
		public static long RGB = 3;
		public static long RGBA = 4;
		public TextureLoader(BufferedImage image, long mask) {
			texture.height = image.getHeight();
			texture.width = image.getWidth();
			
			int[] pixels = new int[image.getWidth(null) * image.getHeight(null)];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
			
			long bytesPerPixel = mask;
			if(mask < RGB || mask > RGBA) {
				bytesPerPixel = 3;
			}
			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getHeight() * image.getWidth() * (int)bytesPerPixel);
			
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xFF)); //RED
					buffer.put((byte)((pixel >> 8) & 0xFF)); //GREEN
					buffer.put((byte)(pixel & 0xFF)); //BLUE
					if(bytesPerPixel == RGBA) {
						buffer.put((byte)((pixel >> 24) & 0xFF)); //ALPHA
					}
				}
			}
			
			buffer.flip();
			
			texture.image = buffer;
		}
		
		public Texture getTexture() {
			return texture;
		}
	}
}
