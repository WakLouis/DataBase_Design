/*
 * @Descripttion: 
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-26 13:54:23
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-16 16:52:50
 */

package Test;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Jrame1 extends JFrame {

	private JPanel contentPane;
	CardLayout cardLayout = new CardLayout();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jrame1 frame = new Jrame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Jrame1() {
		setLayout(null);
		setTitle("demo—同一个窗口不同面板切换");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBounds(0, 10, 200, 200);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		// contentPane.setBounds(0,0,200,200);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(60, 10, 300, 200);
		contentPane.add(panel);
		// 给主要显示面板添加布局方式
		panel.setLayout(cardLayout);
		// 创建相应面板类的对象
		P1 p1 = new P1();
		// 将面板添加到住面板中，注意:add()方法里有两个参数，第一个是要添加的对象，第二个给这个对象所放置的卡片
		// 起个名字，后面调用显示的时候要用到这个名字
		panel.add(p1, "p1");
		P2 p2 = new P2();
		panel.add(p2, "p2");

		panel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));

		JButton btnNewButton = new JButton("页面1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 这里是点击按钮切换不同的页面
				// cardLayout.show(参数 1，参数2)方法里面也有两个参数，
				// 参数1是表示指明你要在哪个容器上显示，
				// 参数2是指明要显示哪个卡片，即你要给出对应卡片的名字
				cardLayout.show(panel, "p1");
			}
		});
		btnNewButton.setBounds(70, 220, 97, 23);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("页面2");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 同上
				cardLayout.show(panel, "p2");
			}
		});
		btnNewButton_1.setBounds(251, 220, 97, 23);
		contentPane.add(btnNewButton_1);
	}
}