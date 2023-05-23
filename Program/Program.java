/*
 * @Descripttion: 对接数据库
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 09:31:43
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-23 17:04:27
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Program {
    static String connectionUrl;
    static String userString, paswdString, ipaddressString;

    public static java.sql.Time toSqlTime(LocalTime localTime) {
        return java.sql.Time.valueOf(localTime);
    }

    public static boolean connection(String user, String paswd, String ipaddress) {
        userString = user;
        paswdString = paswd;
        ipaddressString = ipaddress;
        connectionUrl = "jdbc:sqlserver://" + ipaddressString + ":1433;"
                + "database=DataBaseDesign;"
                + "user=sa;"
                + "password=123456;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=3;";

        try (Connection con = DriverManager.getConnection(connectionUrl);) {

            // 验证用户名和密码
            String sql = "SELECT 密码 FROM dbo.员工表 WHERE 员工编号 = " + user;
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String truePaswd = rs.getString("密码");
            truePaswd = truePaswd.replaceAll(" ", "");
            if (!paswd.equals(truePaswd)) {
                con.close();
                return false;
            }
            con.close();
            return true;

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fCheck() {
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql;
            java.sql.Statement st = con.createStatement();
            int flag = 1;

            // 检测今天是否打卡

            // 打卡前检测是否已经迟到
            sql = "SELECT 上班时间 from dbo.管理信息";
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            Time officeTime = rs.getTime("上班时间");
            if (officeTime.before(toSqlTime(LocalTime.now()))) {
                Panel.displayArea.append(String.format("上班时间为：" + officeTime + "    您已迟到！\n\n"));
                flag = 2;
            }

            // 打卡
            sql = ("EXEC proc_check @checkFlag = " + flag + " ,@checkName = " + userString).toString();

            st.execute(sql);

            st.close();
            rs.close();
            con.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void fQuery() {
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "select * from 数据总表 where 员工编号 = " + userString;
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int num = md.getColumnCount();
            rs.next();
            for (int i = 1; i <= num; i++) {
                String columnName = md.getColumnName(i);
                Panel.displayArea.append(String.format("%s\t", columnName));
            }
            Panel.displayArea.append("\n");
            for (int i = 1; i <= num; i++) {
                if (i <= 3) {
                    Panel.displayArea.append(String.format("%s\t", rs.getObject(i)));
                    continue;
                }

                if ((int) rs.getObject(i) == 1) {
                    Panel.displayArea.append(String.format("正常\t"));
                } else if ((int) rs.getObject(i) == 2) {
                    Panel.displayArea.append(String.format("迟到\t"));
                } else {
                    Panel.displayArea.append(String.format("缺勤\t"));
                }
                // Panel.displayArea.append(String.format("%s\t", rs.getObject(i)));
            }
            Panel.displayArea
                    .append(String.format("\n您的考勤率为：%.1f", Float.parseFloat(rs.getString("考勤天数")) / (num - 2)));
            Panel.displayArea
                    .append("\n_________________________________________________________________________________\n");
            rs.close();
            con.close();
        } catch (SQLException e) {
            Panel.displayArea.append("操作失败！\n");
            Panel.displayArea
                    .append("\n_________________________________________________________________________________\n");
            e.printStackTrace();
        }

    }

    public static void fQueryAll() {
        char level = userString.charAt(0);
        if (level == '1') {
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");
            return;
        }
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "select * from 数据总表";
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int num = md.getColumnCount();
            for (int i = 1; i <= num; i++) {
                String columnName = md.getColumnName(i);
                Panel.displayAreaForCtrl.append(String.format("%s\t", columnName));
            }
            while (rs.next()) {
                Panel.displayAreaForCtrl.append("\n");
                for (int i = 1; i <= num; i++) {
                    Panel.displayAreaForCtrl.append(String.format("%s\t", rs.getObject(i)));
                }
            }
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");
            rs.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fUpdate(String Name, String Date, String To) {
        char level = Name.charAt(0);
        if (level == '1') {
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");
            return;
        }
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "update 数据总表 set \"" + Date + "\" = " + To + " where 员工编号 = " + Name;
            java.sql.Statement st = con.createStatement();
            st.execute(sql);
            Panel.displayAreaForCtrl.append("操作成功!已将" + Name + "在" + Date + "的值设置为：" + To + "\n");
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");

            con.close();
        } catch (SQLException e) {
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");
            e.printStackTrace();
        }
    }

    public static ArrayList<Mail> fGetMail() {
        ArrayList<Mail> mailList = new ArrayList<Mail>();
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "SELECT * FROM [DatabaseDesign].[dbo].[邮件总表] where 收件人集合 LIKE '%" + userString + "%'";
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Mail mail = new Mail();
                mail.mailID = rs.getString(1);
                mail.senderID = rs.getString(2);
                mail.receiverIDSet = rs.getString(3);
                mail.sendedTime = rs.getTimestamp(4);
                mail.title = rs.getString(5);
                mail.content = rs.getString(6);
                mailList.add(mail);
            }
            return mailList;

        } catch (SQLException e) {
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl
                    .append("\n_________________________________________________________________________________\n");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean fSendMail(String receiverIDSet, String title, String content) {
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String sql = "INSERT dbo.邮件总表(发件人ID, 收件人集合, 发件日期, 邮件标题, 邮件内容) values('" + userString + "','"
                    + receiverIDSet
                    + "','" + timestamp + "','" + title + "','" + content + "')";
            java.sql.Statement st = con.createStatement();
            st.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fCreateNewUser(String userID, String departmentID, String name, String sex, String birthday,
            String paswdForCreate) {
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "INSERT dbo.员工表(员工编号, 部门编号, 姓名, 性别, 出生日期, 密码) values('" + userID + "', '" + departmentID
                    + "', '" + name + "', '" + sex + "', '" + birthday + "', '" + paswdForCreate + "')";
            java.sql.Statement st = con.createStatement();
            st.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}