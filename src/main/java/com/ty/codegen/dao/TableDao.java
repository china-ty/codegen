package com.ty.codegen.dao;

import com.ty.codegen.entity.TableField;
import com.ty.codegen.util.MysqlDBUtil;

import java.sql.*;
import java.util.*;

public class TableDao {

    private Connection conn = null;
    // 初始化表对象及数据库链接
    public TableDao() {
        this.conn = MysqlDBUtil.getConn();
    }
    // 获取当前链接数据库所有表名
    public Map<String,String> listTablesName() throws SQLException {
        if (conn == null) {
            return Collections.emptyMap();
        }
        Map<String,String> tableMap = Collections.emptyMap();
        DatabaseMetaData metaData = conn.getMetaData();
        String catalog = conn.getCatalog();
        ResultSet tables = metaData.getTables(catalog, "%", "%", new String[]{"table"});
        tableMap = new HashMap();
        while (tables.next()) {
            String table_name = tables.getString("TABLE_NAME");
            String table_remarks = tables.getString("REMARKS");
            tableMap.put(table_name,table_remarks);
        }
        return tableMap;
    }
    // 获取当前数据库名
    public String databaseName() throws SQLException {
        return conn.getCatalog();
    }

    /**
     * 获取表的字段信息
     * @param tableName
     * @return List<TableField>
     * @throws SQLException
     */
    public List<TableField> listTableField(String tableName) throws SQLException {
        if (conn == null) {
            return Collections.emptyList();
        }
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet colRet = metaData.getColumns(null, "%", tableName, "%");
        ResultSetMetaData coldata = colRet.getMetaData();
        List<TableField> fieldList = new ArrayList<>();
        while (colRet.next()) {
            TableField tableField = new TableField();
            for (int i = 1; i <= coldata.getColumnCount(); i++) {
                tableField.setName(colRet.getString("COLUMN_NAME"));
                tableField.setDbType(colRet.getString("TYPE_NAME"));
                tableField.setEntityType("String");
                tableField.setLength(colRet.getInt("COLUMN_SIZE"));
                tableField.setRemarks(colRet.getString("REMARKS"));
                tableField.setRequired(colRet.getInt("NULLABLE") == 1 ? false : true);
                tableField.setDecimal(colRet.getInt("DECIMAL_DIGITS"));
            }
            fieldList.add(tableField);
        }
        return fieldList;
    }
}
