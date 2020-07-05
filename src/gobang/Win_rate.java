package gobang;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import dao.dao;
import dao.MyConnection;
import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Win_rate.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version v1.0
 */
public class Win_rate extends JFrame {

	/** The frame. */
	private JFrame frame;

	/** The btn goback. */
	private JButton btnGoback;// 返回

	/** The font. */
	private Font font;

	/** The info. */
	private JLabel info;

	/** The user info. */
	private JLabel userInfo;

	/** The rate info. */
	private JLabel rateInfo;

	/**
	 * Inits the.
	 */
	public void init() {
		frame = new JFrame("胜率统计");
		frame.setLayout(null);
		frame.setSize(540, 600);
		frame.setLocation(500, 100);
		frame.setResizable(false);

		info = new JLabel("胜率统计");
		Font font = new Font("宋体", Font.BOLD, 50);
		info.setFont(font);
		info.setBounds(150, 10, 300, 60);
		frame.add(info);

		dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
//		String name;
		try {
			String name = sqlInfo.queryFlag();
			rateInfo = new JLabel("用户名：" + name);
			rateInfo.setFont(font);
			rateInfo.setBounds(10, 100, 300, 60);
			frame.add(rateInfo);
			
			String a = sqlInfo.queryWinRate(name);
			info = new JLabel("胜率为：" + a);
			Font font1 = new Font("宋体", Font.BOLD, 50);
			info.setFont(font1);
			info.setBounds(10, 200, 500, 60);
			frame.add(info);
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}


		btnGoback = new JButton("返回");
		btnGoback.setBounds(400, 500, 70, 30);
		frame.add(btnGoback);

		btnGoback.addActionListener(new ActionListener() { // 返回功能的按钮实现

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();

			}

		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Win_rate().init();
//	}

}
