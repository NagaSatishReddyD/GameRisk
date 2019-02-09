package com.soen6441.risk.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextArea;
import java.awt.Label;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RiskBoardView extends JFrame {

	private JPanel contentPane;
	private JFrame boardFrame;

	/**
	 * Create the frame.
	 */
	public RiskBoardView() {
		boardFrame = new JFrame("Risk Game");
		boardFrame.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dimention = Toolkit.getDefaultToolkit().getScreenSize();
		boardFrame.setSize(dimention.width, dimention.height);
		contentPane = new JPanel();
		
		boardFrame.getContentPane().add(contentPane);
		contentPane.setLayout(null);
		
		JPanel continentPanel = new JPanel();
		continentPanel.setBounds(616, 6, 387, 723);
		contentPane.add(continentPanel);
		continentPanel.setLayout(null);
		
		JTextArea continentTextArea = new JTextArea();
		continentTextArea.setBounds(10, 5, 355, 322);
		continentPanel.add(continentTextArea);
		
		JLabel playerLabel = new JLabel("Player 1 ");
		playerLabel.setBounds(23, 17, 110, 16);
		contentPane.add(playerLabel);
		
		JComboBox countryComboBox = new JComboBox();
		countryComboBox.setModel(new DefaultComboBoxModel(new String[] {"Algeria", "Afghanistan", "Berlin", "China", "Canada", "Denmark"}));
		countryComboBox.setBounds(183, 75, 119, 27);
		contentPane.add(countryComboBox);
		
		JTextArea countryListTextArea = new JTextArea();
		countryListTextArea.setBounds(173, 151, 129, 73);
		contentPane.add(countryListTextArea);
		
		JButton reinforceBtn = new JButton("Reinforce");
		reinforceBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		reinforceBtn.setBounds(364, 12, 117, 29);
		contentPane.add(reinforceBtn);
		
		JButton attackBtn = new JButton("Attack");
		attackBtn.setBounds(364, 74, 117, 29);
		contentPane.add(attackBtn);
		
		JButton addArmiesBtn = new JButton("Add Armies");
		addArmiesBtn.setBounds(364, 146, 117, 29);
		contentPane.add(addArmiesBtn);
		
		JLabel adjacentCountriesLabel = new JLabel("Adjacent Countries");
		adjacentCountriesLabel.setBounds(23, 177, 138, 16);
		contentPane.add(adjacentCountriesLabel);
		
		JLabel numberOfArmiesLabel = new JLabel("Number Of Armies");
		numberOfArmiesLabel.setBounds(148, 295, 88, 27);
		contentPane.add(numberOfArmiesLabel);
		
		Label lableNumberOfArmies = new Label("Number Of Armies");
		lableNumberOfArmies.setBounds(10, 295, 132, 22);
		contentPane.add(lableNumberOfArmies);
		
		Label ownCountriesLabel = new Label("Own Countries");
		ownCountriesLabel.setBounds(23, 75, 119, 22);
		contentPane.add(ownCountriesLabel);
	}
}
