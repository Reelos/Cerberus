package de.reelos.stu.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.reelos.stu.logic.objects.player.Player;

public class MachinePanel extends JPanel {

	private Player player;
	private boolean isSelected = false;

	public MachinePanel(Player player) {
		this.player = player;
		Dimension size = new Dimension(50, 75);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		setBackground(Color.BLACK);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = player.getImage().getWidth(null), h = player.getImage().getHeight(null);
		if (isSelected) {
			g.setColor(Color.blue);
			g.drawRect(0, 0, getPreferredSize().width - 2, getPreferredSize().height - 2);
		}
		g.drawImage(player.getImage(), (getPreferredSize().width - w) / 2, (getPreferredSize().height - h - 25) / 2,
				null);
		g.setFont(new Font("Terminal", 0, 10));
		g.setColor(Color.white);
		g.drawString(player.getClass().getSimpleName(), 5, getPreferredSize().height - 12);
		System.out.println(player.getClass().getSimpleName());
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void isSelected(boolean state) {
		isSelected = state;
	}
	
	public Player getPlayer() {
		return player;
	}
}
