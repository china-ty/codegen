package com.ty.codegen.service.impl;

import com.ty.codegen.dao.TableDao;
import com.ty.codegen.entity.TableField;
import com.ty.codegen.service.TableService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 对表相关的业务层的实现
 */
public class MysqlTableServiceImpl implements TableService {

    private TableDao tableDao = new TableDao();

    @Override
    public String databaseName() throws SQLException {
        return tableDao.databaseName();
    }

    @Override
    public Map<String,String> listTableNames() throws SQLException{
        return tableDao.listTablesName();
    }

    @Override
    public List<TableField> getTableFields(String tableName) throws Exception{
        if (Objects.isNull(tableName)) throw new NullPointerException("表名不能为空");
        List<TableField> tableFields = tableDao.listTableField(tableName);
        return tableFields;
    }
}
