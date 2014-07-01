package Hashwave;

import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;

public class HashwaveGui extends JFrame implements ActionListener {
	
	/**
	 * Don't even know what this does but whatever 0_o
	 */
	private static final long serialVersionUID = 1L;
	Hashwave hwi;
	JTextField keyBox = new JTextField(15);
	JTextArea dataBox = new JTextArea(10,50);
	JButton encryptButton = new JButton("Encrypt");
	JButton decryptButton = new JButton("Decrypt");		
	boolean wrap = true;
	boolean wword = true;
	SecretKey key = generateUserKey();
	
	
	public HashwaveGui() {
		super("Hashwave - The Ultimate Encryption Platform");
		setSize(600,600);
		setStyle();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



		dataBox.setLineWrap(wrap);
		dataBox.setWrapStyleWord(wword);
		
		setupPane();
		hwi = new Hashwave();
	}
	
	private SecretKey generateUserKey() {
    	KeyGenerator kg = null;
			try {
				kg = KeyGenerator.getInstance(hwi.defaultCipher);
			} catch (NoSuchAlgorithmException e2) {
				e2.printStackTrace();
			}
	            kg.init(56);
	            SecretKey key = kg.generateKey();
	    return key;
	}
	
	private static void setStyle() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			
		} catch (Exception exc){
			System.out.println(exc.getMessage());
		}
	}
	
	private void setupPane() {		

		JPanel pane = new JPanel(new BorderLayout());
		
		JLabel encryptionLabel = new JLabel("Data to Encrypt/Decrypt");
		JLabel keyLabel = new JLabel("Key");		
		
		JPanel leftsp = new JPanel();
		JPanel rightsp = new JPanel();
		
			pane.add(encryptionLabel);
			pane.add(dataBox);
			pane.add(keyLabel);
			pane.add(keyBox);
		
		leftsp.add(encryptButton);
		rightsp.add(decryptButton);
		
		encryptButton.addActionListener(this);
		decryptButton.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == encryptButton){
	        	try {
	        		dataBox.setText(hwi.maskData( hwi.encryptData(dataBox.getText().getBytes(),this.key) ).toString());
	        	} catch (Exception e1){
	        		System.out.println(e1);
	        	}
        }else if(e.getSource() == decryptButton){
	        	try {
	        		dataBox.setText( hwi.unmaskData( hwi.decryptData(dataBox.getText().getBytes(),this.key).toString() ).toString() );
	        	} catch (Exception e1){
	        		System.out.println(e1);
	        	}
        }
	}
	
	
	
}
