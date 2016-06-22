package de.reelos.stu.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.reelos.stu.logic.Bullet;
import de.reelos.stu.logic.GameObject;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Player;

public class FieldPanel extends JPanel implements Runnable {

	
	class PlayerControl extends KeyAdapter {
		@Override 
		public void keyPressed(KeyEvent evt) {
			if(evt.getKeyCode() == KeyEvent.VK_LEFT) {
				//TODO
			}
			if(evt.getKeyCode() == KeyEvent.VK_SPACE) {
				if(lastShoot >= shootTimeOut) {
					world.getObjects().add(player.fire());
					lastShoot = 0;
				}
			}
			System.out.println("Some Key was pressed!");
		}
		
		@Override
		public void keyReleased(KeyEvent evt) {
			//TODO
			System.out.println("Some Key was released!");
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 201606151543L;
	private float delta;
	private long lastLoop;
	private Thread thread;
	private GameWorld world;
	private boolean running;
	private Player player = new Player();
	private MouseAdapter adapter;
	public KeyAdapter control = new PlayerControl();
	private float lastShoot;
	private final float shootTimeOut = 0.5f;

	public FieldPanel() {
		thread = new Thread(this);
		world = new GameWorld();
		world.getObjects().add(player);
		delta = 0f;
		lastLoop = System.currentTimeMillis();
		lastShoot = 0f;
		adapter = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent evt) {
				if(lastShoot >= shootTimeOut) {
					world.getObjects().add(new Bullet(evt.getX()-2,evt.getY()-5,20));
					lastShoot = lastLoop;
				}
			}
		};
		addMouseListener(adapter);
		addKeyListener(control);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(this.getBackground());
		g.fillRect(0, 0, GameWorld.WORLD_X, GameWorld.WORLD_Y);

		for (int i = 0; i < world.getObjects().size(); i++) {
			GameObject go = world.getObjects().get(i);
			g.setColor(go.getColor());
			g.fillRect(go.getX(), go.getY(), go.getWidth(), go.getHeight());
			g.setColor(Color.BLACK);
			g.drawString(String.valueOf(go.getLife()), go.getX()+2, go.getY()+15);
		}

		g.setColor(Color.WHITE);
		g.drawString("Score: " + world.getScore(), 20, GameWorld.WORLD_Y - 40);
	}

	@Override
	public void run() {
		while (running) {
			delta = (float) (System.currentTimeMillis() - lastLoop) * 0.001f;
			lastShoot += delta;
			lastLoop = System.currentTimeMillis();

			world.update(delta);
			this.repaint();

			if (world.getScore() >= 20) {
				running = false;
			}
		}
	}

	public void start() {
		running = true;
		thread.start();
	}
}
