package com.ty.codegen;

import com.ty.codegen.util.WinGlobalSettingUtil;
import com.ty.codegen.win.IndexWin;

import java.awt.*;
import java.sql.*;
import java.util.List;
import java.util.*;

public class MainApplication {

    public static void main(String[] args) {
        // 全局设置字体样式
        WinGlobalSettingUtil.initGlobalFont(new Font("新宋体", Font.PLAIN, 14));
        // 设置所有窗体样式风格
        WinGlobalSettingUtil.initWinStyle();
        try {
            new IndexWin();
        } catch (Exception e) {
            System.out.println("项目启动失败");
            e.printStackTrace();
        }
//        MysqlDBUtil mysqlDBUtil = new MysqlDBUtil();
//        try {
//            Connection conn = mysqlDBUtil.getConn();
//            Statement statement = conn.createStatement();
//            //getColNames(statement.executeQuery("select * from person"));
//            getTables(conn);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

    }

    // --------------------------------以下是获取表的字段属性 代码样例 需要结合本项目的来改造--------------------------------------------------
    private static void getTables(Connection conn) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        String catalog = conn.getCatalog();
        ResultSet tables = metaData.getTables(catalog, "%", "%", new String[]{"table"});
        while (tables.next()) {
            String table_name = tables.getString("table_name");
            ResultSet colRet = metaData.getColumns(null, "%", table_name, "%");
            ResultSetMetaData coldata = colRet.getMetaData();
            Map<String, List<Object>> map = new HashMap<>();
            while (colRet.next()) {
                ArrayList<Object> objects = new ArrayList<>();
                for (int i = 1; i <= coldata.getColumnCount(); i++) {
                    String columnLabel = coldata.getColumnLabel(i);
                    System.err.println(coldata.getColumnTypeName(i));
                    if ("char".equalsIgnoreCase(coldata.getColumnTypeName(i))) {
                        objects.add(colRet.getString(columnLabel) + "->" + columnLabel);
                    }
                    if ("int".equalsIgnoreCase(coldata.getColumnTypeName(i)) || "SMALLINT".equalsIgnoreCase(coldata.getColumnTypeName(i))) {
                        objects.add(colRet.getInt(columnLabel) + "->" + columnLabel);
                    }
                }
                map.put(UUID.randomUUID().toString(), objects);


            }
            Set<Map.Entry<String, List<Object>>> entries = map.entrySet();
            for (Map.Entry<String, List<Object>> entry : entries) {
                System.out.println(entry.getValue());
                System.out.println("=====================================");
            }
//            Map<String,String> m =new HashMap<>();
//            for (int i = 0; i < 3; i++) {
//                colRet.getString("SOURCE_DATA_TYPE");
//            }
//            for (int i = 1; i <= coldata.getColumnCount(); i++) {
            //  System.err.println(coldata.getColumnLabel(i));
//                m.put(coldata.getColumnTypeName(i),coldata.getColumnName(i));
//                while (colRet.next()) {
//                    System.out.println(colRet.getString(coldata.getColumnName(i)));
//                }
//            }
//            colRet.refreshRow();
//            Set<Map.Entry<String, String>> entries = m.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                System.out.println(entry.getValue());
//                while (colRet.next()) {
//                    if ("CHAR".equalsIgnoreCase(entry.getKey())) {
//                        System.out.println(colRet.getString("REMARKS"));
//                    }
//                }
//
//            }
            //while (colRet.next()) {

//                String columnName = colRet.getString("COLUMN_NAME");
//                String columnType = colRet.getString("TYPE_NAME");
//                int datasize = colRet.getInt("COLUMN_SIZE");
//                int digits = colRet.getInt("DECIMAL_DIGITS");
//                int nullable = colRet.getInt("NULLABLE");
//                String remarks = colRet.getString("REMARKS");

//                System.out.println(remarks + columnName + " " + columnType + " " + datasize + " " + digits + " " + nullable);
            //}
        }
    }

    /**
     * 获取表中所有字段名称
     *
     * @param rs
     * @throws SQLException
     */
    private static List<String> getColNamess(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        System.out.println("getCatalogName(int column) 获取指定列的表目录名称。" + metaData.getCatalogName(1));
        System.out.println("getColumnClassName(int column) 构造其实例的 Java 类的完全限定名称。" + metaData.getColumnClassName(1));
        System.out.println("getColumnCount()  返回此 ResultSet 对象中的列数。" + metaData.getColumnCount());
        System.out.println("getColumnDisplaySize(int column) 指示指定列的最大标准宽度，以字符为单位. " + metaData.getColumnDisplaySize(1));
        System.out.println("getColumnLabel(int column) 获取用于打印输出和显示的指定列的建议标题。 " + metaData.getColumnLabel(1));
        System.out.println("getColumnName(int column)  获取指定列的名称。" + metaData.getColumnName(1));
        System.out.println("getColumnType(int column) 获取指定列的 SQL 类型。 " + metaData.getColumnType(1));
        System.out.println("getColumnTypeName(int column) 获取指定列的数据库特定的类型名称。 " + metaData.getColumnTypeName(1));
        System.out.println("getPrecision(int column)  获取指定列的指定列宽。 " + metaData.getPrecision(1));
        System.out.println("getScale(int column) 获取指定列的小数点右边的位数。 " + metaData.getScale(1));
        System.out.println("getSchemaName(int column) 获取指定列的表模式。 " + metaData.getSchemaName(1));
        System.out.println("getTableName(int column) 获取指定列的名称。 " + metaData.getTableName(1));
        List<String> colNameList = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            colNameList.add(metaData.getColumnName(i));
        }
        System.out.println(colNameList);
//		rs.close();
        rs.first();
        return colNameList;
    }

}
