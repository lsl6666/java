package gobang;

import java.awt.Color;
import java.awt.HeadlessException;

import dao.dao;
import dao.MyConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Login.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version v1.0
 */

public class Login extends JFrame {

	/** The frame. */
	private JFrame frame;
	
	private Machine_chessBoard chessBoard;

	/** The user name. */
	private JLabel userName;

	/** The user password. */
	private JLabel userPassword;

	/** The name txt. */
	private JTextField nameTxt;

	/** The password txt. */
	private JTextField passwordTxt;

	/** The btn ok. */
	private JButton btnOk;

	/** The btn cancel. */
	private JButton btnCancel;

	/** The btn register. */
	private JButton btnRegister;
	
	public Login() {
	}
	public void init() {
		frame = new JFrame("登录界面");
		userName = new JLabel("用户名：");
		userPassword = new JLabel("密码：");
		nameTxt = new JTextField(20);
		passwordTxt = new JTextField(20);
		btnOk = new JButton("确定");
		btnCancel = new JButton("取消");
		btnRegister = new JButton("注册");
		
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

		btnOk.setBounds(40, 90, 60, 20);
		frame.add(btnOk);

		btnCancel.setBounds(120, 90, 60, 20);
		frame.add(btnCancel);

		btnRegister.setBounds(200, 90, 60, 20);
		frame.add(btnRegister);
		
		
		dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
		sqlInfo.updateFlagZero();
		
		btnOk.addActionListener(new ActionListener() {//确定按钮功能实现

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnOk) {
					String name = nameTxt.getText();
					String password = passwordTxt.getText();
					dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
					try {
						if (sqlInfo.queryLoginInfo(name, password) == 1) {
							sqlInfo.updateFlagInfo(name);
							choose in = new choose();
							in.init();
							frame.dispose();
						} else {
							return;
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}

		});
		
		

		btnCancel.addActionListener(new ActionListener() {//取消

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnCancel) {
					// frame.setVisible(false);
					frame.dispose();
				}
			}

		});

		btnRegister.addActionListener(new ActionListener() {//注册

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (e.getSource() == btnRegister) {
					// frame.setVisible(false);
					Register in = new Register();
					in.init();
				}
			}

		});
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Login a = new Login();
		a.init();
	}

}
