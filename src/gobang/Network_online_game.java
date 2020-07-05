package gobang;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Network_online_game.
 *
 * @date 2020��7��3��
 * @author nieming
 * @version  v1.0
 */
public class Network_online_game extends JFrame {

	/** The frame. */
	private JFrame frame;
	
	/** The chess board. */
	private onlineChessBoard chessBoard;
	
	/** The info. */
	private JLabel info;
	
	/** The info 2. */
	private JLabel info2;
	
	/** The btn prepare. */
	private JButton btnPrepare;
	
	/** The btn restart. */
	private JButton btnRestart;// ��Ϸ���¿�ʼ
	
	/** The btn back. */
	private JButton btnBack;// ��Ϸ����
	
	/** The btn goback. */
	private JButton btnGoback;// ����
	
	/** The btn send. */
	private JButton btnSend;
	
	/** The font. */
	private Font font;

	/** The input IP. */
	private JTextField inputIP;// ����ip��
	
	/** The talk field. */
	private JTextField talkField; // �����ı���
	
	/** The talk area. */
	private JTextArea talkArea;

	/** The menu file. */
	private JMenu menuFile;
	
	/** The menu item restart. */
	private JMenuItem menuItemRestart;
	
	/** The menu item back. */
	private JMenuItem menuItemBack;
	
	/** The menu item prepare. */
	private JMenuItem menuItemPrepare;
	
	/** The bar. */
	private JMenuBar bar;

	/** The socket. */
	private DatagramSocket socket;
	
	/** The ip. */
	private String ip;

	/** The game state. */
	private String gameState;
	
	/** The enemy game state. */
	private String enemyGameState;// ����״̬

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Instantiates a new network online game.
	 */
	public Network_online_game() {
		frame = new JFrame("����ģʽ");
		frame.setLayout(null);
		frame.setSize(800, 660);
		frame.setLocation(300, 10);
		frame.setResizable(false);

		chessBoard = new onlineChessBoard(this);
		chessBoard.setBounds(0, 0, 600, 600);
		frame.add(chessBoard);

		info = new JLabel("���������ip��ַ");
		Font font = new Font("����", Font.BOLD, 18);
		info.setFont(font);
		info.setBounds(620, 20, 170, 30);
		frame.add(info);

		info2 = new JLabel("����");
		Font font2 = new Font("����", Font.BOLD, 18);
		info2.setFont(font);
		info2.setBounds(680, 300, 50, 30);
		frame.add(info2);

		talkField = new JTextField("");
		talkField.setBounds(620, 520, 150, 30);
		frame.add(talkField);

		inputIP = new JTextField("������ip��ַ");
		inputIP.setBounds(620, 50, 150, 30);
		frame.add(inputIP);

		btnPrepare = new JButton("׼����Ϸ");
		btnPrepare.setBounds(620, 100, 150, 40);
		frame.add(btnPrepare);

		btnRestart = new JButton("���¿�ʼ");
		btnRestart.setBounds(620, 150, 150, 40);
		frame.add(btnRestart);

		btnBack = new JButton("����");
		btnBack.setBounds(620, 200, 150, 40);
		frame.add(btnBack);

		btnGoback = new JButton("����");
		btnGoback.setBounds(620, 250, 150, 40);
		frame.add(btnGoback);

		talkArea = new JTextArea(); // ������Ϣ
		talkArea.setEnabled(false);
		talkArea.setBackground(Color.WHITE);
		JScrollPane p = new JScrollPane(talkArea);
		p.setBounds(620, 330, 150, 180);
		frame.add(p);

		btnSend = new JButton("����");
		btnSend.setBounds(660, 560, 70, 30);
		frame.add(btnSend);

		btnPrepare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!inputIP.getText().isEmpty() && !inputIP.getText().equals("����Ϊ��")) {
					ip = inputIP.getText();
					btnPrepare.setEnabled(false);
					// btnPrepare.setText("�ȴ��Է�׼��");
					inputIP.setEditable(false);
					// ����׼������Ϣ
					NetLink.sendUDPBroadCast(ip, "ready, ");
					gameState = "ready";
					if (enemyGameState == "ready") {
						gameState = "FIGHTING";
						btnPrepare.setText("������Ϸ");
					}
				} else {
					inputIP.setText("����Ϊ��");
				}
			}

		});

		btnBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NetLink.sendUDPBroadCast(ip, "back" + ", ");
			}

		});

		btnRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NetLink.sendUDPBroadCast(ip, "restart" + ", ");
			}

		});

		btnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!talkField.getText().isEmpty() && !talkField.getText().equals("����Ϊ��")) {
					// ������������
					String msg = talkField.getText();
					talkArea.append("�ң�" + msg + "\n");
					talkField.setText("");
					ip = inputIP.getText();
					NetLink.sendUDPBroadCast(ip, "enemy" + "," + msg);
				} else {
					talkField.setText("����Ϊ��");
				}
			}

		});

		btnGoback.addActionListener(new ActionListener() {

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
		menuItemPrepare = new JMenuItem("׼����Ϸ");

		frame.setJMenuBar(bar);
		bar.add(menuFile);
		menuFile.add(menuItemPrepare);
		menuFile.add(menuItemRestart);
		menuFile.add(menuItemBack);

		ReceiveThread();
		frame.repaint();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Receive thread.
	 */
	public void ReceiveThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					byte buf[] = new byte[1024];
					socket = new DatagramSocket(2015);
					DatagramPacket dp = new DatagramPacket(buf, buf.length);
					while (true) {
						socket.receive(dp);
						InetAddress ia = dp.getAddress();
						System.out.println("����IP��" + ia.getHostName());
						String data = new String(dp.getData(), 0, dp.getLength());
						String[] msg = data.split(",");
						if (msg[0].equals("ready")) {
							enemyGameState = "ready";
							if (gameState.equals("ready")) {
								gameState = "FIGHTING";
							}
						} else if (msg[0].equals("POS")) {
							System.out.println("��������");
						//	System.out.println(Integer.parseInt(msg[1])+Integer.parseInt(msg[2])+Integer.parseInt(msg[3]));
							chessBoard.set(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]),Boolean.parseBoolean(msg[3]));
						} else if (msg[0].equals("enemy")) {
							talkArea.append("���֣�" + msg[1] + "\n");
						} else if (msg[0].equals("restart")) {
							chessBoard.Restart();
						} else if (msg[0].equals("back")) {

							int n = JOptionPane.showConfirmDialog(chessBoard, "�Ƿ�ͬ��Է�����", "ѡ��",
									JOptionPane.YES_NO_OPTION);
							// ���ȷ����ť����Ի���
							if (n == JOptionPane.YES_OPTION) {
								// chessBoard.Back();
								NetLink.sendUDPBroadCast(ia.getHostName(), "canBack" + ", ");
							} else {
								NetLink.sendUDPBroadCast(ia.getHostName(), "noBack" + ", ");
							}

						} else if (msg[0].equals("canBack")) {
							JOptionPane.showMessageDialog(chessBoard, "�Է�����������");
							chessBoard.Back();
						}
						// ���������
						else if (msg[0].equals("noBack")) {
							JOptionPane.showMessageDialog(chessBoard, "�Է�������������");
						}

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Network_online_game();
	}

}
