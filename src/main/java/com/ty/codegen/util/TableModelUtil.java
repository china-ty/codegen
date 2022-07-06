package com.ty.codegen.util;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * Table表数据
 *
 * @author ty
 * @version 1.0
 * @date 2022/7/6 0006
 * @since 1.8
 */
public class TableModelUtil {

    // 表头（列名）
    private static final String[] COLUMN_NAMES = {"字段名", "DB类型", "Java类型", "长度", "小数点", "是否必填", "注释"};

    private TableModelUtil() {
    }

    /**
     * 创建默认TableModel
     * @return
     */
    public static DefaultTableModel getDefaultTableModel() {
        DefaultTableModel tableModel = new DefaultTableModel() {
            // 禁用数据表格 当返回false为禁用true为可编辑
            @Override
            public boolean isCellEditable(int row, int column) {
                // 禁用下标为0的第一列
                if (column == 0) {
                    return false;
                }
                return true;
            }
        };
        // 设置头标题
        tableModel.setDataVector(null, COLUMN_NAMES);
        return tableModel;
    }
}
