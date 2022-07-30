package Test;
 
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
 
public class P2 extends JPanel {
 
	/**
	 * Create the panel.
	 */
	public P2() {
		setLayout(null);
		JLabel lblNewLabel = new JLabel("我是页面2  (*^▽^*)");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 24));
		lblNewLabel.setBounds(23, 10, 245, 94);
		add(lblNewLabel);
		
		JTextField jt2 = new JTextField();
		jt2.setBounds(42, 114, 206, 21);
		add(jt2);
		jt2.setColumns(10);
		
		JButton btnNewButton = new JButton("确定");
		btnNewButton.setBounds(42, 145, 97, 23);
		add(btnNewButton);
		
	}
}