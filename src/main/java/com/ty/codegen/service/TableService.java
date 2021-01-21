package com.ty.codegen.service;

import java.util.List;

/**
 * 对表相关的业务层
 */
public interface TableService {
    /**
     * 获取当前连接的数据所有表名
     * @return java.util.List<java.lang.String> 所有表名结果集
     * @see java.util.List
     */
    List<String> listTableNames();

    /**
     * 获取数据库名
     * @return java.lang.String 数据库名称
     */
    String databaseName();

    /**
     * 根据表名获取所有字段属性信息
     * @param tableName
     * @return java.util.List<java.lang.Object>
     */
    List<Object> getTableFields(String tableName)  throws Exception;
}
