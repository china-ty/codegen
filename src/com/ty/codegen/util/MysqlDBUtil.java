package com.ty.codegen.util;

import java.sql.*;

public class MysqlDBUtil {
    //链接字符串
    public Connection conn = null;
    //预编译对象
    public PreparedStatement pst = null;
    //结果集对象
    public ResultSet rs = null;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://120.76.41.108:3306/bee_express_dev?useLegacyDatetimeCode=false&serverTimezone=America/New_York";
    private static final String USER = "sunjd";
    private static final String PASSWORD = "sunjd_0876";

    //静态代码块
    static {
        try {
            //加载驱动
            Class.forName(DRIVER);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
    }

    //链接字符串
    public static Connection getConn() {

        try {
            return   DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;
    }

    //更新的方法
    public int getUpdate(String sql, Object... obj) {

        try {

            //连接字符串
            conn = getConn();
            //把sql语句转入数据库中
            pst = conn.prepareStatement(sql);
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
    public ResultSet getQuery(String sql, Object... obj) {

        try {

            conn = getConn();
            pst = conn.prepareStatement(sql);
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
    public void closeAll() {

        try {

            if (conn != null) {
                conn.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
