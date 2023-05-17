/*
 * @Descripttion: 邮件系统线程，目的是周期性从数据库中获取邮件信息
 * @version: v0.1
 * @Author: WakLouis
 * @Date: 2023-05-17 11:00:01
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-17 13:10:58
 */

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

class MailIconButton extends JButton {

    public MailIconButton(int WIDTH, int HEIGHT, String iconPath) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        ImageIcon icon = new ImageIcon(iconPath);
        Image temp = icon.getImage().getScaledInstance(WIDTH, HEIGHT, icon.getImage().SCALE_DEFAULT);
        icon = new ImageIcon(temp);
        setIcon(icon);
        setSize(WIDTH, HEIGHT);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

        });
    }
}

public class MailSystem extends Thread {
    public void run() {

    }
}