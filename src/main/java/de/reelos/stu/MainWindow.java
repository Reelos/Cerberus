package de.reelos.stu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.reelos.stu.gui.FieldPanel;
import de.reelos.stu.gui.StartScreen;
import de.reelos.stu.level.TrainingLevel;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.objects.player.Player;


public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201606151555L;
	private JPanel panel;
	private FieldPanel levelPanel;
	private Player active = new Player(null,30);
	private GameWorld selectedLevel = new TrainingLevel();

	public MainWindow() {
		super("Cerberus");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setPreferredSize(new Dimension(GameWorld.WORLD_X, GameWorld.WORLD_Y));
		panel = new StartScreen(this);
		super.add(panel);
		super.pack();
		super.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new MainWindow().setVisible(true);
	}
	
	public void backToMenu() {
		remove(levelPanel);
		add(panel);
		revalidate();
		repaint();
		panel.requestFocus();
	}

	public void startLevel() {
		selectedLevel = new TrainingLevel();
		levelPanel = new FieldPanel(this, selectedLevel, active);
		levelPanel.setBackground(Color.BLACK);
		remove(panel);
		add(levelPanel);
		revalidate();
		repaint();
		levelPanel.requestFocus();
		addKeyListener(active.control);
		levelPanel.addKeyListener(active.control);
		levelPanel.start();
	}
}
