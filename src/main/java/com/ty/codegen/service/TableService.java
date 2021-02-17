package com.ty.codegen.service;

import com.ty.codegen.entity.TableField;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 对表相关的业务层
 */
public interface TableService {
    /**
     * 获取当前连接的数据所有表名
     * @return java.util.List<java.lang.String> 所有表名结果集
     * @see java.util.List
     */
    Map<String,String> listTableNames() throws SQLException;

    /**
     * 获取数据库名
     * @return java.lang.String 数据库名称
     */
    String databaseName() throws SQLException;

    /**
     * 根据表名获取所有字段属性信息
     * @param tableName
     * @return java.util.List<com.ty.codegen.entity.TableField>
     */
    List<TableField> getTableFields(String tableName)  throws Exception;
}
