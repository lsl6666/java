package gobang;

import java.awt.Container;
import dao.dao;
import dao.MyConnection;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Register.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version  v1.0
 */

public class Register extends JFrame {
	
	/** The frame. */
	private JFrame frame;
	
	/** The user name. */
	private JLabel userName;
	
	/** The user password. */
	private JLabel userPassword;
	
	/** The name txt. */
	private JTextField nameTxt;
	
	/** The password txt. */
	private JTextField passwordTxt;
	
	/** The btn register. */
	private JButton btnRegister;
	
	/** The btn back. */
	private JButton btnBack;

	/**
	 * Inits the.
	 */
	public void init() {
		frame = new JFrame("登录界面");
		userName = new JLabel("用户名：");
		userPassword = new JLabel("密码：");
		nameTxt = new JTextField(20);
		passwordTxt = new JTextField(20);
		btnRegister = new JButton("注册");
		btnBack = new JButton("返回");
		frame.setSize(320, 180);
		frame.setLocation(400, 200);
		frame.setResizable(false);
		frame.setLayout(null);
		userName.setBounds(50, 20, 100, 20);
		frame.add(userName);
		nameTxt.setBounds(130, 20, 100, 20);
		frame.add(nameTxt);

		userPassword.setBounds(50, 60, 100, 20);
		frame.add(userPassword);

		passwordTxt.setBounds(130, 60, 100, 20);
		frame.add(passwordTxt);

		btnRegister.setBounds(80, 90, 60, 20);
		frame.add(btnRegister);

		btnBack.setBounds(180, 90, 60, 20);
		frame.add(btnBack);

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnBack) {
					frame.dispose();
				}
			}

		});

		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnRegister) {
					String name = nameTxt.getText();
					String password = passwordTxt.getText();
					int total=0;
					int win=0;
					int lose=0;
					int flag=0;
					dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
					sqlInfo.updateLoginInfo(name,password);
					sqlInfo.updateWinInfo(name,total,win,lose,flag);
					JOptionPane.showMessageDialog(null, "注册成功");
				}
			}

		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Register().init();
//	}

}
