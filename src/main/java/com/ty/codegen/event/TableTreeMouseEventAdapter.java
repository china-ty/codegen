package com.ty.codegen.event;

import com.ty.codegen.dao.TableDao;
import com.ty.codegen.entity.TableField;
import com.ty.codegen.service.TableService;
import com.ty.codegen.service.impl.TableServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

/**
 * 主窗体 左部 树型表 鼠标 事件 监听 适配器
 */
public class TableTreeMouseEventAdapter extends MouseAdapter {

    private TableService tableService = new TableServiceImpl();

    private JTree tableTrees;

    private DefaultTableModel tableModel;

    public TableTreeMouseEventAdapter(JTree jTree, DefaultTableModel tableModel) {
        this.tableTrees = jTree;
        this.tableModel = tableModel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int clickCount = e.getClickCount();
        // 点击的数量等于2表示双击
        if (clickCount == 2) {
            this.clear(tableModel);
            // 获取选中的节点的名字
            String selectNodeName = tableTrees.getSelectionPath().getLastPathComponent().toString();
            try {
                List<TableField> tableFields = tableService.getTableFields(selectNodeName);
                for (TableField tableField : tableFields) {
                    Object[] objects = new Object[7];
                    objects[0] = tableField.getName();
                    objects[1] = tableField.getDbType();
                    objects[2] = tableField.getEntityType();
                    objects[3] = tableField.getLength();
                    objects[4] = tableField.getDecimal();
                    objects[5] = tableField.getRequired();
                    objects[6] = tableField.getRemarks();
                    tableModel.addRow(objects);
                }
            } catch (Exception exception) {
                System.out.println("数据库异常");
                JOptionPane.showMessageDialog(null, "数据库链接错误!", "提示",JOptionPane.ERROR_MESSAGE);
                // exception.printStackTrace();
            }
        }
    }

    private void clear(DefaultTableModel tableModel) {
        // 获取table中的所有数据
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            for (int j = 0; j < tableModel.getColumnCount(); j++) {
//                System.out.print(tableModel.getValueAt(i, j) +"--");
//            }
//            System.out.println();
//        }
        // int i = 0; i <= tableModel.getRowCount() ; i++ 错误逻辑 因为
        // 当每 tableModel.removeRow(i);一次tableModel.getRowCount() 是会变的
        // 正确是获取总数减一(最大下标) 一直到最小下标0
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            //System.out.println(i);
            tableModel.removeRow(i);
        }
    }
}
