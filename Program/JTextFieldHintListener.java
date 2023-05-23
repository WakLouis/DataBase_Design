
/*
 * @Descripttion: 
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 12:50:16
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-19 13:51:53
 */

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

class JTextFieldHintListener implements FocusListener {
	private String hintText;
	private JTextField textField;

	public JTextFieldHintListener(JTextField jTextField, String hintText) {
		this.textField = jTextField;
		this.hintText = hintText;
		jTextField.setText(hintText); // 默认直接显示
		jTextField.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// 获取焦点时，清空提示内容
		String temp = textField.getText();
		if (temp.equals(hintText)) {
			textField.setText("");
			textField.setForeground(Color.BLACK);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		// 失去焦点时，没有输入内容，显示提示内容
		String temp = textField.getText();
		if (temp.equals("")) {
			textField.setForeground(Color.GRAY);
			textField.setText(hintText);
		}

	}

}

class JTextAreaHintListener implements FocusListener {
	private String hintText;
	private JTextArea textArea;

	public JTextAreaHintListener(JTextArea textArea, String hintText) {
		this.textArea = textArea;
		this.hintText = hintText;
		textArea.setText(hintText); // 默认直接显示
		textArea.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// 获取焦点时，清空提示内容
		String temp = textArea.getText();
		if (temp.equals(hintText)) {
			textArea.setText("");
			textArea.setForeground(Color.BLACK);
		}

	}

	@Override
	public void focusLost(FocusEvent e) {
		// 失去焦点时，没有输入内容，显示提示内容
		String temp = textArea.getText();
		if (temp.equals("")) {
			textArea.setForeground(Color.GRAY);
			textArea.setText(hintText);
		}

	}
}
