package gobang;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

// TODO: Auto-generated Javadoc
/**
 * The Class choose.
 *
 * @date 2020��7��3��
 * @author nieming
 * @version  v1.0
 */
public class choose extends JFrame {
	
	/** The frame. */
	private JFrame frame;
	
	/** The pan north. */
	private JPanel panNorth;
	
	/** The pan south. */
	private JPanel panSouth;
	
	/** The pan center. */
	private JPanel panCenter;
	
	/** The info. */
	private JLabel info;
	
	/** The btn man machine. */
	private JButton btnMan_machine;
	
	/** The btn double game. */
	private JButton btnDouble_game;
	
	/** The btn forbidden hand. */
	private JButton btnForbidden_hand;
	
	/** The btn network online. */
	private JButton btnNetwork_online;
	
	/** The btn explain. */
	private JButton btnExplain;
	
	/** The btn win rate. */
	private JButton btnWin_rate;
	
	/** The font. */
	private Font font;
	
	/** The back img. */
	private Image backImg;

	/**
	 * Inits the.
	 */
	public void init() {
		frame = new JFrame("ѡ�����");
		info = new JLabel("����������");

		Font font = new Font("����", Font.BOLD, 50);
		info.setFont(font);
		btnMan_machine = new JButton("�˻�ģʽ");
		btnDouble_game = new JButton("˫��ģʽ");
		btnForbidden_hand = new JButton("����ģʽ");
		btnNetwork_online = new JButton("��������");
		btnExplain = new JButton("��Ϸ˵��");
		btnWin_rate = new JButton("ʤ��");

		panNorth = new JPanel();
		panSouth = new JPanel();
		panCenter = new JPanel();

		frame.setSize(540, 600);
		frame.setLocation(500, 100);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		panNorth.setLayout(new FlowLayout());
		frame.add(panNorth, BorderLayout.NORTH);
		panNorth.add(info);

		panSouth.setLayout(new FlowLayout());
		frame.add(panSouth, BorderLayout.SOUTH);
		panSouth.add(btnExplain);
		panSouth.add(btnWin_rate);

		panCenter.setLayout(null);
		frame.add(panCenter, BorderLayout.CENTER);
		btnMan_machine.setBounds(135, 60, 270, 60);
		panCenter.add(btnMan_machine);
		btnDouble_game.setBounds(135, 150, 270, 60);
		panCenter.add(btnDouble_game);
		btnForbidden_hand.setBounds(135, 240, 270, 60);
		panCenter.add(btnForbidden_hand);
		btnNetwork_online.setBounds(135, 330, 270, 60);
		panCenter.add(btnNetwork_online);

		btnDouble_game.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Doublehuman_game();
			}

		});

		btnMan_machine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Man_machine_game();
			}

		});
		
		btnNetwork_online.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Network_online_game();
			}

		});
		
		btnExplain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Explain().init();
			}

		});
		
		btnWin_rate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Win_rate().init();
			}

		});

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new choose().init();
//	}

}
