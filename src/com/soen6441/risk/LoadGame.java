package com.soen6441.risk;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * LoadGame class is used to initalize the game
 * @author Naga Satish Reddy
 *
 */
public class LoadGame extends JFrame {

	private JPanel contentPane;
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
		contentPane = new JPanel();
		setContentPane(contentPane);
		
		JButton startGameButton = new JButton("Start Game");
		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Game loading starts....");
			}
		});
		contentPane.add(startGameButton, BorderLayout.CENTER);
	}
}
