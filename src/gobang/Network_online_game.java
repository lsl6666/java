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
 * @date 2020年7月3日
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
	private JButton btnRestart;// 游戏重新开始
	
	/** The btn back. */
	private JButton btnBack;// 游戏悔棋
	
	/** The btn goback. */
	private JButton btnGoback;// 返回
	
	/** The btn send. */
	private JButton btnSend;
	
	/** The font. */
	private Font font;

	/** The input IP. */
	private JTextField inputIP;// 输入ip框
	
	/** The talk field. */
	private JTextField talkField; // 聊天文本框
	
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
	private String enemyGameState;// 敌人状态

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
		frame = new JFrame("联机模式");
		frame.setLayout(null);
		frame.setSize(800, 660);
		frame.setLocation(300, 10);
		frame.setResizable(false);

		chessBoard = new onlineChessBoard(this);
		chessBoard.setBounds(0, 0, 600, 600);
		frame.add(chessBoard);

		info = new JLabel("请输入对手ip地址");
		Font font = new Font("宋体", Font.BOLD, 18);
		info.setFont(font);
		info.setBounds(620, 20, 170, 30);
		frame.add(info);

		info2 = new JLabel("聊天");
		Font font2 = new Font("宋体", Font.BOLD, 18);
		info2.setFont(font);
		info2.setBounds(680, 300, 50, 30);
		frame.add(info2);

		talkField = new JTextField("");
		talkField.setBounds(620, 520, 150, 30);
		frame.add(talkField);

		inputIP = new JTextField("请输入ip地址");
		inputIP.setBounds(620, 50, 150, 30);
		frame.add(inputIP);

		btnPrepare = new JButton("准备游戏");
		btnPrepare.setBounds(620, 100, 150, 40);
		frame.add(btnPrepare);

		btnRestart = new JButton("重新开始");
		btnRestart.setBounds(620, 150, 150, 40);
		frame.add(btnRestart);

		btnBack = new JButton("悔棋");
		btnBack.setBounds(620, 200, 150, 40);
		frame.add(btnBack);

		btnGoback = new JButton("返回");
		btnGoback.setBounds(620, 250, 150, 40);
		frame.add(btnGoback);

		talkArea = new JTextArea(); // 对弈信息
		talkArea.setEnabled(false);
		talkArea.setBackground(Color.WHITE);
		JScrollPane p = new JScrollPane(talkArea);
		p.setBounds(620, 330, 150, 180);
		frame.add(p);

		btnSend = new JButton("发送");
		btnSend.setBounds(660, 560, 70, 30);
		frame.add(btnSend);

		btnPrepare.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!inputIP.getText().isEmpty() && !inputIP.getText().equals("不能为空")) {
					ip = inputIP.getText();
					btnPrepare.setEnabled(false);
					// btnPrepare.setText("等待对方准备");
					inputIP.setEditable(false);
					// 发送准备好信息
					NetLink.sendUDPBroadCast(ip, "ready, ");
					gameState = "ready";
					if (enemyGameState == "ready") {
						gameState = "FIGHTING";
						btnPrepare.setText("正在游戏");
					}
				} else {
					inputIP.setText("不能为空");
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
				if (!talkField.getText().isEmpty() && !talkField.getText().equals("不能为空")) {
					// 获得输入的内容
					String msg = talkField.getText();
					talkArea.append("我：" + msg + "\n");
					talkField.setText("");
					ip = inputIP.getText();
					NetLink.sendUDPBroadCast(ip, "enemy" + "," + msg);
				} else {
					talkField.setText("不能为空");
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
		menuFile = new JMenu("菜单");
		menuItemRestart = new JMenuItem("重新开始");
		menuItemBack = new JMenuItem("悔棋");
		menuItemPrepare = new JMenuItem("准备游戏");

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
						System.out.println("对手IP：" + ia.getHostName());
						String data = new String(dp.getData(), 0, dp.getLength());
						String[] msg = data.split(",");
						if (msg[0].equals("ready")) {
							enemyGameState = "ready";
							if (gameState.equals("ready")) {
								gameState = "FIGHTING";
							}
						} else if (msg[0].equals("POS")) {
							System.out.println("发送坐标");
						//	System.out.println(Integer.parseInt(msg[1])+Integer.parseInt(msg[2])+Integer.parseInt(msg[3]));
							chessBoard.set(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]),Boolean.parseBoolean(msg[3]));
						} else if (msg[0].equals("enemy")) {
							talkArea.append("对手：" + msg[1] + "\n");
						} else if (msg[0].equals("restart")) {
							chessBoard.Restart();
						} else if (msg[0].equals("back")) {

							int n = JOptionPane.showConfirmDialog(chessBoard, "是否同意对方悔棋", "选择",
									JOptionPane.YES_NO_OPTION);
							// 点击确定按钮则可以悔棋
							if (n == JOptionPane.YES_OPTION) {
								// chessBoard.Back();
								NetLink.sendUDPBroadCast(ia.getHostName(), "canBack" + ", ");
							} else {
								NetLink.sendUDPBroadCast(ia.getHostName(), "noBack" + ", ");
							}

						} else if (msg[0].equals("canBack")) {
							JOptionPane.showMessageDialog(chessBoard, "对方允许您悔棋");
							chessBoard.Back();
						}
						// 不允许悔棋
						else if (msg[0].equals("noBack")) {
							JOptionPane.showMessageDialog(chessBoard, "对方不允许您悔棋");
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
