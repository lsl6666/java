package gobang;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
// TODO: Auto-generated Javadoc

/**
 * The Class Explain.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version  v1.0
 */
public class Explain extends JFrame{

	
	/** The frame. */
	private JFrame frame;
	
	/** The btn goback. */
	private JButton btnGoback;//返回
	
	/** The font. */
	private Font font;
	
	/** The info. */
	private JLabel info;
	
	/** The machin info. */
	private JLabel machinInfo;
	
	/** The double info. */
	private JLabel doubleInfo;
	
	/** The double info 1. */
	private JLabel doubleInfo1;
	
	/** The online info. */
	private JLabel onlineInfo;
	
	/** The online info 1. */
	private JLabel onlineInfo1;
	
	/**
	 * Inits the.
	 */
	public void init() {
		frame = new JFrame("游戏说明");
		frame.setLayout(null);
		
		info = new JLabel("模式说明");
		Font font = new Font("宋体", Font.BOLD, 50);
		info.setFont(font);
		info.setBounds(400, 10, 300, 60);
		frame.add(info);
		
		machinInfo = new JLabel("人机模式：默认黑棋先手，电脑为白棋");
		Font font1 = new Font("宋体", Font.BOLD, 20);
		machinInfo.setFont(font1);
		machinInfo.setBounds(50, 80, 900, 60);
		frame.add(machinInfo);
		
		doubleInfo = new JLabel("双人模式：有两个先手选择按钮，可选择先手棋子颜色；重新开始按钮功能为清空棋盘；有悔棋");
		doubleInfo.setFont(font1);
		doubleInfo.setBounds(50, 140, 960, 60);
		frame.add(doubleInfo);
		
		doubleInfo1 = new JLabel("功能；点击返回关闭双人模式，返回模式选择界面");
		doubleInfo1.setFont(font1);
		doubleInfo1.setBounds(50, 190, 960, 60);
		frame.add(doubleInfo1);
		
		onlineInfo = new JLabel("联机模式：玩家通过ip地址联机其他用户得到更好的体验。界面中可以实现聊天交流，可以悔棋，");
		onlineInfo.setFont(font1);
		onlineInfo.setBounds(50, 250, 960, 60);
		frame.add(onlineInfo);
		
		onlineInfo1 = new JLabel("可以重新开始游戏");
		onlineInfo1.setFont(font1);
		onlineInfo1.setBounds(50, 300, 960, 60);
		frame.add(onlineInfo1);
		
		btnGoback=new JButton("返回");
		btnGoback.setBounds(800,620,70,30);
		frame.add(btnGoback);
		
		frame.setSize(1000, 730);
		frame.setLocation(300, 10);
		frame.setResizable(false);
		
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
	
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Explain().init();
//	}

}
