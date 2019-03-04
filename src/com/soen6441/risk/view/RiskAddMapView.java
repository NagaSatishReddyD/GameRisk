package com.soen6441.risk.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

	/**
	 * The constructor creates a new frame that contains the components for uploading the map
	 * There are 2 buttons in this frame: 
	 * <p>
	 * <b>Choose files button:</b> Choose files button to select 1 map and 1 image file
	 * <b>Back button: </b> To go back to the previous frame.
	 * </p>
	 */
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

	/**
	 * Return the back button object in this frame. Clicking this button will go back 
	 * to the previous frame
	 * @return back button of this frame
	 */
	public JButton getBackBtn() {
		return backBtn;
	}

	/**
	 * Return the frame object of this frame
	 * @return the frame object
	 */
	public JFrame getFrame() {
		return frame;
	}

	/**
	 * Return the label object set on this frame
	 * @return the label object
	 */
	public JLabel getMapFileLabel() {
		return mapFileLabel;
	}

	/**
	 * Return the upload map button object in this frame. Click this button will require the user
	 * to upload the map files
	 * @return upload files button
	 */
	public JButton getMapUploadBtn() {
		return mapUploadBtn;
	}

}
