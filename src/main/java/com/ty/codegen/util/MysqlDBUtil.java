package com.ty.codegen.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.Objects;

public class MysqlDBUtil {
    //链接字符串
    public static Connection conn = null;
    //预编译对象
    public static PreparedStatement pst = null;
    //结果集对象
    public static ResultSet rs = null;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://120.76.41.108:3306/bee_express_dev?useLegacyDatetimeCode=false&serverTimezone=America/New_York&connectTimeout=5000&socketTimeout=5000";
    private static final String USER = "linxx";
    private static final String PASSWORD = "linxx_1181";

    //静态代码块
    static {
            HikariConfig hikariConfig = new HikariConfig("/config/database.properties");
            HikariDataSource ds = new HikariDataSource(hikariConfig);
        try {
            MysqlDBUtil.conn = ds.getConnection();
        } catch (SQLException throwables) {
            System.out.println("连接数据库失败!");
            throwables.printStackTrace();
        }
    }

    //链接字符串
    @Deprecated
    public static Connection getConn() {
        if (!Objects.isNull(MysqlDBUtil.conn)) {
            return MysqlDBUtil.conn;
        }
        try {
            return   DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    //更新的方法
    public static int getUpdate(String sql, Object... obj) {

        try {

            //连接字符串
            //conn = getConn();
            //把sql语句转入数据库中
            pst = MysqlDBUtil.conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {

                pst.setObject(i + 1, obj[i]);
            }
            //执行sql语句  返回的是受影响的行数
            return pst.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;

    }

    //查询的方法
    public static ResultSet getQuery(String sql, Object... obj) {

        try {

            //conn = getConn();
            pst = MysqlDBUtil.conn.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {

                pst.setObject(i + 1, obj[i]);
            }
            rs = pst.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;

    }

    //关闭链接
    public static void closeAll() {

        try {

            if (MysqlDBUtil.conn != null) {
                MysqlDBUtil.conn.close();
            }
            if (MysqlDBUtil.pst != null) {
                MysqlDBUtil.pst.close();
            }
            if (MysqlDBUtil.rs != null) {
                MysqlDBUtil.rs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
