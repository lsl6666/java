package gobang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class onlineChessBoard.
 *
 * @date 2020年7月3日
 * @author nieming
 * @version  v1.0
 */
public class onlineChessBoard extends JPanel implements MouseListener {
	
	/** The mb. */
	private Network_online_game mb;

	/** The back gauge. */
	private int backGauge = 37; // 边距
	
	/** The grid distance. */
	private int gridDistance = 35;// 网格距离
	
	/** The row. */
	private int row = 15; // 行数
	
	/** The col. */
	private int col = 15;// 列数

	/** The chess count. */
	int chessCount;// 棋盘棋子数量
	
	/** The y site. */
	int xSite, ySite;// 下的棋子的位置
	
	/** The chess site. */
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16的棋盘

	/** The background. */
	Image background;// 棋盘背景
	
	/** The chess white. */
	Image chessWhite;// 白棋
	
	/** The chess black. */
	Image chessBlack;// 黑棋

	/** The flag. */
	boolean flag;
	
	/** The gameover. */
	boolean gameover = false;

	/**
	 * Instantiates a new online chess board.
	 *
	 * @param mb the mb
	 */
	public onlineChessBoard(Network_online_game mb) { // 棋盘和棋子图片获取
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
		this.mb = mb;
	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param g
	    * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	    */
	    
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

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mousePressed(MouseEvent e) {
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

		xSite = (e.getX() - backGauge + gridDistance / 2) / gridDistance;
		ySite = (e.getY() - backGauge + gridDistance / 2) / gridDistance;

		// System.out.println(xSite+","+ySite);

		// 棋盘之外不能下
		if (xSite < 0 || xSite > row || ySite < 0 || ySite > col) {
			return;
		}

		// 如果点击位置有棋子不能下
		if (whetherPlay(xSite, ySite)) {
			return;
		}

		// 可以下的情况
//		Site chess=new Site();

		Site chess = new Site(xSite, ySite, flag ? Color.BLACK : Color.WHITE);
		chessSite[chessCount++] = chess;
		// System.out.println(chess.getX() + "," + chess.getY() + "," +
		// chess.getColor());
		System.out.println(mb.getIp());
		NetLink.sendUDPBroadCast(mb.getIp(), "POS" + "," + xSite + "," + ySite + "," + flag);

		this.repaint();

		if (whetherWin(xSite, ySite)) {// 游戏胜利结束
			String msg = String.format("恭喜，%s赢了！", whetherColor);
			JOptionPane.showMessageDialog(this, msg);
			gameover = true;
		}

		// 棋子颜色转换
		flag = !flag;
		this.repaint();
		// System.out.println(xSite + "," + ySite);
	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

//	public void set(int xSite, int ySite) {
//		Site chess = new Site(xSite, ySite, flag ? Color.BLACK : Color.WHITE);
//		chessSite[chessCount++] = chess;
//	}

	/**
 * Whether play.
 *
 * @param x the x
 * @param y the y
 * @return true, if successful
 */
private boolean whetherPlay(int x, int y) { // 判断位置是否可以下棋
		for (Site c : chessSite) {
			if (c != null && c.getX() == x && c.getY() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the chess count.
	 *
	 * @param xSite the x site
	 * @param ySite the y site
	 * @param color the color
	 * @return the chess count
	 */
	private Site getChessCount(int xSite, int ySite, Color color) {// 获取棋子的位置和颜色
		for (Site p : chessSite) {
			if (p != null && p.getX() == xSite && p.getY() == ySite && p.getColor() == color) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Whether win.
	 *
	 * @param Site the site
	 * @param ySite the y site
	 * @return true, if successful
	 */
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

	/**
	 * Restart.
	 */
	public void Restart() { // 重新开始
		for (int i = 0; i < chessSite.length; i++) {// 将棋子记录的信息全部置为空
			chessSite[i] = null;
		}
		flag = true;
		gameover = false;
		chessCount = 0;
		this.repaint();
	}

	/**
	 * Back.
	 */
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

	/**
	 * Sets the.
	 *
	 * @param x the x
	 * @param y the y
	 * @param flag the flag
	 */
	public void set(int x,int y,boolean flag) {
//		Color colorSame;
//		if (flag == true) {
//			colorSame = Color.BLACK;
//		} else {
//			colorSame = Color.WHITE;
//		}
		Site chess = new Site(x, y, flag ? Color.BLACK : Color.WHITE);
		chessSite[chessCount++] = chess;
		
	}
}
