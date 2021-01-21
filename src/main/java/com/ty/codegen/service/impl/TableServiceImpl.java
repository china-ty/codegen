package com.ty.codegen.service.impl;

import com.ty.codegen.dao.TableDao;
import com.ty.codegen.service.TableService;

import java.util.List;
import java.util.Objects;

/**
 * 对表相关的业务层的实现
 */
public class TableServiceImpl implements TableService {

    private TableDao tableDao = new TableDao();

    @Override
    public String databaseName() {
        return tableDao.databaseName();
    }

    @Override
    public List<String> listTableNames() {
        return tableDao.listTablesName();
    }

    @Override
    public List<Object> getTableFields(String tableName) throws Exception{
        if (Objects.isNull(tableName)) throw new NullPointerException("表名不能为空");
        // TODO: 功能待实现
        return null;
    }
}
