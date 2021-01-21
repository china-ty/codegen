package com.ty.codegen.dao;

import com.ty.codegen.util.MysqlDBUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableDao {

    private Connection conn = null;

    public TableDao() {
        this.conn = MysqlDBUtil.getConn();
    }

    public List<String> listTablesName() {
        if (conn == null) {
            return Collections.emptyList();
        }
        List<String> tableList = new ArrayList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            String catalog = conn.getCatalog();
            ResultSet tables = metaData.getTables(catalog, "%", "%", new String[]{"table"});
            while (tables.next()) {
                String table_name = tables.getString("table_name");
                tableList.add(table_name);
            }
        } catch (SQLException throwables) {

        }
        return tableList;
    }

    public String databaseName() {
        try {
            return conn.getCatalog();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }
}
