package Hashwave;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;

public class HashwaveGui extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	Hashwave hwi;
	
	JTextField keyBox = new JTextField(5);
	JTextArea dataBox = new JTextArea(5,5);
	JButton encryptButton = new JButton("Encrypt");
	JButton getStatusButton = new JButton("Get Size of IV");
	JButton decryptButton = new JButton("Decrypt");
	JLabel encryptionLabel = new JLabel("Data to Encrypt/Decrypt");
	JLabel keyLabel = new JLabel("Key");
	JPanel mainPane = new JPanel( new BorderLayout() );
	
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
			//JPanel keysp = new JPanel();
			JPanel stackersp = new JPanel();

			encsp.setLayout(new BoxLayout(encsp, BoxLayout.X_AXIS));
			//keysp.setLayout(new BoxLayout(keysp, BoxLayout.X_AXIS));
			
			stackersp.setLayout(new BoxLayout(stackersp, BoxLayout.Y_AXIS));
			
			
					encsp.add(encryptionLabel);
					encsp.add(dataBox);
						dataBox.setLineWrap(wrap);
						dataBox.setWrapStyleWord(wword);
					//keysp.add(keyLabel);
					//keysp.add(keyBox);
					
				stackersp.add(encsp);
				//stackersp.add(keysp);
			
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
        		dataBox.setText( hwi.encryptData(dataBox.getText().getBytes(),this.key).toString());
        	} catch (Exception e1){
        		System.out.println(e1);
        	}
        }else if(e.getSource() == decryptButton){
        	try {
        		byte[] datab = dataBox.getText().getBytes();
        		byte[] datab1 = java.util.Arrays.copyOf(datab,48);
        		
        		dataBox.setText( hwi.decryptData(datab1,this.key).toString());
        	} catch (Exception e1){
        		System.out.println(e1);
        	}
        }else if(e.getSource() == getStatusButton){
        	Integer fullMessage = hwi.getIvLength();
        	JOptionPane.showMessageDialog(null,fullMessage.toString());
        }
	}
	
	
	
}
