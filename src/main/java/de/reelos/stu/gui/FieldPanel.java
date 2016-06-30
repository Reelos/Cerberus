package de.reelos.stu.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.reelos.stu.MainWindow;
import de.reelos.stu.logic.Boost.BoostType;
import de.reelos.stu.logic.GameObject;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Player;

public class FieldPanel extends JPanel implements Runnable {

	class PlayerControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent evt) {
			System.out.println("some key where pressed");
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setXMotion(-1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setXMotion(1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.setYMotion(-1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setYMotion(1);
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(true);
			}
		}

		@Override
		public void keyReleased(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
				player.setXMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.setXMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_UP) {
				player.setYMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
				player.setYMotion(0);
			}
			if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
				player.setFire(false);
				System.out.println("FIRE!!!");
			}
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
	private Player player;
	private MouseAdapter adapter;
	public KeyAdapter control = new PlayerControl();
	private MainWindow parent;

	public FieldPanel(MainWindow parent) {
		this.parent = parent;
		thread = new Thread(this);
		world = new GameWorld(this);
		player = new Player(world);
		world.setPlayer(player);
		delta = 0f;
		lastLoop = System.currentTimeMillis();
		adapter = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				world.getObjects().add(world.boost());
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
		}
		g.setColor(Color.WHITE);
		g.drawString("Score: " + world.getScore(), 20, GameWorld.WORLD_Y - 40);
		g.setColor(Color.RED);
		g.drawRect(GameWorld.WORLD_X - 168, GameWorld.WORLD_Y - 64, 100, 20);
		g.fillRect(GameWorld.WORLD_X - 168, GameWorld.WORLD_Y - 64,
				(int) (100d * player.getLife() / player.getMaxLife()), 20);
		g.setColor(Color.WHITE);
		g.drawString("( " + player.getLife() + " | " + player.getMaxLife() + " )", GameWorld.WORLD_X - 150,
				GameWorld.WORLD_Y - 50);
		if (player.getAccBoost() > 0) {
			g.setColor(BoostType.ACCELARATION.color());
			g.fillRect(GameWorld.WORLD_X - 200, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getAccBoost()+"", GameWorld.WORLD_X - 190, GameWorld.WORLD_Y - 50);
		}
		if (player.getDmgBoost() > 0) {
			g.setColor(BoostType.SHOOTDAMAGE.color());
			g.fillRect(GameWorld.WORLD_X - 220, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getDmgBoost()+"", GameWorld.WORLD_X - 210, GameWorld.WORLD_Y - 50);
		}
		if (player.getFireRateBoost() > 0) {
			g.setColor(BoostType.FIRERATE.color());
			g.fillRect(GameWorld.WORLD_X - 240, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getFireRateBoost()+"", GameWorld.WORLD_X - 230, GameWorld.WORLD_Y - 50);
		}
		if (player.getShootAccBoost() > 0) {
			g.setColor(BoostType.SHOOTACCELARATION.color());
			g.fillRect(GameWorld.WORLD_X - 260, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getShootAccBoost()+"", GameWorld.WORLD_X - 250, GameWorld.WORLD_Y - 50);
		}
	}

	@Override
	public void run() {
		while (running) {
			delta = (float) (System.currentTimeMillis() - lastLoop) * 0.001f;
			lastLoop = System.currentTimeMillis();

			world.update(delta);
			this.repaint();
		}
	}
	
	public void setLevel(GameWorld world) {
		this.world = world;
		player.setParent(world);
		world.setPlayer(player);
	}
	
	public GameWorld getLevel() {
		return world;
	}

	public void start() {
		running = true;
		thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
		player.setParent(world);
		world.setPlayer(player);
	}
}
