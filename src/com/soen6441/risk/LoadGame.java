package com.soen6441.risk;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * LoadGame class is used to initalize the game
 * @author Naga Satish Reddy
 *
 */
public class LoadGame extends JFrame {

	private JPanel startContentPanel;
	LoadGame frame;

	/**
	 * Launch the application.
	 */
	private void lauchApplication() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	/**
	 * Create the frame.
	 */
	public LoadGame() {
		frame = this;
		lauchApplication();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		startContentPanel = new JPanel();
		setContentPane(startContentPanel);
		
		JButton startGameButton = new JButton("Start Game");
		
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Game loading starts....");
				startButtonActionListener();
			}

		});
		
		
		startContentPanel.add(startGameButton, BorderLayout.CENTER);
	}
	
	
	private void startButtonActionListener() {
		setBounds(100, 100, 450, 300);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 593, 381);
		JPanel playerInfoPanel = new JPanel();
		setContentPane(playerInfoPanel);
		
		
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(System.getProperty("user.dir")+RiskGameConstants.MAP_IMAGE));
			Image scaledImage = bufferedImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
			ImageIcon mapImageIcon = new ImageIcon(scaledImage);
			JLabel imageLabel = new JLabel();
			imageLabel.setIcon(mapImageIcon);
			playerInfoPanel.add(imageLabel);
		} catch (IOException e1) {
			System.out.println("Image not found...");
		}
		
		String [] playersNumbers = {"2","3","4","5","6"};
		JComboBox playerCountCombo = new JComboBox(playersNumbers);
		playerCountCombo.setSelectedIndex(0);
		
		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new BoardFrame(playerCountCombo.getSelectedItem().toString());
			}
		});
		
		JLabel playersCountLabel = new JLabel("Players Count");
		playerInfoPanel.add(playersCountLabel);
		
		playerInfoPanel.add(playerCountCombo);
		playerInfoPanel.add(loadGameButton);	
	}
}
