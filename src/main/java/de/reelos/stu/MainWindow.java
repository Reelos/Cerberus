package de.reelos.stu;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

import de.reelos.stu.gui.FieldPanel;
import de.reelos.stu.logic.GameWorld;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 201606151555L;
	private FieldPanel panel;
	
	public MainWindow() {
		super("Collision Test");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setPreferredSize(new Dimension(GameWorld.WORLD_X,GameWorld.WORLD_Y));
		panel = new FieldPanel();
		panel.setBackground(Color.BLACK);
		super.add(panel);
		super.pack();
		super.setLocationRelativeTo(null);
		addKeyListener(panel.control);
		panel.start();
	}
	
	public static void main(String[] args) {
		new MainWindow().setVisible(true);
	}
}
