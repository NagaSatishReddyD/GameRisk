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
	private JTextField Noofarmies_textField;

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
		
		JPanel continent_panel = new JPanel();
		continent_panel.setBounds(616, 6, 387, 776);
		contentPane.add(continent_panel);
		
		JLabel Player_label = new JLabel("Player 1 ");
		Player_label.setBounds(23, 17, 110, 16);
		contentPane.add(Player_label);
		
		JComboBox country_comboBox = new JComboBox();
		country_comboBox.setModel(new DefaultComboBoxModel(new String[] {"Algeria", "Afghanistan", "Berlin", "China", "Canada", "Denmark"}));
		country_comboBox.setBounds(183, 75, 119, 27);
		contentPane.add(country_comboBox);
		
		JTextArea countrylist_textArea = new JTextArea();
		countrylist_textArea.setBounds(173, 151, 129, 73);
		contentPane.add(countrylist_textArea);
		
		JLabel lblNumberOfArmies = new JLabel("Number of Armies");
		lblNumberOfArmies.setBounds(22, 271, 127, 27);
		contentPane.add(lblNumberOfArmies);
		
		Noofarmies_textField = new JTextField();
		Noofarmies_textField.setBounds(173, 271, 130, 26);
		contentPane.add(Noofarmies_textField);
		Noofarmies_textField.setColumns(10);
		
		JButton Reinforce_btn = new JButton("Reinforce");
		Reinforce_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Reinforce_btn.setBounds(364, 12, 117, 29);
		contentPane.add(Reinforce_btn);
		
		JButton attack_btn = new JButton("Attack");
		attack_btn.setBounds(364, 74, 117, 29);
		contentPane.add(attack_btn);
		
		JButton Addarmies_btn = new JButton("Add Armies");
		Addarmies_btn.setBounds(364, 146, 117, 29);
		contentPane.add(Addarmies_btn);
		
		JLabel lblOwnCountries = new JLabel("Own Countries");
		lblOwnCountries.setBounds(20, 76, 98, 23);
		contentPane.add(lblOwnCountries);
		
		JLabel lblAdjacentCountries = new JLabel("Adjacent Countries");
		lblAdjacentCountries.setBounds(23, 177, 138, 16);
		contentPane.add(lblAdjacentCountries);
	}
}
