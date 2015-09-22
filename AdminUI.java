package com.krkh.xls.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Popup;

public class AdminUI extends JPanel implements ActionListener{
	private JFrame frame;
	private JComboBox dbCombo;
	private JTextField userName;
	private JPasswordField password;
	private JTextField sid;
	private JTextField ipAddress;
	private JTextField portNum;
	private JTextField sqLiteDB;
	Container oracleContainer;
	Container ipDtls;
	Container sqlLite;
	Container footer;
	
	
	//public void paintAdminScreen(){
	public void paintAdminScreen(){
		frame = new JFrame("Admin UI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container dbDtls = DBDetails();
		oracleContainer = OracleDBDtls();
		ipDtls = ipDtls();
		sqlLite = sqLiteDtls();
		footer = footerContainer();
		
		frame.setLayout(new GridLayout(8, 3, 10, 10));
		frame.add(subHeader());
		frame.setSize(400,500);
		frame.add(dbDtls);
		frame.add(ipDtls);
		frame.add(oracleContainer);
		frame.add(sqlLite);
		frame.add(footer);
		frame.setVisible(true);
	}
	public Container DBDetails(){
		Container dbContainer = new Container();
		dbContainer.setLayout(new GridLayout(1,3,10,10));
		JLabel dbLabel = new JLabel();
		dbLabel.setAlignmentX(LEFT_ALIGNMENT);
		dbLabel.setText("DB : ");
		String[] options = {"","Oracle","MySql","SQLite"};
		dbCombo = new JComboBox(options);
		dbCombo.setSize(40,5);
		dbContainer.add(dbLabel);
		dbContainer.add(dbCombo);
		dbContainer.add(blankLabel());
		dbCombo.addActionListener(this);
		
		return dbContainer;
	}
	
	public Container subHeader(){
		Container subHeader = new Container();
		subHeader.setLayout(new GridLayout(1, 3));
		JLabel subHeaderLabel = new JLabel();
		subHeaderLabel.setText("DB Details");
		subHeaderLabel.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		subHeader.add(blankLabel());
		subHeader.add(subHeaderLabel);
		subHeader.add(blankLabel());
		return subHeader;
	}
	
	private Container ipDtls(){
		Container ipContainer = new Container();
		ipContainer.setLayout(new GridLayout(3,3));
		JLabel userNameLabel = new JLabel("IP Address : ");
		JLabel passwordLabel = new JLabel("Port Num : ");
		
		ipAddress = new JTextField();
		portNum = new JTextField();
		
		ipContainer.add(blankLabel());
		ipContainer.add(blankLabel());
		ipContainer.add(blankLabel());
		
		ipContainer.add(userNameLabel);
		ipContainer.add(ipAddress);
		ipContainer.add(blankLabel());
		
		ipContainer.add(passwordLabel);
		ipContainer.add(portNum);
		ipContainer.add(blankLabel());
		
		
		return ipContainer;
	}
	
	private Container OracleDBDtls(){
		Container oracleContainer = new Container();
		oracleContainer.setLayout(new GridLayout(3,3));
		JLabel userNameLabel = new JLabel("User Name : ");
		JLabel passwordLabel = new JLabel("Password : ");
		JLabel sidLabel = new JLabel("Oracle SID : ");
		
		userName = new JTextField();
		password = new JPasswordField();
		sid = new JTextField();
		
		/* User Name */
		oracleContainer.add(userNameLabel);
		oracleContainer.add(userName);
		oracleContainer.add(blankLabel());
		
		/* Password */
		oracleContainer.add(passwordLabel);
		oracleContainer.add(password);
		oracleContainer.add(blankLabel());
		
		/* Oracle SID */
		oracleContainer.add(sidLabel);
		oracleContainer.add(sid);
		oracleContainer.add(blankLabel());
		
		return oracleContainer;
	}
	
	public JLabel blankLabel(){
		JLabel blankLabel = new JLabel();
		blankLabel.setText("");
		return blankLabel;
	}
	
	public Container sqLiteDtls(){
		Container sqLiteContainer = new Container();
		sqLiteContainer.setLayout(new GridLayout(3,3));
		JLabel sqLiteLabel = new JLabel("SQLite DB: ");
		sqLiteDB = new JTextField();
		
		sqLiteContainer.add(blankLabel());
		sqLiteContainer.add(blankLabel());
		sqLiteContainer.add(blankLabel());
		
		sqLiteContainer.add(sqLiteLabel);
		sqLiteContainer.add(sqLiteDB);
		sqLiteContainer.add(blankLabel());
		
		sqLiteContainer.add(blankLabel());
		sqLiteContainer.add(blankLabel());
		sqLiteContainer.add(blankLabel());
		
		
		return sqLiteContainer;
	}
	
	public Container footerContainer(){
		Container footer = new Container();
		footer.setLayout(new GridLayout(2,4,10,10));
		JButton submit = new JButton("Submit");
		JButton clear = new JButton("Clear");
		
		footer.add(blankLabel());
		footer.add(submit);
		footer.add(clear);
		footer.add(blankLabel());
		footer.add(blankLabel());
		footer.add(blankLabel());
		footer.add(blankLabel());
		footer.add(blankLabel());
		submit.addActionListener(this);
		clear.addActionListener(this);
		return footer;
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Submit"))
			handleSubmitEvent();
		else if(event.getActionCommand().equals("Clear"))
			handleClearEvent();
		else if(event.getActionCommand().equals("comboBoxChanged"))
			handleComboChangeEvent();
		else
			JOptionPane.showInputDialog(this, "event not known");
	}
	
	public void handleComboChangeEvent(){
		String selectedItem = (String)dbCombo.getSelectedItem();
		if(selectedItem.equals("SQLite")){
			frame.remove(ipDtls);
			frame.remove(oracleContainer);
			frame.add(sqlLite);
			frame.add(footer);
			frame.revalidate();
			frame.repaint();	
		}
		else{
			frame.remove(sqlLite);
			frame.remove(footer);
			frame.add(ipDtls);
			frame.add(oracleContainer);
			frame.add(footer);
			frame.revalidate();
			frame.repaint();
		}
		//JOptionPane.showInputDialog(this, selectedItem);
	}

	private void handleSubmitEvent(){
		String selectedItem = (String)dbCombo.getSelectedItem();
		Encrptor encrypt = new Encrptor();
		encrypt.setDbType(selectedItem);
		if(selectedItem.equals("SQLite"))
			encrypt.setSqLiteDB(sqLiteDB.getText());
		else{
			encrypt.setIpAddress(ipAddress.getText());
			encrypt.setPortNum(portNum.getText());
			encrypt.setUserId(userName.getText());
			String pass = new String(password.getPassword());
			encrypt.setPassword(pass);
			encrypt.setSid(sid.getText());
		}
		encrypt.encryptKeys();
		//String data = encrypt.decryptFileContent("./encrypted");
		//System.out.println("data after decryption "+data);
	}
	private void handleClearEvent(){
		
	}
}
