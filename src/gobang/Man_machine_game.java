package gobang;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.sql.SQLException;
import java.util.Random;
import gobang.Login;

import javax.swing.*;

// TODO: Auto-generated Javadoc
//class chessBoard1 extends JPanel implements MouseListener {
//
//	
//}

/**
 * The Class Man_machine_game.
 *
 * @date 2020��7��3��
 * @author nieming
 * @version v1.0
 */
public class Man_machine_game extends JFrame {

	/** The chess board. */
	private Machine_chessBoard chessBoard;

	/** The frame. */
	private JFrame frame;

	/** The human left. */
	private JLabel humanLeft;

	/** The human right. */
	private JLabel humanRight;

	/** The chess white box. */
	private JLabel chessWhiteBox;

	/** The chess black box. */
	private JLabel chessBlackBox;

	/** The btn start. */
	private JButton btnStart;// ��Ϸ��ʼ��ť

	/** The btn restart. */
	private JButton btnRestart;// ��Ϸ���¿�ʼ

	/** The btn back. */
	private JButton btnBack;// ��Ϸ����

	/** The btn goback. */
	private JButton btnGoback;// ����

	/** The menu file. */
	private JMenu menuFile;

	/** The menu item restart. */
	private JMenuItem menuItemRestart;

	/** The menu item back. */
	private JMenuItem menuItemBack;

	/** The bar. */
	private JMenuBar bar;

	/** The flag box. */
	int flagBox = 0;

	/**
	 * Instantiates a new man machine game.
	 */
	public Man_machine_game() {
		frame = new JFrame("�˻�ģʽ");
		frame.setLayout(null);
		frame.setSize(1000, 730);
		frame.setLocation(300, 10);
		frame.setResizable(false);

		chessBoard = new Machine_chessBoard();
		chessBoard.setBounds(200, 0, 600, 600);
		frame.add(chessBoard);

		humanLeft = new JLabel();
		humanLeft.setIcon(new ImageIcon("src\\humanleft.png"));
		humanLeft.setBounds(800, 150, 180, 181);
		frame.add(humanLeft);

		humanRight = new JLabel();
		humanRight.setIcon(new ImageIcon("src\\humanright.png"));
		humanRight.setBounds(15, 150, 180, 183);
		frame.add(humanRight);

		chessWhiteBox = new JLabel();
		chessWhiteBox.setIcon(new ImageIcon("src\\whiteChess.png"));
		chessWhiteBox.setBounds(100, 450, 40, 37);
		frame.add(chessWhiteBox);

		chessBlackBox = new JLabel();
		chessBlackBox.setIcon(new ImageIcon("src\\blackChess.png"));
		chessBlackBox.setBounds(850, 450, 40, 37);
		frame.add(chessBlackBox);

		btnRestart = new JButton("���¿�ʼ");
		btnRestart.setBounds(350, 620, 100, 30);
		frame.add(btnRestart);

		btnBack = new JButton("����");
		btnBack.setBounds(500, 620, 70, 30);
		frame.add(btnBack);

		btnGoback = new JButton("����");
		btnGoback.setBounds(600, 620, 70, 30);
		frame.add(btnGoback);

		btnRestart.addActionListener(new ActionListener() { // ���¿�ʼ���ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chessBoard.Restart();
			}

		});

		btnBack.addActionListener(new ActionListener() { // ���幦�ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.gameover == false) {// ��Ϸ�����Ͳ��ܻ�����
					chessBoard.Back();
				}

			}

		});

		btnGoback.addActionListener(new ActionListener() { // ���ع��ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();

			}

		});

		bar = new JMenuBar();
		menuFile = new JMenu("�˵�");
		menuItemRestart = new JMenuItem("���¿�ʼ");
		menuItemBack = new JMenuItem("����");

		frame.setJMenuBar(bar);
		bar.add(menuFile);
		menuFile.add(menuItemRestart);
		menuFile.add(menuItemBack);

		menuItemRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				chessBoard.Restart();
			}

		});

		menuItemBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.gameover == false) {// ��Ϸ�����Ͳ��ܻ�����
					chessBoard.Back();
				}

			}

		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Man_machine_game();
//	}

}
