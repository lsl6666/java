package gobang;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
// TODO: Auto-generated Javadoc

/**
 * The Class Explain.
 *
 * @date 2020��7��3��
 * @author nieming
 * @version  v1.0
 */
public class Explain extends JFrame{

	
	/** The frame. */
	private JFrame frame;
	
	/** The btn goback. */
	private JButton btnGoback;//����
	
	/** The font. */
	private Font font;
	
	/** The info. */
	private JLabel info;
	
	/** The machin info. */
	private JLabel machinInfo;
	
	/** The double info. */
	private JLabel doubleInfo;
	
	/** The double info 1. */
	private JLabel doubleInfo1;
	
	/** The online info. */
	private JLabel onlineInfo;
	
	/** The online info 1. */
	private JLabel onlineInfo1;
	
	/**
	 * Inits the.
	 */
	public void init() {
		frame = new JFrame("��Ϸ˵��");
		frame.setLayout(null);
		
		info = new JLabel("ģʽ˵��");
		Font font = new Font("����", Font.BOLD, 50);
		info.setFont(font);
		info.setBounds(400, 10, 300, 60);
		frame.add(info);
		
		machinInfo = new JLabel("�˻�ģʽ��Ĭ�Ϻ������֣�����Ϊ����");
		Font font1 = new Font("����", Font.BOLD, 20);
		machinInfo.setFont(font1);
		machinInfo.setBounds(50, 80, 900, 60);
		frame.add(machinInfo);
		
		doubleInfo = new JLabel("˫��ģʽ������������ѡ��ť����ѡ������������ɫ�����¿�ʼ��ť����Ϊ������̣��л���");
		doubleInfo.setFont(font1);
		doubleInfo.setBounds(50, 140, 960, 60);
		frame.add(doubleInfo);
		
		doubleInfo1 = new JLabel("���ܣ�������عر�˫��ģʽ������ģʽѡ�����");
		doubleInfo1.setFont(font1);
		doubleInfo1.setBounds(50, 190, 960, 60);
		frame.add(doubleInfo1);
		
		onlineInfo = new JLabel("����ģʽ�����ͨ��ip��ַ���������û��õ����õ����顣�����п���ʵ�����콻�������Ի��壬");
		onlineInfo.setFont(font1);
		onlineInfo.setBounds(50, 250, 960, 60);
		frame.add(onlineInfo);
		
		onlineInfo1 = new JLabel("�������¿�ʼ��Ϸ");
		onlineInfo1.setFont(font1);
		onlineInfo1.setBounds(50, 300, 960, 60);
		frame.add(onlineInfo1);
		
		btnGoback=new JButton("����");
		btnGoback.setBounds(800,620,70,30);
		frame.add(btnGoback);
		
		frame.setSize(1000, 730);
		frame.setLocation(300, 10);
		frame.setResizable(false);
		
		btnGoback.addActionListener(new ActionListener() { // ���ع��ܵİ�ťʵ��

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();

			}

		});
		
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		new Explain().init();
//	}

}
