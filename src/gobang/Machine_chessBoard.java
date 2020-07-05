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

	private int backGauge = 30; // �߾�
	private int gridDistance = 35;// �������
	private int row = 15; // ����
	private int col = 15;// ����

	int chessCount;// ������������
	int xSite, ySite;// �µ����ӵ�λ��
	Site[] chessSite = new Site[(row + 1) * (col + 1)]; // 16*16������

	boolean flag = true;
	boolean gameover = false;

	Image background;// ���̱���
	Image chessWhite;// ����
	Image chessBlack;// ����

	public Machine_chessBoard() { // ���̺�����ͼƬ��ȡ
		background = Toolkit.getDefaultToolkit().getImage("src\\qipan.png");
		chessWhite = Toolkit.getDefaultToolkit().getImage("src\\chess1.png");
		chessBlack = Toolkit.getDefaultToolkit().getImage("src\\chess.png");
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {// �������̺�����

		int backgroundWidth = background.getWidth(this);
		int backgroundHeight = background.getHeight(this);
		// System.out.println(backgroundWidth);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		if (gameover) {
			return;
		}

		String whetherColor = null;// ������ɫ

		if (flag == true) {
			whetherColor = "����";
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
			whetherColor = "����";
			Random random = new Random();
			xSite = random.nextInt(16);
			ySite = random.nextInt(16);
			while (whetherPlay(xSite, ySite)) {
				xSite = random.nextInt(16);
				ySite = random.nextInt(16);
			}
		}

		// �������λ��

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

			if (whetherColor == "����") {
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
			} else if (whetherColor == "����") {
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

	private boolean whetherWin(int xSite, int ySite) {// �ж��Ƿ�Ӯ��
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
