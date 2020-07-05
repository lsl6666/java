package gobang;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Random;
import dao.dao;
import dao.MyConnection;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Machine_chessBoard extends JPanel implements MouseListener {

	private int backGauge = 30; // 边距
	private int gridDistance = 35;// 网格距离
	private int row = 15; // 行数
	private int col = 15;// 列数

	int chessCount;// 棋盘棋子数量
	int xSite, ySite;// 下的棋子的位置
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16的棋盘

	boolean flag = true;
	boolean gameover = false;

	Image background;// 棋盘背景
	Image chessWhite;// 白棋
	Image chessBlack;// 黑棋

	public Machine_chessBoard() { // 棋盘和棋子图片获取
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {// 绘制棋盘和棋子

		int backgroundWidth = background.getWidth(this);
		int backgroundHeight = background.getHeight(this);
		// System.out.println(backgroundWidth);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (gameover) {
			return;
		}

		String whetherColor = null;// 棋子颜色

		if (flag == true) {
			whetherColor = "黑棋";
			xSite = (e.getX() - backGauge + gridDistance / 2) / gridDistance;
			ySite = (e.getY() - backGauge + gridDistance / 2) / gridDistance;
			try {
				Robot robot = new Robot();
				robot.mousePress(InputEvent.BUTTON1_MASK);
				robot.mouseRelease(InputEvent.BUTTON1_MASK);
			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (flag == false) {
			whetherColor = "白棋";
			Random random = new Random();
			xSite = random.nextInt(16);
			ySite = random.nextInt(16);
			while (whetherPlay(xSite, ySite)) {
				xSite = random.nextInt(16);
				ySite = random.nextInt(16);
			}
		}

		// 鼠标点击的位置

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

			if (whetherColor == "黑棋") {
				dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
				try {
					String name = sqlInfo.queryFlag();
					// System.out.println(a);
					sqlInfo.updateWinInfo(name);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				
//				System.out.println(name);
			} else if (whetherColor == "白棋") {
				dao sqlInfo = new dao(MyConnection.driverName, MyConnection.uri);
				String name;
				try {
					name = sqlInfo.queryFlag();
					sqlInfo.updateLoseInfo(name);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
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

	private boolean whetherWin(int xSite, int ySite) {// 判断是否赢了
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
