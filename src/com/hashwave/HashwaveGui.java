package com.hashwave;

import javax.swing.BorderFactory;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;

public class HashwaveGui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	Hashwave hwi;
	
	JTextField keyBox = new JPasswordField(5);
	JTextArea dataBox = new JTextArea(5,5);
		//Border border = BorderFactory.createLineBorder(Color.BLACK);
	
	Border blackline = BorderFactory.createLineBorder(Color.black);
	//dataBox.setBorder(blackline);
	
	JButton encryptButton = new JButton("Encrypt");
	JButton getStatusButton = new JButton("Get Size of IV");
	JButton decryptButton = new JButton("Decrypt");
	JLabel encryptionLabel = new JLabel("Data to Encrypt/Decrypt:  ");
	JLabel keyLabel = new JLabel("Key:  ");
	JPanel mainPane = new JPanel( new BorderLayout(5,5) );
	
	boolean wrap = true;
	boolean wword = true;
	SecretKey key = generateUserKey();
	
	
	public HashwaveGui() {
		super("Hashwave - The Ultimate Encryption Platform");
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
			JPanel leftsp = new JPanel();
			JPanel rightsp = new JPanel();
			JPanel encsp = new JPanel();
				encsp.setBorder(new EmptyBorder(10, 10, 10, 10) );
			JPanel keysp = new JPanel();
				keysp.setBorder(new EmptyBorder(10, 10, 10, 10) );
			JPanel stackersp = new JPanel();
				stackersp.setBorder(new EmptyBorder(10, 10, 10, 10) );

			JPanel innerLayout = new JPanel();

			JPanel encContainer = new JPanel();
			JPanel keyContainer = new JPanel();
			
				encContainer.setLayout(new BorderLayout(5,5));
				keyContainer.setLayout(new BorderLayout(5,5));
	
				
			encsp.setLayout(new BoxLayout(encsp, BoxLayout.X_AXIS));
			keysp.setLayout(new BoxLayout(keysp, BoxLayout.X_AXIS));

			innerLayout.setLayout(new BoxLayout(innerLayout, BoxLayout.Y_AXIS));
			stackersp.setLayout(new BorderLayout(5,5));
			
			
					encsp.add(encryptionLabel);
					encsp.add(dataBox);
						dataBox.setLineWrap(wrap);
						dataBox.setWrapStyleWord(wword);
					keysp.add(keyLabel);
					keysp.add(keyBox);
					
					encContainer.add(encsp);
					keyContainer.add(keysp);
					
					innerLayout.add(encContainer);
					innerLayout.add(keyContainer);
				stackersp.add(innerLayout);
			
				leftsp.add(encryptButton);
				rightsp.add(decryptButton);
				rightsp.add(getStatusButton);

				mainPane.add("West",leftsp);
				mainPane.add("East",rightsp);
				mainPane.add("North",stackersp);
			
			encryptButton.addActionListener(this);
			decryptButton.addActionListener(this);
			getStatusButton.addActionListener(this);

		add(mainPane);
		setVisible(true);
		hwi = new Hashwave();
	}
	
	private SecretKey generateUserKey() {
    	KeyGenerator kg = null;
			try {
				kg = KeyGenerator.getInstance("AES");
			} catch (NoSuchAlgorithmException e2) {
				e2.printStackTrace();
			}
	            kg.init(128);
	            SecretKey key = kg.generateKey();
	    return key;
	}
	
	protected void setStyle() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			
		} catch (Exception exc){
			System.out.println(exc.getMessage());
		}
	}

	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == encryptButton){
        	try {
        		dataBox.setText( hwi.encryptData(dataBox.getText(),keyBox.getText() ));
        	} catch (Exception e1){
        		System.out.println(e1);
        	}
        }else if(e.getSource() == decryptButton){
        	try {
        		String ddata = dataBox.getText();
        		String key = keyBox.getText();
        		
        		dataBox.setText( hwi.decryptData(ddata,key) );
        	} catch (Exception e1){
        		System.out.println(e1);
        	}
        }else if(e.getSource() == getStatusButton){
        	//Integer fullMessage = hwi.getIvLength();
        	//JOptionPane.showMessageDialog(null,fullMessage.toString());
        	JOptionPane.showMessageDialog(null,"Nothing to show. Using MCrypt version 1.0 Modified by Sharetunnel Inc. (Hashwave Inc.)");
        }
	}
	
	
	
}
