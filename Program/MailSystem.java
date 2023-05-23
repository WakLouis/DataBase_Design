/*
 * @Descripttion: 邮件系统线程，目的是周期性从数据库中获取邮件信息
 * @version: v0.1
 * @Author: WakLouis
 * @Date: 2023-05-17 11:00:01
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-19 15:12:46
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
                Main.cardLayout.show(Main.contentPane, "receiveMail");
            }
        });
    }
}

class mailPanel extends JPanel {
    // 滚动框
    static JScrollPane mailListJScrollPane;
    static JScrollPane contentJScrollPane;

    // 消息框
    static JPanel mailListPanel;
    static JTextArea displayArea;

    // 发送邮件界面的输入框
    static JTextField receiverIDSetField;
    static JTextField titleField;

    // 发邮件的文本框
    static JTextArea mailContentArea;

    // 发邮件的滚动框
    static JScrollPane mailContentScrollPane;

    // 发送按钮
    static JButton sendButton;

    // 提示信息
    static JLabel hintLabel;

    void receiveMailPanel() {
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

        mailListJScrollPane.setBounds(0, 30, 200, 400);
        contentJScrollPane.setBounds(200, 30, 590, 400);

        mailListPanel = new JPanel();
        // mailListPanel.setLayout(new GridLayout(300, 1));
        mailListJScrollPane.setViewportView(mailListPanel);

        displayArea = new JTextArea();
        displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        displayArea.setEditable(false);
        contentJScrollPane.setViewportView(displayArea);

        mailListJScrollPane.setName("mailListJScrollPane");
        contentJScrollPane.setName("contentJScrollPane");

        add(mailListJScrollPane);
        add(contentJScrollPane);
    }

    void sendMailPanel() {
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

        receiverIDSetField = new JTextField();
        receiverIDSetField.addFocusListener(new JTextFieldHintListener(receiverIDSetField, "收件人"));
        receiverIDSetField.setBounds(20, 40, 700, 40);
        add(receiverIDSetField);

        titleField = new JTextField();
        titleField.addFocusListener(new JTextFieldHintListener(titleField, "标题"));
        titleField.setBounds(20, 100, 700, 40);
        add(titleField);

        mailContentArea = new JTextArea();
        mailContentArea.addFocusListener(new JTextAreaHintListener(mailContentArea, "正文"));
        mailContentScrollPane = new JScrollPane();
        mailContentScrollPane.setBounds(20, 160, 700, 200);
        mailContentScrollPane.setViewportView(mailContentArea);
        add(mailContentScrollPane);

        sendButton = new JButton("发送");
        sendButton.setBounds(20, 380, 64, 40);
        sendButton.setFocusPainted(false);
        sendButton.setContentAreaFilled(false);
        add(sendButton);

        hintLabel = new JLabel("");
        hintLabel.setFont(new Font("宋体", Font.PLAIN, 10));
        hintLabel.setBounds(100, 380, 164, 40);
        add(hintLabel);

        sendButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = Program.fSendMail(receiverIDSetField.getText(), titleField.getText(),
                        mailContentArea.getText());
                if (flag) {
                    hintLabel.setText("发送成功！");
                } else {
                    hintLabel.setText("发送失败！");
                }
            }

        });

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

    public Component getByName(List list, String Name) {
        for (Object com : list) {
            if (((Component) com).getName() == Name) {
                return (Component) com;
            }
        }
        return null;
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
            JPanel _panel = mailPanel.mailListPanel;

            _panel.removeAll();

            int num = 0;
            ArrayList<MailButton> mailButtons = new ArrayList<MailButton>();
            _panel.setLayout(new GridLayout(mailList.size(), 1));
            for (Mail mail : mailList) {
                MailButton mailButton = new MailButton(mail);
                mailButtons.add(mailButton);
                _panel.add(mailButtons.get(num++));
            }

            _panel.repaint();
        }
    }
}