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
 * @date 2020��7��3��
 * @author nieming
 * @version  v1.0
 */
public class onlineChessBoard extends JPanel implements MouseListener {
	
	/** The mb. */
	private Network_online_game mb;

	/** The back gauge. */
	private int backGauge = 37; // �߾�
	
	/** The grid distance. */
	private int gridDistance = 35;// �������
	
	/** The row. */
	private int row = 15; // ����
	
	/** The col. */
	private int col = 15;// ����

	/** The chess count. */
	int chessCount;// ������������
	
	/** The y site. */
	int xSite, ySite;// �µ����ӵ�λ��
	
	/** The chess site. */
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16������

	/** The background. */
	Image background;// ���̱���
	
	/** The chess white. */
	Image chessWhite;// ����
	
	/** The chess black. */
	Image chessBlack;// ����

	/** The flag. */
	boolean flag;
	
	/** The gameover. */
	boolean gameover = false;

	/**
	 * Instantiates a new online chess board.
	 *
	 * @param mb the mb
	 */
	public onlineChessBoard(Network_online_game mb) { // ���̺�����ͼƬ��ȡ
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
		this.mb = mb;
	}

	
	    /* (�� Javadoc)
	    * 
	    * 
	    * @param g
	    * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	    */
	    
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

	
	    /* (�� Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (�� Javadoc)
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

		String whetherColor = null;// ������ɫ

		if (flag == true) {
			whetherColor = "����";
		} else if (flag == false) {
			whetherColor = "����";
		}

		xSite = (e.getX() - backGauge + gridDistance / 2) / gridDistance;
		ySite = (e.getY() - backGauge + gridDistance / 2) / gridDistance;

		// System.out.println(xSite+","+ySite);

		// ����֮�ⲻ����
		if (xSite < 0 || xSite > row || ySite < 0 || ySite > col) {
			return;
		}

		// ������λ�������Ӳ�����
		if (whetherPlay(xSite, ySite)) {
			return;
		}

		// �����µ����
//		Site chess=new Site();

		Site chess = new Site(xSite, ySite, flag ? Color.BLACK : Color.WHITE);
		chessSite[chessCount++] = chess;
		// System.out.println(chess.getX() + "," + chess.getY() + "," +
		// chess.getColor());
		System.out.println(mb.getIp());
		NetLink.sendUDPBroadCast(mb.getIp(), "POS" + "," + xSite + "," + ySite + "," + flag);

		this.repaint();

		if (whetherWin(xSite, ySite)) {// ��Ϸʤ������
			String msg = String.format("��ϲ��%sӮ�ˣ�", whetherColor);
			JOptionPane.showMessageDialog(this, msg);
			gameover = true;
		}

		// ������ɫת��
		flag = !flag;
		this.repaint();
		// System.out.println(xSite + "," + ySite);
	}

	
	    /* (�� Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (�� Javadoc)
	    * 
	    * 
	    * @param e
	    * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	    */
	    
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	
	    /* (�� Javadoc)
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
private boolean whetherPlay(int x, int y) { // �ж�λ���Ƿ��������
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
	private Site getChessCount(int xSite, int ySite, Color color) {// ��ȡ���ӵ�λ�ú���ɫ
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

	/**
	 * Restart.
	 */
	public void Restart() { // ���¿�ʼ
		for (int i = 0; i < chessSite.length; i++) {// �����Ӽ�¼����Ϣȫ����Ϊ��
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
