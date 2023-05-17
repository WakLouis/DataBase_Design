/*
 * @Descripttion: 对接数据库
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 09:31:43
 * @LastEditors: WakLouis
 * @LastEditTime: 2023-05-17 10:43:21
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class Program {
    static String connectionUrl;
    static String userString, paswdString;

    public static java.sql.Time toSqlTime(LocalTime localTime) {
        return java.sql.Time.valueOf(localTime);
    }

    public static boolean connection(String user, String paswd) {
        userString = user;
        paswdString = paswd;
        connectionUrl = "jdbc:sqlserver://192.168.137.128:1433;"
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
            for (int i = 1; i <= md.getColumnCount(); i++) {
                String columnName = md.getColumnName(i);
                Panel.displayArea.append(String.format("%s\t", columnName));
            }
            Panel.displayArea.append("\n");
            for (int i = 1; i <= md.getColumnCount(); i++) {
                Panel.displayArea.append(String.format("%s\t", rs.getObject(i)));
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
        char level = Panel.user.getText().charAt(0);
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
            for (int i = 1; i <= md.getColumnCount(); i++) {
                String columnName = md.getColumnName(i);
                Panel.displayAreaForCtrl.append(String.format("%s\t", columnName));
            }
            while (rs.next()) {
                Panel.displayAreaForCtrl.append("\n");
                for (int i = 1; i <= md.getColumnCount(); i++) {
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
        char level = Panel.user.getText().charAt(0);
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
}