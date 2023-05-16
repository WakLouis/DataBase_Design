
/*
 * @Descripttion: 程序主体文件
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 09:52:48
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-16 17:17:57
 */
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
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
    static JTextField user, paswd;
    // 显示面板
    static JTextArea displayArea;
    static JTextArea displayAreaForCtrl;
    // 显示面板的滚动条
    static JScrollPane displayScorllPane;
    static JScrollPane displayScorllPaneForCtrl;

    // 登录界面
    void loginPanel() {
        setLayout(null);
        setVisible(true);

        // 输入框
        user = new JTextField();
        paswd = new JTextField();
        user.addFocusListener(new JTextFieldHintListener(user, "请输入用户名"));
        paswd.addFocusListener(new JTextFieldHintListener(paswd, "请输入密码"));
        user.setBounds(300, 170, 200, 40);
        paswd.setBounds(300, 220, 200, 40);
        add(user);
        add(paswd);

        // 主体文字
        JLabel loginText = new JLabel("用户登录");
        loginText.setFont(new Font("宋体", Font.BOLD, 30));
        loginText.setBounds(340, 50, 200, 100);
        add(loginText);

        // 提示文字
        JLabel tipText = new JLabel("");
        tipText.setBounds(350, 300, 200, 40);
        add(tipText);

        // 提交按钮功能实现
        submitButton = new JButton("登录");
        submitButton.setBounds(300, 270, 200, 40);
        submitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag = Program.connection(user.getText(), paswd.getText());
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
}

public class Main {

    static CardLayout cardLayout = new CardLayout();
    static Panel panel = new Panel();
    static JFrame loginFrame;
    static JFrame mainFrame;
    static JPanel contentPane;

    static JMenuBar menuBar = new JMenuBar();
    static JMenu checkMenu = new JMenu("考勤");
    static JMenu ctrlMenu = new JMenu("管理");
    static JMenu settingMenu = new JMenu("设置");
    static JMenuItem ctrlOption = new JMenuItem("修改信息");
    static JMenuItem checkOption = new JMenuItem("打卡查询");
    static JMenuItem settingOption = new JMenuItem("退出");

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

    static void mainFrameInit(JFrame f) {
        f.setTitle("数据库管理页面");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(790, 500);
        f.setVisible(true);
        f.setLocation(400, 200);

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

        // 创建菜单栏
        checkMenu.add(checkOption);
        ctrlMenu.add(ctrlOption);
        settingMenu.add(settingOption);

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
        settingOption.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                var loginPanel = loginFrame.getContentPane();
                List<JTextField> jTextFields = getTextFieldObject(loginPanel);
                for (var com : jTextFields) {
                    com.addFocusListener(new JTextFieldHintListener(com, "请输入用户名"));
                    com.addFocusListener(new JTextFieldHintListener(com, "请输入密码"));
                }

                loginFrame.setVisible(true);
            }
        });

    }

    public static void main(String[] args) {
        loginFrame = new JFrame();
        loginFrameInit(loginFrame);
    }
}