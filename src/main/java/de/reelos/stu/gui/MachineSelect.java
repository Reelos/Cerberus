package de.reelos.stu.gui;

import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import de.reelos.stu.MainWindow;
import de.reelos.stu.logic.objects.player.Carrier;
import de.reelos.stu.logic.objects.player.Player;
import de.reelos.stu.logic.objects.player.Raider;

public class MachineSelect extends JPanel {

	private MainWindow parent;
	private List<MachinePanel> playerList = Arrays.asList(new MachinePanel(new Carrier(null)), new MachinePanel(new Raider(null)));
	private Player selected = null;

	public MachineSelect(MainWindow parent, Player player) {
		this.parent = parent;
		this.selected = player;
		for (MachinePanel panel : playerList) {
			if(panel.getPlayer().getClass() == selected.getClass())
				panel.isSelected(true);
			panel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						MachinePanel src = (MachinePanel)e.getSource();
						playerList.forEach(p -> p.isSelected(false));
						src.isSelected(true);
						selected = src.getPlayer();
						repaint();
					}
				}
			});
			add(panel);
		}
	}

	public void setSelection(Player player) {
		this.selected = player;
	}
}
