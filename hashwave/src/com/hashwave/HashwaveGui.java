package com.hashwave;

import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;

import java.io.IOException;

import javax.imageio.ImageIO;

public class HashwaveGui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	Hashwave hwi = new Hashwave();
	
	JTextField keyBox = new JPasswordField(5);
	JTextArea dataBox = new JTextArea();
		JScrollPane dataBoxScrollPane = new JScrollPane(dataBox);
		
	Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK);
	Border blackline = BorderFactory.createLineBorder(Color.black);
	
	JButton encryptButton = new JButton("Encrypt");
	JButton getStatusButton = new JButton("About Hashwave");
	JButton decryptButton = new JButton("Decrypt");
	JLabel errorLabel = new JLabel();
	JLabel encryptionLabel = new JLabel("Data to Encrypt/Decrypt:  ");
	JLabel keyLabel = new JLabel("Secure Key (Need this to encrypt and decrypt your data):  ");
	JPanel mainPane = new JPanel( new BorderLayout(5,5) );
	static String title = Hashwave.versionNumber;
	
	boolean wrap = true;
	boolean wword = true;
	
	
	public HashwaveGui() {
		super("Hashwave "+HashwaveGui.title+" - The Ultimate Encryption Platform");
		
		setSize(700,700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel hwLabel = null;
		BufferedImage hwTitle = null;

		try {
			BufferedImage image = ImageIO.read(getClass().getResource("/resources/hwtitle_2.png"));
			hwTitle = image;
		}catch(IOException e){
			errorLabel.setText(e.toString());
		}
		if(hwTitle !=null) {
			hwLabel = new JLabel(new ImageIcon(hwTitle));
		}
			
			JPanel topTitleContainer = new JPanel(new GridLayout(1,1));
			if(hwLabel !=null){
				topTitleContainer.add(hwLabel);
			}
			topTitleContainer.setPreferredSize(new Dimension(150, 100));
		
			dataBox.setSize(100,100);
			JPanel leftsp = new JPanel();
			JPanel rightsp = new JPanel();
			JPanel encsp = new JPanel();
				encsp.setBorder(new EmptyBorder(15, 15, 15, 15) );
			JPanel keysp = new JPanel();
				keysp.setBorder(new EmptyBorder(15, 15, 15, 15) );

			JPanel stackersp = new JPanel();
				stackersp.setBorder(new EmptyBorder(15, 15, 15, 15) );

			JPanel topLayout = new JPanel();
			JPanel bottomLayout = new JPanel();

			JPanel encContainer = new JPanel();
			JPanel keyContainer = new JPanel();
			
			
			encContainer.setLayout(new BoxLayout(encContainer, BoxLayout.X_AXIS));
			keyContainer.setLayout(new BoxLayout(keyContainer, BoxLayout.X_AXIS));
			
			
			topLayout.setLayout(new BoxLayout(topLayout, BoxLayout.Y_AXIS));
			
			
					encsp.setLayout(new BoxLayout(encsp, BoxLayout.X_AXIS));
					keysp.setLayout(new BoxLayout(keysp, BoxLayout.X_AXIS));
						encsp.setPreferredSize(new Dimension(700,180));
						keysp.setPreferredSize(new Dimension(200,50));
						
					encsp.add(encryptionLabel);
					encsp.add(dataBoxScrollPane);
						dataBox.setLineWrap(wrap);
						dataBox.setWrapStyleWord(wword);
					keysp.add(keyLabel);
					keysp.add(keyBox);
					
					
					
					encContainer.add(encsp);
						encContainer.setBorder(defaultBorder);
					keyContainer.add(keysp);
						keyContainer.setBorder(defaultBorder);
					topLayout.add(BorderLayout.CENTER,topTitleContainer);
					topLayout.add(encContainer);
					topLayout.add(keyContainer);
					
					
					bottomLayout.setLayout(new BoxLayout(bottomLayout, BoxLayout.Y_AXIS));
						bottomLayout.add(errorLabel);
					stackersp.setLayout(new BorderLayout(5,5));
						stackersp.add(topLayout); //encase topLayout in a border container
			
				leftsp.add(encryptButton);
				rightsp.add(decryptButton);
				rightsp.add(getStatusButton);

				mainPane.add("West",leftsp);
				mainPane.add("East",rightsp);
				mainPane.add("North",stackersp);
				mainPane.add("South",bottomLayout);
			
			encryptButton.addActionListener(this);
			decryptButton.addActionListener(this);
			getStatusButton.addActionListener(this);

		add(mainPane);
		setVisible(true);
		pack();
        setLocationRelativeTo(null);
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
        	if(keyOk()){
		        	try {
		        		dataBox.setText( hwi.encryptData(dataBox.getText(),keyBox.getText() ));
		        	} catch (Exception e1){
		        		System.out.println(e1);
		        	}
        	}
        }else if(e.getSource() == decryptButton){
        	if(keyOk()){
	        	try {
	        		String ddata = dataBox.getText();
	        		String key = keyBox.getText();
	        		
	        		dataBox.setText( hwi.decryptData(ddata,key) );
	        	} catch (Exception e1){
	        		System.out.println(e1);
	        	}
        	}
        }else if(e.getSource() == getStatusButton){
        	//Integer fullMessage = hwi.getIvLength();
        	//JOptionPane.showMessageDialog(null,fullMessage.toString());
        	JOptionPane.showMessageDialog(null,Hashwave.aboutMessage);
        }
  
	}
	
	private boolean keyOk() {
    	return true;
	}
	
	
	
}
