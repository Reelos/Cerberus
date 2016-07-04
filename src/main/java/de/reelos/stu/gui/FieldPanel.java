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
	private MainWindow parent;

	public FieldPanel(MainWindow parent, GameWorld world, Player player) {
		this.parent = parent;
		this.thread = new Thread(this);
		this.world = world;
		this.player = player;
		this.world.setPlayer(this.player);
		this.player.setParent(this.world);
		this.delta = 0f;
		this.lastLoop = System.currentTimeMillis();
		this.adapter = new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				world.getObjects().add(world.boost());
			}
		};
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
			g.drawString(player.getAccBoost() + "", GameWorld.WORLD_X - 190, GameWorld.WORLD_Y - 50);
		}
		if (player.getDmgBoost() > 0) {
			g.setColor(BoostType.SHOOTDAMAGE.color());
			g.fillRect(GameWorld.WORLD_X - 220, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getDmgBoost() + "", GameWorld.WORLD_X - 210, GameWorld.WORLD_Y - 50);
		}
		if (player.getFireRateBoost() > 0) {
			g.setColor(BoostType.FIRERATE.color());
			g.fillRect(GameWorld.WORLD_X - 240, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getFireRateBoost() + "", GameWorld.WORLD_X - 230, GameWorld.WORLD_Y - 50);
		}
		if (player.getShootAccBoost() > 0) {
			g.setColor(BoostType.SHOOTACCELARATION.color());
			g.fillRect(GameWorld.WORLD_X - 260, GameWorld.WORLD_Y - 64, 20, 20);
			g.setColor(Color.BLACK);
			g.drawString(player.getShootAccBoost() + "", GameWorld.WORLD_X - 250, GameWorld.WORLD_Y - 50);
		}
	}

	@Override
	public void run() {
		while (running) {
			delta = (float) (System.currentTimeMillis() - lastLoop) * 0.001f;
			lastLoop = System.currentTimeMillis();

			world.update(delta);
			this.repaint();
			if (world.isClear()) {
				running = false;
				parent.backToMenu();
			}
		}
	}

	public void setLevel(GameWorld world) {
		this.world = world;
		world.setPlayer(player);
		player.setParent(world);
	}

	public GameWorld getLevel() {
		return world;
	}

	public void start() {
		requestFocus();
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
