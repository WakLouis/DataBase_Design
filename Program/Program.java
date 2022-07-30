/*
 * @Descripttion: 
 * @version: 
 * @Author: WakLouis
 * @Date: 2022-05-23 09:31:43
 * @LastEditors: WakLouis
 * @LastEditTime: 2022-06-05 21:43:37
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


public class Program {
    static String connectionUrl;
    public static boolean connection(String user, String paswd) {
        connectionUrl =
            "jdbc:sqlserver://127.0.0.1:1433;"
                + "database=DatabaseDesign;"
                + "user=" + user + ";"
                + "password=" + paswd + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";

        try (Connection conn = DriverManager.getConnection(connectionUrl);) {
            conn.close();
        	return true;
            
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            //e.printStackTrace();
            return false;
        }
    }
    
    public static boolean fCheck(){
        try{
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "EXEC proc_check";
            java.sql.Statement st = con.createStatement();
            st.execute(sql);

            st.close();
            con.close();
            return true;
        }
        catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public static void fQuery(){
        try{
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "select * from 数据总表 where 员工编号 = SUSER_NAME()";
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            int num = md.getColumnCount();
            rs.next();
            for(int i = 1;i <= md.getColumnCount();i++){
                String columnName = md.getColumnName(i);
                Panel.displayArea.append(String.format("%s\t",columnName));
            }
            Panel.displayArea.append("\n");
            for(int i = 1;i <= md.getColumnCount();i++){
                Panel.displayArea.append(String.format("%s\t",rs.getObject(i)));
            }
            Panel.displayArea.append(String.format("\n您的考勤率为：%.1f", Float.parseFloat(rs.getString("考勤天数")) / (num-2) ) );
            Panel.displayArea.append("\n_________________________________________________________________________________\n");
            rs.close();
            con.close();
        }
        catch(SQLException e){
            Panel.displayArea.append("操作失败！\n");
            Panel.displayArea.append("\n_________________________________________________________________________________\n");
            e.printStackTrace();
        }

    }

    public static void fQueryAll(){
        if(Panel.user.getText().equals("1001")){
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl.append("\n_________________________________________________________________________________\n");
            return;
        }
        try{
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "select * from 数据总表";
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData();
            for(int i = 1;i <= md.getColumnCount();i++){
                String columnName = md.getColumnName(i);
                Panel.displayAreaForCtrl.append(String.format("%s\t",columnName));
            }
            while(rs.next()){
                Panel.displayAreaForCtrl.append("\n");
                for(int i = 1;i <= md.getColumnCount();i++){
                    Panel.displayAreaForCtrl.append(String.format("%s\t",rs.getObject(i)));
                }
            }
            Panel.displayAreaForCtrl.append("\n_________________________________________________________________________________\n");
            rs.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static void fUpdate(String Name,String Date,String To){
        if(Panel.user.getText().equals("1001")){
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl.append("\n_________________________________________________________________________________\n");
            return;
        }
        try {
            Connection con = DriverManager.getConnection(connectionUrl);
            String sql = "update 数据总表 set \"" + Date + "\" = " + To + " where 员工编号 = " + Name;
            java.sql.Statement st = con.createStatement();
            st.execute(sql);
            Panel.displayAreaForCtrl.append("操作成功!已将"+Name+"在"+Date+"的值设置为："+To+"\n");
            Panel.displayAreaForCtrl.append("\n_________________________________________________________________________________\n");
        } catch (SQLException e) {
            Panel.displayAreaForCtrl.append("操作失败！\n");
            Panel.displayAreaForCtrl.append("\n_________________________________________________________________________________\n");
            e.printStackTrace();
        }
    }
}