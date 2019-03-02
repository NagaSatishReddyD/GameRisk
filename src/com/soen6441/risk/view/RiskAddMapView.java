package com.soen6441.risk.view;

import javax.swing.*;

/**
 * RiskAddMapView contains the frame for users to upload the map files
 * @author An Nguyen
 *
 */
public class RiskAddMapView extends JFrame {
	
	private JFrame frame;
	private JLabel mapFileLabel;
	private JButton mapUploadBtn;
	private JButton backBtn;

	public RiskAddMapView() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setSize(400,200);
		
		mapFileLabel = new JLabel("Choose 1 .map and 1 .bmp file");
		mapFileLabel.setBounds(107, 45, 215, 16);
		frame.getContentPane().add(mapFileLabel);
		
		mapUploadBtn = new JButton("Choose files");
		mapUploadBtn.setBounds(41, 124, 117, 29);
		frame.getContentPane().add(mapUploadBtn);
		
		backBtn = new JButton("Back");
		backBtn.setBounds(254, 124, 117, 29);
		frame.getContentPane().add(backBtn);
	}

	public JButton getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(JButton backBtn) {
		this.backBtn = backBtn;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JLabel getMapFileLabel() {
		return mapFileLabel;
	}

	public void setMapFileLabel(JLabel mapFileLabel) {
		this.mapFileLabel = mapFileLabel;
	}


	public JButton getMapUploadBtn() {
		return mapUploadBtn;
	}

	public void setMapUploadBtn(JButton mapUploadBtn) {
		this.mapUploadBtn = mapUploadBtn;
	}
}
