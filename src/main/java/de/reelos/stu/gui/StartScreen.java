package de.reelos.stu.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.reelos.stu.MainWindow;
import de.reelos.stu.logic.GameWorld;

public class StartScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201606281236L;
	private JButton levelButton = new JButton("Level Auswahl");
	private JButton upgradeButton = new JButton("Upgrades");
	private JButton exitButton = new JButton("Verlassen");
	private MainWindow parent;

	public StartScreen(MainWindow parent) {
		this.parent = parent;
		super.setSize(new Dimension(GameWorld.WORLD_X, GameWorld.WORLD_Y));
		super.setLayout(new BorderLayout(50, 50));
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		levelButton.setAlignmentX(CENTER_ALIGNMENT);
		levelButton.addActionListener(a -> parent.startLevel());
		upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.setAlignmentX(CENTER_ALIGNMENT);
		exitButton.addActionListener(a -> parent.dispose());
		panel.add(levelButton);
		panel.add(upgradeButton);
		panel.add(exitButton);
		super.add(panel, BorderLayout.SOUTH);
	}
	
	public MainWindow getMainWindow() {
		return parent;
	}
}
