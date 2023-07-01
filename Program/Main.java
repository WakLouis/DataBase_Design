
/*
 * @Descripttion: 程序主体文件
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 09:52:48
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-06-05 01:13:13
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//创建欢迎词
class WelcomeText extends JLabel {
    Date date = new Date();

    void welcomeText() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        setText("你好! " + Panel.user.getText() + ", " + "今天是" + formatter.format(date));
        // setBorder(BorderFactory.createLineBorder(Color.RED,1));
        setFont(new Font("微软雅黑", Font.BOLD, 18));
        setBounds(5, 5, 500, 20);
    }
}

// 窗口界面
class Panel extends JPanel {
    // 提交按钮
    static JButton submitButton;
    // 输入框
    static JTextField user, paswd, ipaddress;
    // 显示面板
    static JTextArea displayArea;
    static JTextArea displayAreaForCtrl;
    // 显示面板的滚动条
    static JScrollPane displayScorllPane;
    static JScrollPane displayScorllPaneForCtrl;

    // 登录面板
    void loginPanel() {
        setLayout(null);
        setVisible(true);

        // 输入框
        user = new JTextField();
        paswd = new JTextField();
        user.addFocusListener(new JTextFieldHintListener(user, "请输入用户名"));
        paswd.addFocusListener(new JTextFieldHintListener(paswd, "请输入密码"));
        user.setBounds(300, 220, 200, 40);
        paswd.setBounds(300, 270, 200, 40);
        add(user);
        add(paswd);

        // 连接地址
        ipaddress = new JTextField();
        ipaddress.addFocusListener(new JTextFieldHintListener(ipaddress, "连接地址"));
        ipaddress.setText("192.168.5.128");
        ipaddress.setBounds(300, 170, 200, 40);
        add(ipaddress);

        // 主体文字
        JLabel loginText = new JLabel("用户登录");
        loginText.setFont(new Font("宋体", Font.BOLD, 30));
        loginText.setBounds(340, 50, 200, 100);
        add(loginText);

        // 提示文字
        JLabel tipText = new JLabel("");
        tipText.setBounds(350, 350, 200, 40);
        add(tipText);

        // 提交按钮功能实现
        submitButton = new JButton("登录");
        submitButton.setBounds(300, 320, 200, 40);
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = Program.connection(user.getText(), paswd.getText(), ipaddress.getText());
                if (flag == true) {
                    // Main.loginFrame.dispose();
                    Main.loginFrame.setVisible(false);
                    Main.mainFrame = new JFrame();
                    Main.mainFrameInit(Main.mainFrame);
                } else {
                    tipText.setText("用户名或密码错误");
                }
                // Main.loginFrame.dispose();
            }

        });
        add(submitButton);
    }

    // 考勤面板
    void checkPanel() {
        setVisible(true);
        setLayout(null);

        WelcomeText welcomeText = new WelcomeText();
        welcomeText.welcomeText();
        add(welcomeText);

        JButton checkButton = new JButton("一键打卡");
        checkButton.setBounds(0, 30, 201, 70);
        checkButton.setContentAreaFilled(false);
        checkButton.setFocusPainted(false);
        add(checkButton);

        JButton queryButton = new JButton("考勤查询");
        queryButton.setBounds(0, 99, 201, 70);
        queryButton.setContentAreaFilled(false);
        queryButton.setFocusPainted(false);
        add(queryButton);

        MailIconButton mailIconButton = new MailIconButton(25, 25, "./icon/mailIcon.png");
        mailIconButton.setLocation(740, 5);
        mailIconButton.setContentAreaFilled(false);
        mailIconButton.setFocusPainted(false);
        add(mailIconButton);

        displayArea = new JTextArea();
        displayArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        displayArea.setEditable(false);

        displayScorllPane = new JScrollPane();
        displayScorllPane.setBounds(200, 30, 590, 400);
        displayScorllPane.setViewportView(displayArea);

        add(displayScorllPane);

        checkButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Program.fCheck()) {
                    displayArea.append("每日打卡成功！\n");
                    Panel.displayArea.append(
                            "\n_________________________________________________________________________________\n");
                } else {
                    displayArea.append("每日打卡失败！\n");
                    Panel.displayArea.append(
                            "\n_________________________________________________________________________________\n");
                }
                // displayArea.append("每日打卡成功！\n");
            }
        });
        queryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Program.fQuery();

            }
        });
    }

    // 控制面板
    void ctrlPanel() {
        setVisible(true);
        setLayout(null);

        WelcomeText welcomeText = new WelcomeText();
        welcomeText.welcomeText();
        add(welcomeText);

        JButton checkButtonForCtrl = new JButton("查看考勤表");
        checkButtonForCtrl.setBounds(0, 30, 201, 70);
        checkButtonForCtrl.setContentAreaFilled(false);
        checkButtonForCtrl.setFocusPainted(false);
        add(checkButtonForCtrl);

        JButton updateButton = new JButton("修改考勤信息");
        updateButton.setBounds(0, 99, 201, 70);
        updateButton.setContentAreaFilled(false);
        updateButton.setFocusPainted(false);
        add(updateButton);

        MailIconButton mailIconButton = new MailIconButton(25, 25, "./icon/mailIcon.png");
        mailIconButton.setLocation(740, 5);
        mailIconButton.setContentAreaFilled(false);
        mailIconButton.setFocusPainted(false);
        add(mailIconButton);

        displayAreaForCtrl = new JTextArea();
        displayAreaForCtrl.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        displayAreaForCtrl.setBounds(200, 30, 590, 470);
        displayAreaForCtrl.setEditable(false);

        displayScorllPaneForCtrl = new JScrollPane();
        displayScorllPaneForCtrl.setBounds(200, 30, 590, 400);
        displayScorllPaneForCtrl.setViewportView(displayAreaForCtrl);

        add(displayScorllPaneForCtrl);

        JTextField upName, upDate, upTo;
        upName = new JTextField();
        upName.addFocusListener(new JTextFieldHintListener(upName, "需更新用户名"));
        upDate = new JTextField();
        upDate.addFocusListener(new JTextFieldHintListener(upDate, "需更新日期"));
        upTo = new JTextField();
        upTo.addFocusListener(new JTextFieldHintListener(upTo, "修改值为"));

        upName.setBounds(0, 169, 201, 30);
        upDate.setBounds(0, 199, 201, 30);
        upTo.setBounds(0, 229, 201, 30);

        add(upName);
        add(upDate);
        add(upTo);

        checkButtonForCtrl.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Program.fQueryAll();
            }
        });

        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Program.fUpdate(upName.getText(), upDate.getText(), upTo.getText());
            }
        });

    }

    // 创建新用户面板
    static JTextField userID, departmentID, name, sex, birthday, paswdForCreate;
    static JLabel hintText;
    static JButton submmitButton;

    void createNewUserPanel() {
        setLayout(null);

        WelcomeText welcomeText = new WelcomeText();
        welcomeText.welcomeText();
        add(welcomeText);

        userID = new JTextField();
        userID.addFocusListener(new JTextFieldHintListener(userID, "员工编号"));
        userID.setBounds(300, 60, 200, 40);
        add(userID);

        departmentID = new JTextField();
        departmentID.addFocusListener(new JTextFieldHintListener(departmentID, "所属部门编号"));
        departmentID.setBounds(300, 110, 200, 40);
        add(departmentID);

        name = new JTextField();
        name.addFocusListener(new JTextFieldHintListener(name, "员工姓名"));
        name.setBounds(300, 160, 200, 40);
        add(name);

        sex = new JTextField();
        sex.addFocusListener(new JTextFieldHintListener(sex, "员工性别"));
        sex.setBounds(300, 210, 200, 40);
        add(sex);

        birthday = new JTextField();
        birthday.addFocusListener(new JTextFieldHintListener(birthday, "出生日期"));
        birthday.setBounds(300, 260, 200, 40);
        add(birthday);

        paswdForCreate = new JTextField();
        paswdForCreate.addFocusListener(new JTextFieldHintListener(paswdForCreate, "账户密码"));
        paswdForCreate.setBounds(300, 310, 200, 40);
        add(paswdForCreate);

        submitButton = new JButton("提交");
        submitButton.setBounds(300, 360, 200, 40);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Program.fCreateNewUser(userID.getText(), departmentID.getText(), name.getText(), sex.getText(),
                        birthday.getText(), paswdForCreate.getText())) {
                    hintText.setText("创建成功！");
                } else {
                    hintText.setText("创建失败！");
                }
            }

        });

        hintText = new JLabel("");
        hintText.setFont(new Font("宋体", Font.PLAIN, 12));
        hintText.setBounds(372, 390, 164, 40);
        add(hintText);
    }
}

public class Main {

    static CardLayout cardLayout = new CardLayout();
    static Panel panel = new Panel();
    static JFrame loginFrame;
    static JFrame mainFrame;
    static JPanel contentPane;

    // 创建邮件系统线程
    static MailSystem mailSystemThread;

    static JMenuBar menuBar = new JMenuBar();

    static JMenu checkMenu = new JMenu("考勤");
    static JMenu ctrlMenu = new JMenu("管理");
    static JMenu settingMenu = new JMenu("设置");

    static JMenuItem ctrlOption = new JMenuItem("修改信息");
    static JMenuItem checkOption = new JMenuItem("打卡查询");
    static JMenuItem quitOption = new JMenuItem("退出");
    static JMenuItem sendMailOption = new JMenuItem("发邮件");
    static JMenuItem createNewUserOption = new JMenuItem("创建用户");

    // 一次性获取TextField控件的方法
    public static List<JTextField> getTextFieldObject(Component component) {
        List<JTextField> result = new ArrayList<>();
        if (component instanceof JPanel) {
            for (Component com : ((JPanel) component).getComponents()) {
                List<JTextField> textFields = getTextFieldObject(com);
                result.addAll(textFields);
            }
        } else {
            if (component instanceof JTextField) {
                result.add((JTextField) component);
            }
        }
        return result;
    }

    static void loginFrameInit(JFrame f) {
        // f.setLayout(null);
        f.setLocation(400, 200);
        f.setTitle("登录");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(790, 500);
        f.setVisible(true);

        panel.loginPanel();
        f.add(panel);
        panel.updateUI();

    }

    // 主界面创建
    static void mainFrameInit(JFrame f) {
        f.setTitle("数据库管理页面");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(790, 500);
        f.setVisible(true);
        f.setLocation(400, 200);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("Closed!!!");
            }
        });

        // 创建Frame面板
        contentPane = new JPanel();
        // contentPane.setLayout(null);
        contentPane.setLayout(cardLayout);
        f.setContentPane(contentPane);

        // 创建check面板
        Panel checkPanel = new Panel();
        checkPanel.checkPanel();
        contentPane.add(checkPanel, "check");

        // 创建ctrl面板
        Panel ctrlPanel = new Panel();
        ctrlPanel.ctrlPanel();
        contentPane.add(ctrlPanel, "ctrl");

        // 创建receivemail面板
        mailPanel receiveMailPanel = new mailPanel();
        receiveMailPanel.receiveMailPanel();
        contentPane.add(receiveMailPanel, "receiveMail");

        // 创建sendMail面板
        mailPanel sendMailPanel = new mailPanel();
        sendMailPanel.sendMailPanel();
        contentPane.add(sendMailPanel, "sendMail");

        // 创建createUser面板
        Panel createNewUserPanel = new Panel();
        createNewUserPanel.createNewUserPanel();
        contentPane.add(createNewUserPanel, "createNewUserPanel");

        // 创建菜单栏
        checkMenu.add(checkOption);

        ctrlMenu.add(ctrlOption);
        ctrlMenu.add(sendMailOption);
        ctrlMenu.add(createNewUserOption);

        settingMenu.add(quitOption);

        menuBar.add(checkMenu);
        menuBar.add(ctrlMenu);
        menuBar.add(settingMenu);

        f.setJMenuBar(menuBar);
        checkOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "check");
            }
        });
        ctrlOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "ctrl");
            }
        });
        quitOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                var loginPanel = loginFrame.getContentPane();
                List<JTextField> jTextFields = getTextFieldObject(loginPanel);

                int index = 0;
                for (var com : jTextFields) {
                    switch (index) {
                        case 0:
                            com.addFocusListener(new JTextFieldHintListener(com, "请输入用户名"));
                            index++;
                            break;
                        case 1:
                            com.addFocusListener(new JTextFieldHintListener(com, "请输入密码"));
                            index++;
                            break;
                        case 2:
                            com.addFocusListener(new JTextFieldHintListener(com, "连接地址"));
                            ((JTextField) com).setText("192.168.5.128");
                            index++;
                            break;
                    }

                }

                mailSystemThread.interrupt();

                loginFrame.setVisible(true);
            }
        });
        sendMailOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "sendMail");
            }

        });
        createNewUserOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPane, "createNewUserPanel");
            }

        });

        mailSystemThread = new MailSystem();
        mailSystemThread.start();
    }

    public static void main(String[] args) {
        loginFrame = new JFrame();
        loginFrameInit(loginFrame);
    }
}