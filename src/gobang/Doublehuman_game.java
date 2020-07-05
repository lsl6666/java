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

	private int backGauge = 37; // 边距
	private int gridDistance = 35;// 网格距离
	private int row = 15; // 行数
	private int col = 15;// 列数

	int chessCount;// 棋盘棋子数量
	int xSite, ySite;// 下的棋子的位置
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16的棋盘

	boolean flag;
	boolean gameover = false;

	Image background;// 棋盘背景
	Image chessWhite;// 白棋
	Image chessBlack;// 黑棋

	public ChessBoard() { // 棋盘和棋子图片获取
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
		this.flag = flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void paintComponent(Graphics g) {// 绘制棋盘和棋子

		int backgroundWidth = background.getWidth(this);
		int backgroundHeight = background.getHeight(this);
		g.drawImage(background, 0, 0, null);

		for (int i = 0; i <= row; i++) { // 画行线
			g.drawLine(backGauge, backGauge + i * gridDistance, backGauge + col * gridDistance,
					backGauge + i * gridDistance);
		}
		for (int i = 0; i <= col; i++) { // 画列线
			g.drawLine(backGauge + i * gridDistance, backGauge, backGauge + i * gridDistance,
					backGauge + row * gridDistance);
		}

		for (int i = 0; i < chessCount; i++) { // 画棋子

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

		String whetherColor = null;// 棋子颜色

		if (flag == true) {
			whetherColor = "黑棋";
		} else if (flag == false) {
			whetherColor = "白棋";
		}

		// 鼠标点击的位置
		xSite = (e.getX() - backGauge + gridDistance / 2) / gridDistance;
		ySite = (e.getY() - backGauge + gridDistance / 2) / gridDistance;

		// 棋盘之外不能下
		if (xSite < 0 || xSite > row || ySite < 0 || ySite > col) {
			return;
		}

		// 如果点击位置有棋子不能下
		if (whetherPlay(xSite, ySite)) {
			return;
		}

		// 可以下的情况
		Site chess = new Site(xSite, ySite, flag ? Color.BLACK : Color.WHITE);
		chessSite[chessCount++] = chess;
		this.repaint();

		if (whetherWin(xSite, ySite)) {// 游戏胜利结束
			String msg = String.format("恭喜，%s赢了！", whetherColor);
			JOptionPane.showMessageDialog(this, msg);
			gameover = true;
		}

		// 棋子颜色转换
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

	private boolean whetherPlay(int x, int y) { // 判断位置是否可以下棋
		for (Site c : chessSite) {
			if (c != null && c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}

	private Site getChessCount(int xSite, int ySite, Color color) {// 获取棋子的位置和颜色
		for (Site p : chessSite) {
			if (p != null && p.getX() == xSite && p.getY() == ySite && p.getColor() == color) {
				return p;
			}
		}
		return null;
	}

	private boolean whetherWin(int Site, int ySite) {// 判断是否赢了
		int continuousChess = 1;

		// 向左判断
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

		// 向右判断
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

		// 向上判断

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

		// 向下判断

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

		// 向右上判断

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

		// 向左下判断

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

		// 向右下判断

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

		// 向左上判断

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

		// 如果continuousChess=5游戏结束
		if (continuousChess >= 5) {
			return true;
		} else {
			continuousChess = 1;
		}

		return false;
	}

	public void Restart() { // 重新开始
		for (int i = 0; i < chessSite.length; i++) {// 将棋子记录的信息全部置为空
			chessSite[i] = null;
		}
		flag = true;
		gameover = false;
		chessCount = 0;
		this.repaint();
	}

	public void Back() {// 悔棋
		if (chessCount == 0) {// 当棋盘中没有棋子时，弹出提示框
			JOptionPane.showMessageDialog(null, "棋盘中没有棋子，无法悔棋");
			return;
		}
		chessSite[chessCount - 1] = null;// 上一个棋子重置为null
		chessCount--;// 棋子的数量减一
		flag = !flag;// 将下棋权给另一方
		this.repaint();
	}
}

/**
 * The Class Doublehuman_game.
 *
 * @date 2020年7月3日
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
	private JButton btnRestart;// 游戏重新开始
	
	/** The btn back. */
	private JButton btnBack;// 游戏悔棋
	
	/** The btn goback. */
	private JButton btnGoback;// 返回
	
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
		frame = new JFrame("双人模式");
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

		btnRestart = new JButton("重新开始");
		btnRestart.setBounds(470, 620, 100, 30);
		frame.add(btnRestart);

		btnBack = new JButton("悔棋");
		btnBack.setBounds(600, 620, 70, 30);
		frame.add(btnBack);

		btnGoback = new JButton("返回");
		btnGoback.setBounds(700, 620, 70, 30);
		frame.add(btnGoback);

		btnWhite = new JButton("白子先走");
		btnWhite.setBounds(350, 620, 90, 30);
		frame.add(btnWhite);

		btnBlack = new JButton("黑子先走");
		btnBlack.setBounds(230, 620, 90, 30);
		frame.add(btnBlack);

		
		btnBack.addActionListener(new ActionListener() { // 悔棋功能的按钮实现

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.gameover == false) {// 游戏结束就不能悔棋了
					chessBoard.Back();
				}

			}

		});

		btnGoback.addActionListener(new ActionListener() { // 返回功能的按钮实现

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();

			}

		});

		btnWhite.addActionListener(new ActionListener() { // 返回功能的按钮实现

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (chessBoard.chessCount == 0)
					chessBoard.setFlag(false);

			}

		});

		btnBlack.addActionListener(new ActionListener() { // 返回功能的按钮实现

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
		menuFile = new JMenu("菜单");
		menuItemRestart = new JMenuItem("重新开始");
		menuItemBack = new JMenuItem("悔棋");

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
				if (chessBoard.gameover == false) {// 游戏结束就不能悔棋了
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
