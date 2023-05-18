/*
 * @Descripttion: 邮件系统线程，目的是周期性从数据库中获取邮件信息
 * @version: v0.1
 * @Author: WakLouis
 * @Date: 2023-05-17 11:00:01
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-18 23:55:19
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class MailIconButton extends JButton {

    public MailIconButton(int WIDTH, int HEIGHT, String iconPath) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
        ImageIcon icon = new ImageIcon(iconPath);
        icon.getImage();
        Image temp = icon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
        icon = new ImageIcon(temp);
        setIcon(icon);
        setSize(WIDTH, HEIGHT);
        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.cardLayout.show(Main.contentPane, "mail");
            }
        });
    }
}

class mailPanel extends JPanel {
    // 滚动框
    static JScrollPane mailListJScrollPane;
    static JScrollPane contentJScrollPane;

    // 消息框
    static JTextArea displayArea;

    public mailPanel() {
        setVisible(true);
        setLayout(null);

        WelcomeText welcomeText = new WelcomeText();
        welcomeText.welcomeText();
        add(welcomeText);

        MailIconButton mailIconButton = new MailIconButton(25, 25, "./icon/mailIcon.png");
        mailIconButton.setLocation(740, 5);
        mailIconButton.setContentAreaFilled(false);
        mailIconButton.setFocusPainted(false);
        add(mailIconButton);

        mailListJScrollPane = new JScrollPane();
        contentJScrollPane = new JScrollPane();

        mailListJScrollPane.setBounds(0, 30, 200, 500);
        contentJScrollPane.setBounds(200, 30, 590, 500);

        displayArea = new JTextArea();
        displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        displayArea.setEditable(false);
        contentJScrollPane.setViewportView(displayArea);

        mailListJScrollPane.setName("mailListJScrollPane");
        contentJScrollPane.setName("contentJScrollPane");

        add(mailListJScrollPane);
        add(contentJScrollPane);
    }
}

class Mail {
    String senderID;
    String mailID;
    String receiverIDSet;
    Timestamp sendedTime;
    String title;
    String content;
}

class MailButton extends JButton {
    public Mail mail;
    public JTextArea displayArea;

    public MailButton(Mail mail) {
        this.mail = mail;
        if (this.mail.title != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");// 定义格式，不显示毫秒

            this.setText(
                    "<html>" + mail.title + "  ---  " + mail.senderID + "<br>" + df.format(mail.sendedTime)
                            + "</html>");
        } else {
            this.setText("标题为空");
        }
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mailPanel.displayArea.setText(mail.content);
            }

        });
    }
}

public class MailSystem extends Thread {
    ArrayList<Mail> mailList = new ArrayList<Mail>();
    static List<JScrollPane> mailPanels;

    private static List<JScrollPane> getTextFieldObject(Component component) {
        List<JScrollPane> result = new ArrayList<>();
        if (component instanceof JPanel && !(component instanceof JScrollPane)) {
            for (Component com : ((JPanel) component).getComponents()) {
                List<JScrollPane> textFields = getTextFieldObject(com);
                result.addAll(textFields);
            }
        } else {
            if (component instanceof JScrollPane) {
                result.add((JScrollPane) component);
            }
        }
        return result;
    }

    public void run() {
        // 周期向数据库抓取邮件
        while (true) {
            try {
                sleep(1000);
                mailList = Program.fGetMail();

            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            mailPanels = getTextFieldObject(Main.contentPane);
            JScrollPane temp = mailPanels.get(2);

            temp.setVisible(true);
            temp.setLayout(null);
            temp.removeAll();

            int num = 0, sep = 50;
            ArrayList<MailButton> mailButtons = new ArrayList<MailButton>();
            for (Mail mail : mailList) {
                MailButton mailButton = new MailButton(mail);
                mailButtons.add(mailButton);
                mailButtons.get(num).setBounds(0, sep * num, 200, 50);
                temp.add(mailButtons.get(num++));
            }

            temp.repaint();
        }
    }
}