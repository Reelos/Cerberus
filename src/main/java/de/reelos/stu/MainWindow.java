package de.reelos.stu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.reelos.stu.gui.FieldPanel;
import de.reelos.stu.gui.StartScreen;
import de.reelos.stu.logic.GameWorld;
import de.reelos.stu.logic.Player;
import de.reelos.stu.logic.TrainingLevel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201606151555L;
	private JPanel panel;
	private FieldPanel levelPanel = new FieldPanel(this);
	private Player active = new Player(null);

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
	
	public void startLevel() {
		levelPanel.setBackground(Color.BLACK);
		levelPanel.setLevel(new TrainingLevel(levelPanel));
		levelPanel.setPlayer(active);
		addKeyListener(levelPanel.control);
		remove(panel);
		add(levelPanel);
		revalidate();
		repaint();
		levelPanel.start();
	}
}
