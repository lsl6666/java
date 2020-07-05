package gobang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


import javax.swing.*;
import gobang.Site;

// TODO: Auto-generated Javadoc
class ChessBoard extends JPanel implements MouseListener {

	private int backGauge = 37; // �߾�
	private int gridDistance = 35;// �������
	private int row = 15; // ����
	private int col = 15;// ����

	int chessCount;// ������������
	int xSite, ySite;// �µ����ӵ�λ��
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16������

	boolean flag;
	boolean gameover = false;

	Image background;// ���̱���
	Image chessWhite;// ����
	Image chessBlack;// ����

	public ChessBoard() { // ���̺�����ͼƬ��ȡ
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
		this.flag = flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void paintComponent(Graphics g) {// �������̺�����

		int backgroundWidth = background.getWidth(this);
		int backgroundHeight = background.getHeight(this);
		g.drawImage(background, 0, 0, null);

		for (int i = 0; i <= row; i++) { // ������
			g.drawLine(backGauge, backGauge + i * gridDistance, backGauge + col * gridDistance,
					backGauge + i * gridDistance);
		}
		for (int i = 0; i <= col; i++) { // ������
			g.drawLine(backGauge + i * gridDistance, backGauge, backGauge + i * gridDistance,
					backGauge + row * gridDistance);
		}

		for (int i = 0; i < chessCount; i++) { // ������

			int xPoint = chessSite[i].getX() * gridDistance + backGauge;
			int yPoint = chessSite[i].getY() * gridDistance + backGauge;

			g.setColor(chessSite[i].getColor());
			if (chessSite[i].getColor() == Color.BLACK) {
				g.drawImage(chessBlack, xPoint - 15, yPoint - 15, null);

			} else if (chessSite[i].getColor() == Color.WHITE) {
				g.drawImage(chessWhite, xPoint - 15, yPoint - 15, null);
			}
			this.repaint();
		}

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (gameover) {
			return;
		}

		String whetherColor = null;// ������ɫ

		if (flag == true) {
			whetherColor = "����";
		} else if (flag == false) {
			whetherColor = "����";
		}

		// �������λ��
		xSite = (e.getX() - backGauge + gridDistance / 2) / gridDistance;
		ySite = (e.getY() - backGauge + gridDistance / 2) / gridDistance;

		// ����֮�ⲻ����
		if (xSite < 0 || xSite > row || ySite < 0 || ySite > col) {
			return;
		}

		// ������λ�������Ӳ�����
		if (whetherPlay(xSite, ySite)) {
			return;
		}

		// �����µ����
		Site chess = new Site(xSite, ySite, flag ? Color.BLACK : Color.WHITE);
		chessSite[chessCount++] = chess;
		this.repaint();

		if (whetherWin(xSite, ySite)) {// ��Ϸʤ������
			String msg = String.format("��ϲ��%sӮ�ˣ�", whetherColor);
			JOptionPane.showMessageDialog(this, msg);
			gameover = true;
		}

		// ������ɫת��
		flag = !flag;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private boolean whetherPlay(int x, int y) { // �ж�λ���Ƿ��������
		for (Site c : chessSite) {
			if (c != null && c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}

	private Site getChessCount(int xSite, int ySite, Color color) {// ��ȡ���ӵ�λ�ú���ɫ
		for (Site p : chessSite) {
			if (p != null && p.getX() == xSite && p.getY() == ySite && p.getColor() == color) {
				return p;
			}
		}
		return null;
	}

	private boolean whetherWin(int Site, int ySite) {// �ж��Ƿ�Ӯ��
		int continuousChess = 1;

		// �����ж�
		for (int x = xSite - 1; x >= 0; x--) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}

			if (getChessCount(x, ySite, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}

		}

		// �����ж�
		for (int x = xSite + 1; x <= col; x++) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(x, ySite, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}
		if (continuousChess >= 5) {
			return true;
		} else {
			continuousChess = 1;
		}

		// �����ж�

		for (int y = ySite - 1; y >= 0; y--) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(xSite, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}

		// �����ж�

		for (int y = ySite + 1; y <= row; y++) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(xSite, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}
		if (continuousChess >= 5) {
			return true;
		} else {
			continuousChess = 1;
		}

		// �������ж�

		for (int x = xSite + 1, y = ySite - 1; y >= 0 && x <= col; x++, y--) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(x, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}

		// �������ж�

		for (int x = xSite - 1, y = ySite + 1; x >= 0 && y <= row; x--, y++) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(x, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}

		if (continuousChess >= 5) {
			return true;
		} else {
			continuousChess = 1;
		}

		// �������ж�

		for (int x = xSite + 1, y = ySite + 1; x <= col && y <= row; x++, y++) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(x, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}

		// �������ж�

		for (int x = xSite - 1, y = ySite - 1; x >= 0 && y >= 0; x--, y--) {
			Color colorSame;
			if (flag == true) {
				colorSame = Color.BLACK;
			} else {
				colorSame = Color.WHITE;
			}
			if (getChessCount(x, y, colorSame) != null) {
				continuousChess++;
			} else {
				break;
			}
		}

		// ���continuousChess=5��Ϸ����
		if (continuousChess >= 5) {
			return true;
		} else {
			continuousChess = 1;
		}

		return false;
	}

	public void Restart() { // ���¿�ʼ
		for (int i = 0; i < chessSite.length; i++) {// �����Ӽ�¼����Ϣȫ����Ϊ��
			chessSite[i] = null;
		}
		flag = true;
		gameover = false;
		chessCount = 0;
		this.repaint();
	}

	public void Back() {// ����
		if (chessCount == 0) {// ��������û������ʱ��������ʾ��
			JOptionPane.showMessageDialog(null, "������û�����ӣ��޷�����");
			return;
		}
		chessSite[chessCount - 1] = null;// ��һ����������Ϊnull
		chessCount--;// ���ӵ�������һ
		flag = !flag;// ������Ȩ����һ��
		this.repaint();
	}
}

/**
 * The Class Doublehuman_game.
 *
 * @date 2020��7��3��
 * @author nieming
 * @version  v1.0
 */
public class Doublehuman_game extends JFrame {
	
	/** The chess board. */
	private ChessBoard chessBoard;
	
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

	/** The btn restart. */
	private JButton btnRestart;// ��Ϸ���¿�ʼ
	
	/** The btn back. */
	private JButton btnBack;// ��Ϸ����
	
	/** The btn goback. */
	private JButton btnGoback;// ����
	
	/** The btn white. */
	private JButton btnWhite;
	
	/** The btn black. */
	private JButton btnBlack;

	/** The menu file. */
	private JMenu menuFile;
	
	/** The menu item restart. */
	private JMenuItem menuItemRestart;
	
	/** The menu item back. */
	private JMenuItem menuItemBack;
	
	/** The bar. */
	private JMenuBar bar;

	/**
	 * Instantiates a new doublehuman game.
	 */
	public Doublehuman_game() {
		frame = new JFrame("˫��ģʽ");
		frame.setLayout(null);
		frame.setSize(1000, 730);
		frame.setLocation(300, 10);
		frame.setResizable(false);

		chessBoard = new ChessBoard();
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
		btnRestart.setBounds(470, 620, 100, 30);
		frame.add(btnRestart);

		btnBack = new JButton("����");
		btnBack.setBounds(600, 620, 70, 30);
		frame.add(btnBack);

		btnGoback = new JButton("����");
		btnGoback.setBounds(700, 620, 70, 30);
		frame.add(btnGoback);

		btnWhite = new JButton("��������");
		btnWhite.setBounds(350, 620, 90, 30);
		frame.add(btnWhite);

		btnBlack = new JButton("��������");
		btnBlack.setBounds(230, 620, 90, 30);
		frame.add(btnBlack);

		
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

		btnWhite.addActionListener(new ActionListener() { // ���ع��ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.chessCount == 0)
					chessBoard.setFlag(false);

			}

		});

		btnBlack.addActionListener(new ActionListener() { // ���ع��ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.chessCount == 0)
					chessBoard.setFlag(true);
//				System.out.println(chessBoard.chessCount);
//				for (int i = 0; i < chessBoard.chessCount; i++) {
//					System.out.println(chessBoard.chessSite[i].getX());
//					System.out.println(chessBoard.chessSite[i].getY());
//					System.out.println(chessBoard.chessSite[i].getColor());
//				}
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

	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Doublehuman_game();
	}

}
